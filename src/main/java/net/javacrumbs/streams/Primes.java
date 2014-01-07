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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.sqrt;
import static java.util.stream.LongStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;


public class Primes {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger();
//
        System.out.println(
                new Random(12345L).longs(1, 10_000_000)
                        .peek(i -> counter.incrementAndGet())
                        .filter(Primes::isPrime).findFirst().getAsLong()
        );
//
//        System.out.println(counter);


        measure(() ->
                System.out.println(range(1, 1024).parallel().filter(Primes::isPrime).collect(ArrayList::new, Primes::addToList, Primes::combine))
//              System.out.println(range(1, 100).parallel().filter(Primes::isPrime).findFirst().getAsLong())
        );
//        System.out.println(measure(() -> range(1, 1_000_000).parallel().filter(Primes::isPrime)).count());
//        range(1, 100).filter(Primes::isPrime).forEach(System.out::println);


    }

    private static void addToList(List<Long> list, Long n) {
        log("Adding " + n + " to " + list);
        list.add(n);
    }

    private static void combine(List<Long> list1, List<Long> list2) {
        log("Combining " + list1 + " and " + list2);
        list1.addAll(list2);
    }


    public static boolean isPrime(long n) {
        log("Checking " + n);
        //if (n == 95) sleep(1000);
        boolean isPrime = n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
        if (isPrime) log("Prime found " + n);
        return isPrime;
    }

    private static boolean isPrime2(long n) {
        if (n <= 1) {
            return false;
        }
        for (long divisor = 2; divisor <= sqrt(n); divisor++) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }


}
