package ru.intervale.courses.fp;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;

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
        throw TODO();
    }

    @Override
    public long fibonacci(int n) {
        throw TODO();
    }

    @Override
    public double randomAverage(int n) {
        throw TODO();
    }

    @Override
    public double averageSize(List<File> files) {
        throw TODO();
    }

    @Override
    public int naturalCountByPredicate(int n, IntPredicate predicate) {
        throw TODO();
    }

    @Override
    public BigInteger naturalSpecialSeqSliceProduct(int m, int n) {
        throw TODO();
    }

    @Override
    public <T> Function<T, T> fN(Function<T, T> f, int n) {
        throw TODO();
    }

    @Override
    public String shortestEven(List<String> list) {
        throw TODO();
    }

    @Override
    public int countMatchingPairs(List<String> sequence, BiPredicate<String, String> predicate) {
        throw TODO();
    }

    @Override
    public String concat(List<String> list, String delimiter, String prefix, String suffix) {
        throw TODO();
    }

    @Override
    public int getPositiveRank(int[] numbers) {
        throw TODO();
    }

    @Override
    public EnumMap<Month, Long> totalMondaysByMonth(int yearSince, int yearUntilInclusive) {
        throw TODO();
    }

    @Override
    public List<LocalDate> friday13s(int yearFrom, int yearUntilInclusive) {
        throw TODO();
    }

    @Override
    public int writeAllCountingBytesTransferred(Writer writer, List<String> list) {
        throw TODO();
    }

    @Override
    public long crc32(Path dir) throws IOException {
        throw TODO();
    }

    @Override
    public Map<Path, Long> fileSizes(Path dir, String mask, boolean recursive) throws IOException {
        throw TODO();
    }

    @Override
    public long recaman(long n) {
        throw TODO();
    }
}
