package ru.intervale.courses.fp;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.time.Month;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
public abstract class AbstractTasksTest {

    private Tasks tasks = getTasks();

    protected abstract Tasks getTasks();

    @Test
    public void sum() {
        assertEquals(0, tasks.sum(new int[]{}));
        assertEquals(42, tasks.sum(new int[]{42}));
        assertEquals(-2, tasks.sum(new int[]{-5, 0, 1, 2}));
    }

    @Test
    public void maxHypotenuse() throws Exception {
        int a[] = new int[]{12, 10, 3, 48};
        int b[] = new int[]{3, 5};
        assertEquals(12.4, tasks.maxHypotenuse(a, b), 0.1);
        int c[] = new int[]{5, 3, 2};
        int d[] = new int[]{10, 10, 10};
        assertEquals(11.2, tasks.maxHypotenuse(c,d), 0.1);
    }

    @Test
    public void fibonacci() throws Exception {
        assertEquals(0, tasks.fibonacci(0));
        assertEquals(1, tasks.fibonacci(1));
        assertEquals(34, tasks.fibonacci(9));
    }

    @Test
    public void randomAverage() throws Exception {
        double averageValue = tasks.randomAverage(10_000_000);
        assertTrue(averageValue < 1_000_000 && averageValue > - 1_000_000);
    }

    @Test
    public void averageSize() throws Exception {
        File firstTestfile =
                new File("src/main/java/ru/intervale/courses/fp/FunctionalTasksImpl.java");
        File secondTestFile =
                new File("src/main/java/ru/intervale/courses/fp/ImperativeTasksImpl.java");
        assertTrue(tasks.averageSize(Arrays.asList(firstTestfile, secondTestFile)) > 0);
    }

    @Test
    public void naturalCountByPredicate() throws Exception {
        assertEquals(3, tasks.naturalCountByPredicate(10,
                value -> value % 3 == 0));
        assertEquals(5, tasks.naturalCountByPredicate(15,
                value -> value > 10));
    }

    @Test
    public void naturalSpecialSeqSliceProduct() throws Exception {
        assertEquals(new BigInteger("4"), tasks.naturalSpecialSeqSliceProduct(1, 2) );
        assertEquals(new BigInteger("27"), tasks.naturalSpecialSeqSliceProduct(4, 6));
    }

    @Test
    public void fN() throws Exception {
        Function<Integer, Integer> func = new ImperativeTasksImpl().<Integer>fN(f-> f + 1, 10);
        int res = func.apply(1);
        assertEquals(11, res);
    }

    @Test
    public void shortestEven() throws Exception {
        assertEquals("11", tasks.shortestEven(Arrays.asList("gfh", "asdf", "11")));
        assertEquals("", tasks.shortestEven(Arrays.asList("gfhwfw", "", "11")));
    }

    @Test
    public void countMatchingPairs() throws Exception {
        assertEquals(2, tasks.countMatchingPairs(Arrays.asList("11", "22", "333", "ddd"),
                (s, s2) -> s.length() == s2.length()));
        assertEquals(1, tasks.countMatchingPairs(Arrays.asList("11", "11", "333", "ddd"),
                (s, s2) -> s.equals(s2)));
    }

    @Test
    public void concat() throws Exception {
        assertEquals("<a;b;c>", tasks.concat(Arrays.asList("a", "b", "c"),
                ";", "<", ">"));
        assertEquals("/d-g%", tasks.concat(Arrays.asList("d", "g"), "-", "/", "%"));
    }

    @Test
    public void getPositiveRank() throws Exception {
        assertEquals(1, tasks.getPositiveRank(new int[]{1, 2, -1000, 10, 0}));
        assertEquals(-1, tasks.getPositiveRank(new int[]{1, -2, -1000, -10, 0}));
        assertEquals(0, tasks.getPositiveRank(new int[]{1, -2, -1000, -10, 0, 158}));
    }

    @Test
    public void totalMondaysByMonth() throws Exception {
        assertFalse(tasks.totalMondaysByMonth(2016,2016).isEmpty());
        assertEquals(new Long(5),
                tasks.totalMondaysByMonth(2018, 2018).get(Month.JULY));
    }

    @Test
    public void friday13s() throws Exception {
        assertEquals(1, tasks.friday13s(2016, 2016).size());
    }

    @Test
    public void writeAllCountingBytesTransferred() throws Exception {
        assertEquals(6, tasks.writeAllCountingBytesTransferred(new PrintWriter(System.out),
                Arrays.asList("111", "222")));
        assertEquals(0, tasks.writeAllCountingBytesTransferred(new PrintWriter(System.out),
                Arrays.asList("", "")));
    }

    @Test
    public void crc32() throws Exception {
        assertTrue(tasks.crc32(Paths.get("src/main/java/ru/intervale/courses/fp")) > 0);
    }

    @Test
    public void fileSizes() throws Exception {
        assertTrue(tasks.fileSizes(Paths.get("src/main/java/ru/intervale/courses"),
                "*", false).isEmpty());
        assertFalse(tasks.fileSizes(Paths.get("src/main/java/ru/intervale/courses"),
                "*", true).isEmpty());
    }

    @Test
    public void recaman() throws Exception {
        assertEquals(6, tasks.recaman(3));
        assertEquals(2, tasks.recaman(4));
        assertEquals(0, tasks.recaman(0));
    }

}
