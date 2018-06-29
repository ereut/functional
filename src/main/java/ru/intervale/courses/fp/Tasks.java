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

public interface Tasks {

    /**
     * Найти сумму элементов массива
     */
    int sum(int[] array);

    /**
     * Даны массивы с катетами прямоугольных треугольников.
     * Найти самую большую гипотенузу.
     */
    Double maxHypotenuse(int[] a, int[] b);

    /**
     * Вычислить n-ное число Фибоначчи
     */
    long fibonacci(int n);

    /**
     * Вычислить среднее значение последовательности из n случайных целых чисел
     */
    double randomAverage(int n);

    /**
     * Вычислить средний размер файлов в списке
     */
    double averageSize(List<File> files);

    /**
     * Посчитать число натуральных чисел от 1 до n, удовлетворяющих заданному предикату
     */
    int naturalCountByPredicate(int n, IntPredicate predicate);

    /**
     * Вычислить сумму с m-того по n-ный включительно элементов последовательности натуральных чисел, у которых все цифры нечётные
     */
    BigInteger naturalSpecialSeqSliceProduct(int m, int n);

    /**
     * Вернуть функцию, которая возвращает результат применения функции f к своему результату n раз подряд
     */
    <T> Function<T, T> fN(Function<T, T> f, int n);

    // TODO: monads

    /**
     * Найти самую короткую строку чётной длины из списка
     */
    String shortestEven(List<String> list);

    /**
     * Посчитать число пар соседних элементов в заданной последовательности, удовлетворяющих заданному предикату
     */
    int countMatchingPairs(List<String> sequence, BiPredicate<String, String> predicate);

    /**
     * Склеить все строки в одну через заданный разделитель с заданным префиксом и суффиксом
     */
    String concat(List<String> list, String delimiter, String prefix, String suffix);

    /**
     * Определить, каких чисел больше в заданной последовательносоти целых чисел, положительных или отрицательных.
     * @return -1 - больше отрицательных, 0 - поровну, 1 - больше положительных
     */
    int getPositiveRank(int[] numbers);

    /**
     * Посчитать общее число понедельников за заданные годы с группировкой по месяцам
     */
    EnumMap<Month, Long> totalMondaysByMonth(int yearSince, int yearUntilInclusive);

    /**
     * Выдать список пятниц 13-го за заданные годы,
     * отсортированный по возрастанию в первую очередь по году, а во вторую - по лексикографическому английскому названию месяца
     */
    List<LocalDate> friday13s(int yearFrom, int yearUntilInclusive);

    /**
     * Вывести в заданный writer все строки из списка, которые состоят только из латинских букв или цифр
     * @return Число успешно записанных байт до первого исключения с учётом {@link java.io.InterruptedIOException#bytesTransferred}
     */
    int writeAllCountingBytesTransferred(Writer writer, List<String> list);

    /**
     * Посчитать хэш CRC32 содержимого всех файлов в заданном каталоге, склеенных в алфавитном порядке.
     * IOException пропускать наверх.
     */
    long crc32(Path dir) throws IOException;

    /**
     * Вернуть карту с размерами файлов в заданном каталоге, имена которых удовлетворяют заданной маске.
     * Маска в формате DOS может содержать спецсимволы '*' и '?'.
     */
    Map<Path, Long> fileSizes(Path dir, String mask, boolean recursive) throws IOException;

    /**
     * Посчитать n-ый элемент последовательности Рекамана.
     * Задание со звёздочкой. За самую эффективную реализацию будет конфетка. )
     * Последовательность можно послушать: http://oeis.org/play?seq=A005132
     */
    long recaman(long n);

    // TODO: Можно добавлять свои показательные задачи по ещё не раскрытым здесь методам и нюансам. За бонус )

    default UnsupportedOperationException TODO() {
        throw new UnsupportedOperationException("TODO");
    }
}
