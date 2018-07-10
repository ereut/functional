package ru.intervale.courses.fp;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
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
        return maxHyp;
    }

    @Override
    public long fibonacci(int n) {

        if (n <= 1) {
            return 0;
        }

        int a = 0;
        int b = 1;

        for (int i = 2; i < n; i++) {
            int next = a + b;
            a = b;
            b = next;
        }

        return b;
    }

    @Override
    public double randomAverage(int n) {

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += new Random().nextInt();
        }
        return (double) sum / n;
    }

    @Override
    public double averageSize(List<File> files) {

        long sumFileSize = 0;
        for (File file : files) {
            sumFileSize += file.length();
        }
        return sumFileSize / files.size();
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

        int sum = 0;
        for (int i = m; i <= n; i++) {
            if (String.valueOf(i).matches("[13579]+")) {
                sum += i;
            }
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
        sb.deleteCharAt(sb.length() - 1);
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
        LocalDate currentDate = startLocalDate;

        while (currentDate.isBefore(finishLocalDate)) {
            currentDate = currentDate.plusDays(1);
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
        LocalDate currentDate = startLocalDate;
        List<LocalDate> friday13sList = new ArrayList<>();

        while (currentDate.isBefore(finishLocalDate)) {
            currentDate = currentDate.plusDays(1);
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
                } catch (IOException e) {
                    break;
                }
            }
        }
        return bytesSize;
    }

    private void readDirAndAddToSet(Set<File> set, File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                readDirAndAddToSet(set, file);
            }
            set.add(file);
        }
    }

    @Override
    public long crc32(Path dir) throws IOException {

        CRC32 crc32 = new CRC32();
        Set<File> filesSet = new TreeSet<>(Comparator.comparing(File::getName));

        for (File file : dir.toFile().listFiles()) {
            if (file.isDirectory()) {
                readDirAndAddToSet(filesSet, file);
            } else {
                filesSet.add(file);
            }
        }

        for (File file : filesSet) {
            crc32.update(Files.readAllBytes(file.toPath()));
        }
        return crc32.getValue();
    }

    private void readDirAndPutInMap(Map<Path, Long> map, File dir, String mask) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                readDirAndPutInMap(map, file, mask);
            }
            if (file.getName().matches(mask)) {
                map.put(file.toPath(), file.length());
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
                System.out.println(file.getName());
                if (file.getName().matches(mask)) {
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

        for (long i = 1; i < n; i++) {
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
