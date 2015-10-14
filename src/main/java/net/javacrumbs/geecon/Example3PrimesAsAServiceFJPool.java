/**
 * Copyright 2009-2013 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.geecon;

import static java.lang.Math.sqrt;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;
import static net.javacrumbs.common.Utils.sleep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Example3PrimesAsAServiceFJPool {
    private final ForkJoinPool fjPool = new ForkJoinPool(4);

    public static void main(String[] args) throws InterruptedException {
        new Example3PrimesAsAServiceFJPool().doRun();
    }

    private void doRun() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        // simulating multiple requests
        measure(() -> {
            executor.submit(() -> countPrimes(1_000_000, Integer.MAX_VALUE));
            executor.submit(() -> countPrimes(1_000_000, 2_000_000));
            executor.submit(() -> countPrimes(1_000_000, 2_000_000));
            executor.submit(() -> countPrimes(1_000_000, 2_000_000));

            executor.submit(() -> countPrimes(1_000_000, 2_000_000));
            executor.submit(() -> countPrimes(1_000_000, 2_000_000));
            executor.submit(() -> countPrimes(1_000_000, 2_000_000));

            sleep(50); //nasty
            executor.shutdown();
            fjPool.shutdown();
            try {
                executor.awaitTermination(60, SECONDS);
                fjPool.awaitTermination(60, SECONDS);
            } catch (InterruptedException ignore) {
            }
        }, time -> System.out.println("Total time: " + time));
    }

    private long countPrimes(int from, int to) {
        Future<Long> future = fjPool.submit(() -> doCountPrimes(from, to));
        try {
            return future.get(10, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true); // Does not work
            e.printStackTrace();
            throw new PrimeCalculationException(e);
        }
    }

    private long doCountPrimes(int from, int to) {
        return measure(() -> {
            long result = range(from, to)
                    .parallel()
                    .filter(this::isPrime)
                    .count();
            log(result);
            return result;
        });
    }


    public boolean isPrime(int n) {
        return n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }

    private static class PrimeCalculationException extends RuntimeException {
        public PrimeCalculationException(Throwable cause) {
            super(cause);
        }
    }
}

