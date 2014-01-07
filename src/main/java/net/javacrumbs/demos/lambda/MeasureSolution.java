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
package net.javacrumbs.demos.lambda;

import net.javacrumbs.streams.Primes;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.javacrumbs.common.Utils.log;

public class MeasureSolution {

    public static void main(String[] args) {
        measure(() -> isPrimePrint(1_854_527));
        System.out.println(measure(() -> isPrime(1_854_527)));
    }

    public static <T> void measure(Runnable runnable) {
        measure(runnable, time -> log("Time taken: " + time + "ms"));
    }


    public static <T> void measure(Runnable runnable, Consumer<Long> consumer) {
        long start = System.currentTimeMillis();
        runnable.run();
        consumer.accept(System.currentTimeMillis() - start);
    }

    public static <T> T measure(Supplier<T> supplier) {
        return measure(supplier, time -> log("Time taken: " + time + "ms"));
    }

    public static <T> T measure(Supplier<T> supplier, Consumer<Long> consumer) {
        long start = System.currentTimeMillis();
        T result = supplier.get();
        consumer.accept(System.currentTimeMillis() - start);
        return result;
    }

    private static void isPrimePrint(long number) {
        System.out.println(Primes.isPrime(number));
    }

    private static boolean isPrime(long number) {
        return Primes.isPrime(number);
    }
}
