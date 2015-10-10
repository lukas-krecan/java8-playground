/**
 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.streams;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;

public class ParallelPerfTest {



    private static long runTask(int limit) {
        return range(1, limit).parallel().filter(ParallelPerfTest::isPrime).count();
    }

    public static boolean isPrime(long n) {
        return n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }


    @GenerateMicroBenchmark
    @BenchmarkMode(value = Mode.AverageTime)
    public void directExecution() {
        runTask(100_000);
    }

//    @GenerateMicroBenchmark
//    @BenchmarkMode(value = Mode.AverageTime)
//    public void inForkJoinPool() throws InterruptedException, ExecutionException, TimeoutException {
//        ForkJoinPool fjp = new ForkJoinPool();
//        ForkJoinTask<Long> task = fjp.submit(() -> runTask(100_000));
//        task.get(1, TimeUnit.SECONDS);
//        fjp.shutdown();
//     }

    public static void main(String... args) {
        Main.main(args);
    }
}
