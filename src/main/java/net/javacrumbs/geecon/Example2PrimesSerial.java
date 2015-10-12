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
import static java.util.stream.IntStream.range;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;

public class Example2PrimesSerial {
    public static void main(String[] args) throws InterruptedException {
        new Example2PrimesSerial().doRun();
    }

    private void doRun() throws InterruptedException {
        measure(() -> log(countPrimes(1_000_000, 3_000_000)));
    }

    /**
     * Returns number of primes in specified interval
     * using really ineffective algorithm.
     */
    private long countPrimes(int from, int to) {
        return range(from, to).filter(this::isPrime).count();
    }

    public boolean isPrime(long n) {
        return n > 1 && rangeClosed(2, (long) sqrt(n))
                .noneMatch(divisor -> n % divisor == 0);
    }
}
