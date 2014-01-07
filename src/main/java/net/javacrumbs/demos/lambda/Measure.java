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

import static net.javacrumbs.common.Utils.log;

public class Measure {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        isPrimePrint(1_854_527);
        log("Time taken " + (System.currentTimeMillis() - start) + "ms.");

        //measure(() -> isPrimePrint(1_854_527));
    }

    private static void isPrimePrint(long number) {
        System.out.println(Primes.isPrime(number));
    }

    private static boolean isPrime(long number) {
        return Primes.isPrime(number);
    }
}
