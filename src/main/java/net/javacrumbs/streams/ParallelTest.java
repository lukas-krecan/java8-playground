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

import net.javacrumbs.common.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.sleep;

public class ParallelTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        // Simulating multiple threads in the system if one of them is executing long running task.
        // Some of the other threads/tasks are waiting for it to finish

        es.execute(() -> runTask(1000)); //incorrect task
        es.execute(() -> runTask(0));
        es.execute(() -> runTask(0));
        es.execute(() -> runTask(0));
        es.execute(() -> runTask(0));
        es.execute(() -> runTask(0));


        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void runTask(int delay) {
        log(range(1, 1_000_000).parallel().filter(ParallelTest::isPrime).peek(i -> sleep(delay)).count());
    }

    public static boolean isPrime(long n) {
        return n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }
}
