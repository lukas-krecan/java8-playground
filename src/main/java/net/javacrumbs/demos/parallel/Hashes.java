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

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Hashes {
    private static final Random random = new Random(3456789L);

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        ForkJoinPool fjp = new ForkJoinPool(3);

        try {
            fjp.submit(() ->
                    spliteratorStream().peek(s -> adder.increment())
                            .filter(s -> md5(s).startsWith("0000000")).findAny().ifPresent(s -> System.out.println(s + " " + md5(s)))
            ).get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(adder);
    }

    private static Stream<String> normalStream() {
        return Stream.iterate("", s -> randomString(20));
    }

    private static Stream<String> spliteratorStream() {
         return StreamSupport.stream(new RandomStringsSpliterator(20), false);
     }

    private static String md5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    private static String randomString(int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, random);
    }

    static final class RandomStringsSpliterator implements Spliterator {
        long index;
        final long fence;
        final int length;

        RandomStringsSpliterator(int length) {
            this(0, Long.MAX_VALUE, length);
        }

        RandomStringsSpliterator(long index, long fence,
                                 int length) {
            this.index = index;
            this.fence = fence;
            this.length = length;
        }

        public RandomStringsSpliterator trySplit() {
            long i = index, m = (i + fence) >>> 1;
            return (m <= i) ? null :
                    new RandomStringsSpliterator(i, index = m, length);
        }

        public long estimateSize() {
            return fence - index;
        }

        public int characteristics() {
            return (Spliterator.SIZED | Spliterator.SUBSIZED |
                    Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }

        public boolean tryAdvance(Consumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                consumer.accept(randomString(length));
                index = i + 1;
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                do {
                    consumer.accept(randomString(length));
                } while (++i < f);
            }
        }
    }


}
