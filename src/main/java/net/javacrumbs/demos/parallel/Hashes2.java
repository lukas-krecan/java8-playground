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

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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

import static net.javacrumbs.common.Utils.measure;


public class Hashes2 {
    private static final Random random = new Random(3456789L);

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        ForkJoinPool fjp = new ForkJoinPool(3);

        measure(() ->
            spliteratorStream().peek(s -> adder.increment())
                            .filter(s -> md5(s).startsWith("000000")).findAny().ifPresent(s -> System.out.println(new String(s) + " " + md5(s)))
        );
        System.out.println(adder);
    }


    private static Stream<char[]> spliteratorStream() {
        return StreamSupport.stream(new RandomStringsSpliterator(20), false);
    }

    private static String md5(char[] s) {
        return DigestUtils.md5Hex(charsToBytesUTFNIO(s));
    }

    public static byte[] charsToBytesUTFNIO(char[] buffer) {
        byte[] b = new byte[buffer.length << 1];
        CharBuffer cBuffer = ByteBuffer.wrap(b).asCharBuffer();
        for (int i = 0; i < buffer.length; i++)
            cBuffer.put(buffer[i]);
        return b;
    }

    private static char[] randomString(char[] buffer) {
        return random(0, 0, true, true, random, buffer);
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
            return (Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }

        public boolean tryAdvance(Consumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            char[] buffer = new char[length];
            if (i < f) {
                consumer.accept(randomString(buffer));
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
                char[] buffer = new char[length];
                do {
                    consumer.accept(randomString(buffer));
                } while (++i < f);
            }
        }
    }

    /**
     * <p>Creates a random string based on a variety of options, using
     * supplied source of randomness.</p>
     * <p>
     * <p>If start and end are both {@code 0}, start and end are set
     * to {@code ' '} and {@code 'z'}, the ASCII printable
     * characters, will be used, unless letters and numbers are both
     * {@code false}, in which case, start and end are set to
     * {@code 0} and {@code Integer.MAX_VALUE}.
     * <p>
     * <p>If set is not {@code null}, characters between start and
     * end are chosen.</p>
     * <p>
     * <p>This method accepts a user-supplied {@link java.util.Random}
     * instance to use as a source of randomness. By seeding a single
     * {@link java.util.Random} instance with a fixed seed and using it for each call,
     * the same random sequence of strings can be generated repeatedly
     * and predictably.</p>
     *
     * @param start   the position in set of chars to start at
     * @param end     the position in set of chars to end before
     * @param letters only allow letters?
     * @param numbers only allow numbers?
     *                If {@code null}, then it will use the set of all chars.
     * @param random  a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not
     *                                        {@code (end - start) + 1} characters in the set array.
     * @throws IllegalArgumentException       if {@code count} &lt; 0.
     * @since 2.0
     */
    public static char[] random(int start, int end, boolean letters, boolean numbers,
                                Random random, char[] buffer) {
        int count = buffer.length;
        if (start == 0 && end == 0) {
            end = 'z' + 1;
            start = ' ';
            if (!letters && !numbers) {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        int gap = end - start;

        while (count-- != 0) {
            char ch = (char) (random.nextInt(gap) + start);
            if (letters && Character.isLetter(ch)
                    || numbers && Character.isDigit(ch)
                    || !letters && !numbers) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return buffer;
    }


}
