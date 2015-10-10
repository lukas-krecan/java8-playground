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
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;

import static net.javacrumbs.common.Utils.measure;


public class Hashes4 {
    private static final Random random = new Random(3456789L);

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        ForkJoinPool fjp = new ForkJoinPool(3);

//        measure(() ->
//                spliteratorStream().peek(s -> adder.increment())
//                    .filter(s -> md5(s).startsWith("0000000")).findAny()
//                    .ifPresent(b -> System.out.println(b + " " + md5(b)))
//        );

        measure(() ->
                stream()
//                    .parallel()
//                    .peek(b -> adder.increment())
//                    .peek(b -> {if (b % 1_000_000 == 0) log(b);})
                    .filter(s -> hash(s).startsWith("000000"))
//                    .filter(b -> b % 10_000_000 == 0)
//                    .findAny().ifPresent(b -> System.err.println(b + " " + hash(b)))
//                    .limit(1)
                    .unordered()
                    .forEach(b -> System.err.println(b + " " + hash(b)))
        );
        System.out.println(adder);
    }


    private static LongStream stream() {
        return LongStream.range(0, 100_000_000);
    }

    private static String hash(long bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(bytes);
        return DigestUtils.md5Hex(buffer.array());
    }


}
