package ru.intervale.courses.fp;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.zip.CRC32;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Реализации в императивном стиле
 */
public class ImperativeTasksImpl implements Tasks {

    @Override
    public int sum(int[] array) {
        int sum = 0;
        for (int v : array) {
            sum += v;
        }
        return sum;
    }

    @Override
    public Double maxHypotenuse(int[] a, int[] b) {

        double maxHyp = 0;
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            double currentHyp = Math.sqrt(Math.pow(a[i], 2) + Math.pow(b[i], 2));

            if (currentHyp > maxHyp ) {
                maxHyp = currentHyp;
            }
        }
        return maxHyp == 0 ? null : maxHyp;
    }

    @Override
    public long fibonacci(int n) {

        if (n == 0) {
            return 0;
        }

        long a = 0;
        long b = 1;

        for (int i = 1; i < n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }

    @Override
    public double randomAverage(int n) {

        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += new Random().nextInt();
        }
        return n != 0 ? sum / n : 0;
    }

    @Override
    public double averageSize(List<File> files) {

        if (files == null || files.isEmpty()) {
            return 0;
        }

        int count = 0;

        double sumFileSize = 0;
        for (File file : files) {
            if (file.isFile()) {
                sumFileSize += file.length();
                count++;
            } else {
                continue;
            }
        }
        return sumFileSize / count;
    }

    @Override
    public int naturalCountByPredicate(int n, IntPredicate predicate) {

        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (predicate.test(i)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public BigInteger naturalSpecialSeqSliceProduct(int m, int n) {

        if (n < m) {
            return BigInteger.ZERO;
        }

        List<Long> seqList = new ArrayList<>();
        Long sum = (long) 0;

        for (long i = 0; seqList.size() < n; i++) {
            if (String.valueOf(i).matches("[13579]+")) {
                seqList.add(i);
            }
        }
        seqList = seqList.subList(m - 1, n);
        for (Long num : seqList) {
            sum += num;
        }
        return new BigInteger(String.valueOf(sum));
    }

    @Override
    public <T> Function<T, T> fN(Function<T, T> f, int n) {
        return t -> {
            T res = t;
            for (int i = 0; i < n; i++) {
                res = f.apply(res);
            }
            return res;
        };
    }

    @Override
    public String shortestEven(List<String> list) {

        if (list == null || list.size() == 0) {
            return null;
        }

        Collections.sort(list, Comparator.comparing(String::length));
        String minLengthString = null;

        for (String str : list) {
            if (str == null) {
                continue;
            }
            if (str.length() % 2 == 0) {
                minLengthString = str;
                break;
            }
        }
        return minLengthString;
    }

    @Override
    public int countMatchingPairs(List<String> sequence, BiPredicate<String, String> predicate) {

        int count = 0;
        for (int i = 0; i < sequence.size() - 1; i++) {
            if (predicate.test(sequence.get(i), sequence.get(i + 1))) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String concat(List<String> list, String delimiter, String prefix, String suffix) {

        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        for (String str : list) {
            sb.append(str);
            sb.append(delimiter);
        }
        if (!list.isEmpty()) {
            sb.delete(sb.lastIndexOf(delimiter), sb.length());
        }
        sb.append(suffix);
        return sb.toString();
    }

    @Override
    public int getPositiveRank(int[] numbers) {

        int positiveCount = 0;
        int negativeCount = 0;

        for (int number : numbers) {

            if (number < 0) {
                negativeCount++;
            } else {
                positiveCount++;
            }
        }
        return new Integer(positiveCount).compareTo(negativeCount);
    }

    @Override
    public EnumMap<Month, Long> totalMondaysByMonth(int yearSince, int yearUntilInclusive) {

        EnumMap<Month, Long> mondaysMap = new EnumMap<>(Month.class);
        LocalDate startLocalDate = LocalDate.ofYearDay(yearSince, 1);
        LocalDate finishLocalDate =
                LocalDate.ofYearDay(yearUntilInclusive, 1).with(TemporalAdjusters.lastDayOfYear());
        long daysBetween = DAYS.between(startLocalDate, finishLocalDate);

        for (int i = 0; i <= daysBetween; i++) {
            LocalDate currentDate = startLocalDate.plusDays(i);
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                Month currentMonth = currentDate.getMonth();
                Long count = mondaysMap.get(currentMonth);
                mondaysMap.put(currentMonth, count == null ? 1 : count + 1);
            }
        }
        return mondaysMap;
    }

    @Override
    public List<LocalDate> friday13s(int yearFrom, int yearUntilInclusive) {

        LocalDate startLocalDate = LocalDate.ofYearDay(yearFrom, 1);
        LocalDate finishLocalDate =
                LocalDate.ofYearDay(yearUntilInclusive, 1).with(TemporalAdjusters.lastDayOfYear());
        List<LocalDate> friday13sList = new ArrayList<>();
        long daysBetween = DAYS.between(startLocalDate, finishLocalDate);

        for (int i = 0; i <= daysBetween; i++) {
            LocalDate currentDate = startLocalDate.plusDays(i);
            if (currentDate.getDayOfMonth() == 13 && currentDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                friday13sList.add(currentDate);
            }
        }

        friday13sList.sort((o1, o2) -> {
            int yearsDifference = o1.getYear() - o2.getYear();
            return yearsDifference != 0 ?
                    yearsDifference : o1.getMonth().name().compareTo(o2.getMonth().name());
        });

        return friday13sList;
    }

    @Override
    public int writeAllCountingBytesTransferred(Writer writer, List<String> list) {

        int bytesSize = 0;
        for (String str : list) {
            if (str.matches("^[a-zA-Z0-9]+$")) {
                try {
                    writer.write(str);
                    bytesSize += str.getBytes().length;
                } catch (InterruptedIOException e) {
                    bytesSize += e.bytesTransferred;
                    break;
                } catch (IOException e) {
                    break;
                }
            }
        }
        return bytesSize;
    }

    @Override
    public long crc32(Path dir) throws IOException {

        CRC32 crc32 = new CRC32();
        Set<File> filesSet = new TreeSet<>(Comparator.comparing(File::getName));

        if (!Files.exists(dir)) {
            throw new NoSuchFileException(dir.getFileName().toString());
        }

        for (File file : dir.toFile().listFiles()) {
           if (file.isFile()) {
               filesSet.add(file);
           } else {
               continue;
           }
        }
        for (File file : filesSet) {
            crc32.update(Files.readAllBytes(file.toPath()));
        }
        return crc32.getValue();
    }

    private String getRegexpFromMask(String mask) {

        return mask
                .replace(".", "\\.")
                .replace("?", ".{1}")
                .replace("*", ".*");
    }

    private void readDirAndPutInMap(Map<Path, Long> map, File dir, String mask) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                readDirAndPutInMap(map, file, mask);
            } else {
                if (file.getName().matches(getRegexpFromMask(mask))) {
                    map.put(file.toPath(), file.length());
                }
            }
        }
    }

    @Override
    public Map<Path, Long> fileSizes(Path dir, String mask, boolean recursive) throws IOException {

        Map<Path, Long> map = new HashMap<>();

        for (File file : dir.toFile().listFiles()) {
            if (file.isDirectory()) {
                if(recursive) {
                    readDirAndPutInMap(map, file, mask);
                } else {
                    continue;
                }
            } else {

                if (file.getName().matches(getRegexpFromMask(mask))) {
                    map.put(file.toPath(), file.length());
                }
                }
            }
            return map;
    }

    @Override
    public long recaman(long n) {

        Set<Long> set = new HashSet<>();
        long count = 0;
        long element = 0;

        for (long i = 0; i < n; i++) {
            count++;
            if (element - count > 0 && !set.contains(element - count)) {
                element -= count;
                set.add(element);
            } else {
                element += count;
                set.add(element);
            }
        }
        return element;
    }

}
