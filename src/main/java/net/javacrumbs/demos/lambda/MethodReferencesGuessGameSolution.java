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

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReferencesGuessGameSolution {

    public static void main(String[] args) {

        Consumer<Object> c = System.out::println;

        BiFunction<Integer, Integer, Integer> b = Math::min;

        Function<Integer, Float> f = Integer::floatValue;

        Function<Double, Boolean> finite = Double::isFinite;

        Predicate<Double> finite2 = Double::isFinite;

        Supplier<Double> r = Math::random;

        Supplier<String> ss = String::new;
    }
}
