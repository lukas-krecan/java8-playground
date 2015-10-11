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

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;

import java.util.Collection;
import java.util.List;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

public class ExampleXPerfect {
    public static void main(String[] args) throws InterruptedException {
        new ExampleXPerfect().doRun();
    }

    public void doRun() throws InterruptedException {
        measure(() -> log(perfectNumbers(1, 100_000)));
    }

    private Collection<Integer> perfectNumbers(int from, int to) {
        return range(from, to)
                .parallel()
                .filter(this::isPerfect)
                .mapToObj(i -> i)
                .collect(toList());
    }

    public boolean isPerfect(int n) {
        return range(1, n)
                .parallel()
                .filter(divisor -> n % divisor == 0)
                .sum() == n;
    }
}
