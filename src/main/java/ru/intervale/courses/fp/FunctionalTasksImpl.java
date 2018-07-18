package ru.intervale.courses.fp;

import one.util.streamex.StreamEx;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.zip.CRC32;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Реализации в функциональном стиле
 */
public class FunctionalTasksImpl implements Tasks {

    @Override
    public int sum(int[] array) {
        return Arrays.stream(array).sum();
    }

    @Override
    public Double maxHypotenuse(int[] a, int[] b) {

        int len = Math.min(a.length, b.length);
        return IntStream.range(0, len)
                .mapToDouble(i -> Math.sqrt(Math.pow(a[i], 2) + Math.pow(b[i], 2)))
                .max()
                .orElse(0);
    }

    @Override
    public long fibonacci(int n) {
        return Stream.iterate(new long[] {1, 1}, f -> new long[] {f[1], f[0] + f[1]})
                .limit(n)
                .reduce((a, b) -> b)
                .map(v -> v[0])
                .orElse((long) 0);
    }

    @Override
    public double randomAverage(int n) {
        return new Random().ints()
                .limit(n)
                .average()
                .orElse(0);
    }

    @Override
    public double averageSize(List<File> files) {
        return files.stream()
                .filter(File::isFile)
                .mapToLong(File::length)
                .average()
                .orElse(0);
    }

    @Override
    public int naturalCountByPredicate(int n, IntPredicate predicate) {
        return (int) IntStream
                .rangeClosed(1, n)
                .filter(predicate::test)
                .count();
    }

    @Override
    public BigInteger naturalSpecialSeqSliceProduct(int m, int n) {

        return new BigInteger(String.valueOf(
               IntStream.iterate(1, x -> x + 1)
                .filter(num -> String.valueOf(num).matches("[13579]+"))
                .limit(n)
                .skip(m - 1)
                .sum()));

    }

    @Override
    public <T> Function<T, T> fN(Function<T, T> f, int n) {
        return t -> Stream.iterate(f.apply(t), f::apply)
                .limit(n)
                .reduce((a, b) -> b)
                .orElse(t);
    }

    @Override
    public String shortestEven(List<String> list) {
        return list.stream()
                .filter(s -> s.length() % 2 == 0)
                .min(Comparator.comparing(String :: length)).orElse(null);
    }

    @Override
    public int countMatchingPairs(List<String> sequence, BiPredicate<String, String> predicate) {

        return (int) StreamEx.of(sequence)
                .pairMap((val1, val2) -> new String[] {val1, val2})
                .filter(arr -> predicate.test(arr[0], arr[1]))
                .count();
    }

    @Override
    public String concat(List<String> list, String delimiter, String prefix, String suffix) {
        return list
                .stream()
                .collect(Collectors.joining(delimiter, prefix, suffix));
    }

    @Override
    public int getPositiveRank(int[] numbers) {
        return new Integer(Arrays.stream(numbers)
                .map(num -> num >= 0 ? 1 : -1)
                .sum()).compareTo(0);
    }

    @Override
    public EnumMap<Month, Long> totalMondaysByMonth(int yearSince, int yearUntilInclusive) {
        LocalDate startLocalDate = LocalDate.ofYearDay(yearSince, 1);
        LocalDate finishLocalDate = LocalDate.ofYearDay(yearUntilInclusive, 1).
                with(TemporalAdjusters.lastDayOfYear());
        long daysBetween = DAYS.between(startLocalDate, finishLocalDate);

        return LongStream.rangeClosed(0, daysBetween)
                .mapToObj(startLocalDate::plusDays)
                .filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY)
                .collect(Collectors.groupingBy(
                        LocalDate::getMonth,
                        () -> new EnumMap<>(Month.class),
                        Collectors.counting()));
    }

    @Override
    public List<LocalDate> friday13s(int yearFrom, int yearUntilInclusive) {
        LocalDate startLocalDate = LocalDate.ofYearDay(yearFrom, 1);
        LocalDate finishLocalDate = LocalDate.ofYearDay(yearUntilInclusive, 1).
                with(TemporalAdjusters.lastDayOfYear());
        long daysBetween = DAYS.between(startLocalDate, finishLocalDate);

        return LongStream.rangeClosed(0, daysBetween)
                .mapToObj(startLocalDate::plusDays)
                .filter(day -> day.getDayOfMonth() == 13 && day.getDayOfWeek() == DayOfWeek.FRIDAY)
                .sorted(Comparator.comparing(LocalDate::getYear)
                        .thenComparing(Comparator.comparing(o -> o.getMonth().name())))
                .collect(Collectors.toList());
    }

    @Override
    public int writeAllCountingBytesTransferred(Writer writer, List<String> list) {

        List<String> filterList = list.stream()
                .filter(str -> str.matches("^[a-zA-Z0-9]+$"))
                .collect(Collectors.toList());
        int bytes = 0;

        for (String str : filterList) {
            try {
                writer.write(str);
                bytes += str.getBytes().length;
            } catch (InterruptedIOException e) {
                bytes += e.bytesTransferred;
                break;
            } catch (IOException e) {
                break;
            }
        }
        return bytes;
    }

    @Override
    public long crc32(Path dir) throws IOException {

        CRC32 crc32 = new CRC32();

        Files.walk(dir, 1)
                .filter(Files::isRegularFile)
                .sorted(Comparator.comparing(path -> path.toFile().getName()))
                .forEach(file -> {
                    try {
                        crc32.update(Files.readAllBytes(file));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        return crc32.getValue();
    }

    @Override
    public Map<Path, Long> fileSizes(Path dir, String mask, boolean recursive) throws IOException {

        return Files.walk(dir, recursive ? Integer.MAX_VALUE : 1)
                .filter(Files::isRegularFile)
                .filter(path -> path.toFile().getName().matches(mask
                        .replace(".", "\\.")
                        .replace("?", ".{1}")
                        .replace("*", ".*")))
                .collect(Collectors.toMap(
                        path -> (Path) path,
                        path -> path.toFile().length()
                ));
    }

    @Override
    public long recaman(long n) {

        Set<Long> set = new HashSet<>();

        return Stream.iterate(new long[] {1, 1}, f -> new long[] {f[0] + 1, f[1] - f[0] - 1 > 0
                && !set.contains(f[1] - f[0] - 1) ? f[1] - f[0] - 1 : f[1] + f[0] + 1})
                .limit(n)
                .peek(f -> set.add(f[1]))
                .reduce((a, b) -> b)
                .map(v -> v[1])
                .orElse((long) 0);
    }

}
