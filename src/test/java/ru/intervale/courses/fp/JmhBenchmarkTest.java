package ru.intervale.courses.fp;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JmhBenchmarkTest {

    @SuppressWarnings("PackageVisibleField")
    @State(Scope.Benchmark)
    public static class St {
        Tasks functional = new FunctionalTasksImpl();
        Tasks imperative = new ImperativeTasksImpl();
        Random random = new Random();
        int[] intArray = random.ints().limit(100000).toArray();
    }

    @Test
    public void runTest() throws Exception {
        Options opt = initBench();
        Collection<RunResult> results = runBench(opt);
        assertOutputs(results);
    }

    @Benchmark
    public int functionalSum(St st) {
        return st.functional.sum(st.intArray);
    }

    @Benchmark
    public int imperativeSum(St st) {
        return st.imperative.sum(st.intArray);
    }

    // TODO: приготовить сравнительные бечмарки остальных интересных методов


    private Options initBench() {
        return new OptionsBuilder()
                .include(getClass().getSimpleName() + ".*")
                .mode(Mode.AverageTime)
                .verbosity(VerboseMode.EXTRA)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(3)
                .threads(1)
                .warmupIterations(2)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(1)
                .build();
        // TODO: alter params to make higher Score to Error ratio in benchmark results
    }

    private Collection<RunResult> runBench(Options opt) throws RunnerException {
        return new Runner(opt).run();
    }

    private void assertOutputs(Collection<RunResult> results) {
        double AVERAGE_EXPECTED_TIME = 1000;
        for (RunResult r : results) {
            for (BenchmarkResult rr : r.getBenchmarkResults()) {
                Mode mode = rr.getParams().getMode();
                double score = rr.getPrimaryResult().getScore();
                String methodName = rr.getPrimaryResult().getLabel();

                Assert.assertEquals("Test mode is not average mode. Method = " + methodName,
                        Mode.AverageTime, mode);
                Assert.assertTrue("Benchmark score = " + score + " is higher than " + AVERAGE_EXPECTED_TIME + " " + rr.getScoreUnit() + ". Too slow performance!",
                        score < AVERAGE_EXPECTED_TIME);
            }
        }
    }
}