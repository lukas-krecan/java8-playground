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
import java.util.Base64;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static net.javacrumbs.common.Utils.measure;


public class Hashes3 {
    private static final Random random = new Random(3456789L);

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        ForkJoinPool fjp = new ForkJoinPool(3);

        measure(() ->
                spliteratorStream().parallel().peek(s -> adder.increment())
                    .filter(s -> md5(s).startsWith("0000000")).findAny()
                    .ifPresent(b -> System.out.println(Base64.getEncoder().encodeToString(b) + " " + md5(b)))
        );
        System.out.println(adder);
    }


    private static Stream<byte[]> spliteratorStream() {
        return Stream.iterate(randomBytes(20), x -> randomBytes(20));
    }

    private static String md5(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    public static byte[] randomBytes(int len) {
        byte[] bytes = new byte[len];
        ThreadLocalRandom.current().nextBytes(bytes);
        return bytes;
    }


}
