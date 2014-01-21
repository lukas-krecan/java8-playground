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

import java.math.BigInteger;
import java.util.stream.Stream;


public class InfiniteStream {

    public static void main(String[] args) {
        Stream<BigInteger> infiniteStream = Stream.iterate(BigInteger.ZERO, i -> i.add(BigInteger.ONE));
        infiniteStream.skip(10000000).limit(10).forEach(System.out::println);

        Stream<BigInteger> factorials = Stream.iterate(FactorialAndIndex.first(), fi -> fi.next()).map(fi -> fi.factorial);
        factorials.limit(100).forEach(System.out::println);
        //factorials.skip(4).findFirst().ifPresent(System.out::println);

//        Stream<FactorialAndIndex> factAndIndexes = Stream.iterate(FactorialAndIndex.first(), fi -> fi.next());
//        factAndIndexes.limit(1000).forEach(System.out::println);
    }

    private static class FactorialAndIndex {
        private final BigInteger factorial;
        private final int index;

        private FactorialAndIndex(int index, BigInteger factorial) {
            this.factorial = factorial;
            this.index = index;
        }

        public static FactorialAndIndex first() {
            return new FactorialAndIndex(1, BigInteger.ONE);
        }

        public FactorialAndIndex next() {
            int newIndex = index + 1;
            return new FactorialAndIndex(newIndex, factorial.multiply(BigInteger.valueOf(newIndex)));
        }

        @Override
        public String toString() {
            return index + " : " + factorial;
        }
    }
}
