package ru.intervale.courses.fp;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.google.common.collect.ImmutableMap;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AuthorTasksTest {

    private Tasks tasks = getTasks();
    private static final double EPSILON = 1e-8;

    protected abstract Tasks getTasks();

    @Test
    public void sum() {
        assertEquals(0, tasks.sum(new int[]{}));
        assertEquals(42, tasks.sum(new int[]{42}));
        assertEquals(-2, tasks.sum(new int[]{-5, 0, 1, 2}));
    }

    @Test
    public void maxHypotenuse() {
        assertNull(tasks.maxHypotenuse(new int[]{}, new int[]{}));
        assertEquals(5.0, tasks.maxHypotenuse(new int[]{3}, new int[]{4}), EPSILON);
        try {
            assertEquals(5.0, tasks.maxHypotenuse(new int[]{3, 30}, new int[]{4}), EPSILON);
            assertEquals(5.0, tasks.maxHypotenuse(new int[]{3}, new int[]{4, 40}), EPSILON);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(50.0, tasks.maxHypotenuse(new int[]{3, 30}, new int[]{4, 40}), EPSILON);
        assertEquals(10.0, tasks.maxHypotenuse(new int[]{3, 6}, new int[]{4, 8}), EPSILON);
        assertEquals(3 * Math.sqrt(2), tasks.maxHypotenuse(new int[]{1, 2, 3}, new int[]{1, 2, 3}), EPSILON);
        assertEquals(Math.sqrt(10), tasks.maxHypotenuse(new int[]{1, 2, 3}, new int[]{3, 2, 1}), EPSILON);
    }

    @Test
    public void fibonacci() {
        assertEquals(0, tasks.fibonacci(0));
        assertEquals(1, tasks.fibonacci(1));
        assertEquals(1, tasks.fibonacci(2));
        assertEquals(2, tasks.fibonacci(3));
        assertEquals(3, tasks.fibonacci(4));
        assertEquals(5, tasks.fibonacci(5));
        assertEquals(55, tasks.fibonacci(10));
        assertEquals(6765, tasks.fibonacci(20));
        assertEquals(12586269025L, tasks.fibonacci(50));
        assertEquals(2880067194370816120L, tasks.fibonacci(90));
    }

    @Test
    public void randomAverage() {
        try {
            tasks.randomAverage(10);
            tasks.randomAverage(1000000);
            assertEquals(0.0, tasks.randomAverage(0), EPSILON);
//			assertEquals(0.0, tasks.randomAverage(Integer.MAX_VALUE >> 4), 100000);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private Path getTestFilesPath() {
        try {
            Path testClassesPath = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
//            System.out.println(testClassesPath);
            Path testFilesPath = Paths.get(testClassesPath.toString(), "testfiles");
//            System.out.println(testFilesPath);
            return testFilesPath;
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void averageSize() {
        try {
            assertEquals(0, tasks.averageSize(new ArrayList<>()), EPSILON);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        Path root = getTestFilesPath();
        assertEquals(4, tasks.averageSize(Arrays.asList(root.resolve("recursive").toFile().listFiles())), EPSILON);
        assertEquals(8780.0/6, tasks.averageSize(Arrays.asList(root.toFile().listFiles())), EPSILON);
    }


    @Test
    public void naturalCountByPredicate() {
        assertEquals(0, tasks.naturalCountByPredicate(0, (x) -> x < 0));
        assertEquals(0, tasks.naturalCountByPredicate(4, (x) -> x > 8));
        assertEquals(7, tasks.naturalCountByPredicate(10, (x) -> x > 3));
        assertEquals(5, tasks.naturalCountByPredicate(25, (x) -> x > 20));
        assertEquals(3, tasks.naturalCountByPredicate(8, (x) -> (x > 2 && x <= 5)));
        assertEquals(100, tasks.naturalCountByPredicate(100, (x) -> true));
        assertEquals(0, tasks.naturalCountByPredicate(100, (x) -> false));
    }

    @Test
    public void naturalSpecialSeqSliceProduct() {
        assertEquals(BigInteger.ZERO, tasks.naturalSpecialSeqSliceProduct(2, 1));
        assertEquals(BigInteger.valueOf(4), tasks.naturalSpecialSeqSliceProduct(1, 2));
        assertEquals(BigInteger.valueOf(8), tasks.naturalSpecialSeqSliceProduct(2, 3));
        assertEquals(BigInteger.valueOf(194), tasks.naturalSpecialSeqSliceProduct(10, 15));
        assertEquals(BigInteger.valueOf(469), tasks.naturalSpecialSeqSliceProduct(10, 20));
        assertEquals(BigInteger.valueOf(179), tasks.naturalSpecialSeqSliceProduct(50, 50));
        assertEquals(BigInteger.valueOf(104081996055L), tasks.naturalSpecialSeqSliceProduct(12345, 54321));
    }

    @Test
    public void fN() {
        Function<Integer, Integer> f1 = (x) -> x + 1;
        assertEquals(13, (int)tasks.fN(f1, 13).apply(0));
        assertEquals(2, (int)tasks.fN(f1, 1).apply(1));
        assertEquals(13, (int)tasks.fN(f1, 0).apply(13));
        assertEquals(0, (int)tasks.fN(f1, 12345).apply(-12345));
        assertEquals(123456789, (int)tasks.fN(f1, 123456789).apply(0)); // *

        Function<Double, Double> f2 = (x) -> x/2;
        assertEquals(32.0, tasks.fN(f2, 5).apply(1024.0), EPSILON);
        assertEquals(100.0, tasks.fN(f2, 10).apply(102400.0), EPSILON);
        assertEquals(1.0, tasks.fN(f2, 2).apply(4.0), EPSILON);

        Function<Long, Long> f3 = (x) -> x*x;
        assertEquals(1, (long)tasks.fN(f3, 365).apply(1L));
        assertEquals(4294967296L, (long)tasks.fN(f3, 5).apply(2L));
        assertEquals(14641, (long)tasks.fN(f3, 2).apply(11L));
    }

    @Test
    public void shortestEven() {
        List<String> list = new ArrayList<>();
        assertNull(tasks.shortestEven(list));
        list.add("1");
        assertNull(tasks.shortestEven(list));
        list.add("abc");
        assertNull(tasks.shortestEven(list));
        list.add("qwertyqwerty");
        list.add("qwerty");
        list.add("123");
        assertEquals("qwerty", tasks.shortestEven(list));
        list.add("");
        list.add("121212");
        list.add("1234");
        list.add("123");
        assertEquals("", tasks.shortestEven(list));
        list.remove("");
        list.add("12");
        list.add("5678");
        assertEquals("12", tasks.shortestEven(list));
    }

    @Test
    public void countMatchingPairs() {
        List<String> list = new ArrayList<>();
        assertEquals(0, tasks.countMatchingPairs(list, String::equals));
        list.add("123");
        list.add("123");
        list.add("456");
        list.add("123");
        assertEquals(1, tasks.countMatchingPairs(list, String::equals));
        list.add("12345");
        list.add("1234");
        list.add("12345");
        list.add("12345");
        list.add("123456");
        assertEquals(7, tasks.countMatchingPairs(list, (a, b) -> a.length() <= b.length()));
        list.add("qwe");
        list.add("qwerty");
        list.add("q1w2e3r4t5y6");
        list.add("привет");
        list.add("hello");
        list.add("HELLO");
        assertEquals(1, tasks.countMatchingPairs(list, (a, b) -> a.matches("[a-z]*$") && b.matches("[a-z]*$")));
        assertEquals(5, tasks.countMatchingPairs(list, (a, b) -> a.matches("[a-z]*$") || b.matches("[a-z]*$")));
    }

    @Test
    public void concat() {
        List<String> list = new ArrayList<>();
        assertEquals("ps", tasks.concat(list, ", ", "p", "s"));
        list.add("qwerty");
        assertEquals("pqwertys", tasks.concat(list, ", ", "p", "s"));
        list.add("12345");
        assertEquals("[qwerty, 12345]", tasks.concat(list, ", ", "[", "]"));
        list.add("asdf");
        assertEquals("qwerty_12345_asdf", tasks.concat(list, "_", "", ""));
        list.add("");
        assertEquals("-qwerty 12345 asdf =", tasks.concat(list, " ", "-", "="));
    }

    @Test
    public void getPositiveRank() {
        assertEquals(0, tasks.getPositiveRank(new int[]{1, 2, 3, -1, -2, -3}));
        assertEquals(0, tasks.getPositiveRank(new int[]{}));
        assertEquals(1, tasks.getPositiveRank(new int[]{1}));
        assertEquals(-1, tasks.getPositiveRank(new int[]{-1}));
        assertEquals(1, tasks.getPositiveRank(new int[]{0}));
        assertEquals(1, tasks.getPositiveRank(new int[]{1, 2, 3, 4, 5, -5, -4, -3, -2}));
        assertEquals(1, tasks.getPositiveRank(new int[]{1, 1, 1, 1, 1, -100}));
    }

    @Test
    public void totalMondaysByMonth() {
        Map<Month, Long> map = new EnumMap<>(Month.class);
        assertEquals(map, tasks.totalMondaysByMonth(2018, 2017));

        map.put(Month.JANUARY, 5L);
        map.put(Month.FEBRUARY, 4L);
        map.put(Month.MARCH, 4L);
        map.put(Month.APRIL, 5L);
        map.put(Month.MAY, 4L);
        map.put(Month.JUNE, 4L);
        map.put(Month.JULY, 5L);
        map.put(Month.AUGUST, 4L);
        map.put(Month.SEPTEMBER, 4L);
        map.put(Month.OCTOBER, 5L);
        map.put(Month.NOVEMBER, 4L);
        map.put(Month.DECEMBER, 5L);
        assertEquals(map, tasks.totalMondaysByMonth(2018, 2018));

        map.clear();
        map.put(Month.JANUARY, 9L);
        map.put(Month.FEBRUARY, 8L);
        map.put(Month.MARCH, 8L);
        map.put(Month.APRIL, 10L);
        map.put(Month.MAY, 8L);
        map.put(Month.JUNE, 8L);
        map.put(Month.JULY, 10L);
        map.put(Month.AUGUST, 8L);
        map.put(Month.SEPTEMBER, 9L);
        map.put(Month.OCTOBER, 9L);
        map.put(Month.NOVEMBER, 8L);
        map.put(Month.DECEMBER, 10L);
        assertEquals(map, tasks.totalMondaysByMonth(2018, 2019));
    }

    @Test
    public void friday13s() {
        // april, august, december, february, january, july, june, march, may, november, october, september
        List<LocalDate> list = new ArrayList<>();
        assertEquals(list, tasks.friday13s(2018, 2017));

        list.add(LocalDate.of(2018, 4, 13));
        list.add(LocalDate.of(2018, 7, 13));
        assertEquals(list, tasks.friday13s(2018, 2018));

        list.add(LocalDate.of(2019, 12, 13));
        list.add(LocalDate.of(2019, 9, 13));
        assertEquals(list, tasks.friday13s(2018, 2019));
    }

    @Test
    public void writeAllCountingBytesTransferred() {
        try (Writer writer = new FileWriter("target/file.txt", false)) {
            List<String> list = new ArrayList<>();
            assertEquals(0, tasks.writeAllCountingBytesTransferred(writer, list));

            list.add("");   			// no
            list.add(" ");   			// no
            list.add("!!!");			// no
            list.add("aZ");     		// yes
            list.add("HelloWorld");		// yes
            list.add("HelloWorld1");	// yes
            list.add("HelloWorld!");	// no
            list.add("Hello 123");	    // no
            list.add("123");            // yes
            list.add("1a2b3r4a5cadabra"); // yes
            list.add("Привет");			// no
            list.add("Ахалай Махалай");	// no
            assertEquals(42, tasks.writeAllCountingBytesTransferred(writer, list));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        try (Writer writer = new Writer() {
            int cnt = 0;

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
            }

            @Override
            public void write(String str) throws InterruptedIOException {
                if (++cnt == 3) {
                    InterruptedIOException e = new InterruptedIOException("oups");
                    e.bytesTransferred = str.length() / 2;
                    throw e;
                }
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public void close() throws IOException {
                cnt = 0;
            }
        }) {
            List<String> list = new ArrayList<>();
            assertEquals(0, tasks.writeAllCountingBytesTransferred(writer, list));

            list.add("");   			// no
            list.add(" ");   			// no
            list.add("!!!");			// no
            assertEquals(0, tasks.writeAllCountingBytesTransferred(writer, list));

            list.add("aZ");     		// yes
            list.add("HelloWorld");		// yes
            assertEquals(12, tasks.writeAllCountingBytesTransferred(writer, list));

            writer.close();
            list.add("HelloWorld1");	// yes
            assertEquals(17, tasks.writeAllCountingBytesTransferred(writer, list));

            writer.close();
            list.add("123");            // yes
            // writeAllCountingBytesTransferred should stop on first exception
            assertEquals(17, tasks.writeAllCountingBytesTransferred(writer, list));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void crc32() throws IOException {
        assertEquals(4010479782L, tasks.crc32(getTestFilesPath()));
        assertEquals(3632233996L, tasks.crc32(getTestFilesPath().resolve("recursive")));
        try {
            tasks.crc32(getTestFilesPath().resolve("nonexistent"));
            fail("should throw on non existent dir");
        } catch (NoSuchFileException ok) {
        }
    }

    @Test
    public void fileSizes() throws IOException {
        Path root = getTestFilesPath();
        Path p1 = root.resolve("test11.txt");
        Path p2 = root.resolve("test12.txt");
        Path p3 = root.resolve("test21.txt");
        Path p4 = root.resolve("test22.txt");
        Path p5 = root.resolve("test23.test");
        Path p6 = root.resolve("test24.test");
        Path pRec = root.resolve("recursive/test.txt");
        Path pDeepRec = root.resolve("recursive/deep/deeper/deeptest.txt");
        Map<Path, Long> allFiles = new TreeMap<>();
        allFiles.put(p1, 100L);
        allFiles.put(p2, 1000L);
        allFiles.put(p3, 512L);
        allFiles.put(p4, 1024L);
        allFiles.put(p5, 2048L);
        allFiles.put(p6, 4096L);
        allFiles.put(p6, 4096L);
        Map<Path, Long> allfilesRecursive = new TreeMap<>(allFiles);
        allfilesRecursive.put(pRec, 4L);
        allfilesRecursive.put(pDeepRec, 8L);

        assertEquals(allfilesRecursive, tasks.fileSizes(root, "*", true));
        assertEquals(Collections.emptyMap(), tasks.fileSizes(root, "", true));
        assertEquals(Collections.emptyMap(), tasks.fileSizes(root, "", false));

        Map<Path, Long> part = new TreeMap<>(allFiles);
        part.remove(p5);
        part.remove(p6);
        assertEquals(part, tasks.fileSizes(root, "*.txt", false));

        part = new TreeMap<>(allFiles);
        part.remove(p1);
        part.remove(p2);
        assertEquals(part, tasks.fileSizes(root, "test2?.*", true));

        assertEquals(ImmutableMap.of(p6, p6.toFile().length()), tasks.fileSizes(root, "*4.t??t", true));
    }

    @Test
    public void recaman() {
        assertEquals(0, tasks.recaman(0));
        assertEquals(1, tasks.recaman(1));
        assertEquals(3, tasks.recaman(2));
        assertEquals(6, tasks.recaman(3));
        assertEquals(2, tasks.recaman(4));
        assertEquals(7, tasks.recaman(5));
        assertEquals(13, tasks.recaman(6));
        assertEquals(20, tasks.recaman(7));
        assertEquals(12, tasks.recaman(8));
        assertEquals(21, tasks.recaman(9));
        assertEquals(11, tasks.recaman(10));
        assertEquals(24, tasks.recaman(15));
        assertEquals(42, tasks.recaman(20));
        assertEquals(45, tasks.recaman(30));
        assertEquals(38, tasks.recaman(40));
        assertEquals(33, tasks.recaman(50));
        assertEquals(28, tasks.recaman(60));
        assertEquals(155, tasks.recaman(70));
        assertEquals(3686, tasks.recaman(1000));
        assertEquals(18658, tasks.recaman(10000));
        assertEquals(199508, tasks.recaman(100000));
        assertEquals(2057164, tasks.recaman(1000000));
        //assertEquals(20438710, tasks.recaman(10000000)); // *
        //assertEquals(203701556, tasks.recaman(100000000)); // **
    }

}
