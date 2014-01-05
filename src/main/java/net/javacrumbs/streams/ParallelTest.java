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

import net.javacrumbs.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.util.Utils.log;
import static net.javacrumbs.util.Utils.measure;

public class ParallelTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).peek(i -> Utils.sleep(1000)).max()));
        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).max()));
        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).max()));
        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).max()));
        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).max()));
        es.execute(()-> measure(() -> createStream().filter(ParallelTest::isPrime).max()));


        es.shutdown();
        es.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }

    private static IntStream createStream() {
        return range(1, 1_000_000).parallel();
    }

    public static boolean isPrime(long n) {
        return n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }

}
