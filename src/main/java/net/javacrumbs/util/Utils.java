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
package net.javacrumbs.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class Utils {
    public static <T> void measure(Runnable runnable) {
        measure(runnable, time -> System.out.println("Time taken: " + time + "ms"));
    }


    public static <T> void measure(Runnable runnable, Consumer<Long> consumer) {
        long start = System.currentTimeMillis();
        runnable.run();
        consumer.accept(System.currentTimeMillis() - start);
    }

    public static <T> T measure(Supplier<T> supplier) {
        return measure(supplier, time -> System.out.println("Time taken: " + time + "ms"));
    }

    public static <T> T measure(Supplier<T> supplier, Consumer<Long> consumer) {
        long start = System.currentTimeMillis();
        T result = supplier.get();
        consumer.accept(System.currentTimeMillis() - start);
        return result;
    }


    public static void log(Object message) {
        System.out.println(format("%s %s %s ", now(), Thread.currentThread().getName(), message));
    }


    private static String now() {
        return LocalTime.now().format(DateTimeFormatter.ISO_TIME);
    }


    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {

        }
    }

}
