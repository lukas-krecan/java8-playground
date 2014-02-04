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
package net.javacrumbs.demos.parallel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;
import static java.util.stream.LongStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;


public class PrimesPrint {
    public static void main(String[] args) {
        System.out.println(
                range(1, 128).parallel().filter(PrimesPrint::isPrime).collect(ArrayList::new, PrimesPrint::addToList, PrimesPrint::combine)
        );
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
        boolean isPrime = n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
        if (isPrime) log("Prime found " + n);
        return isPrime;
    }
}
