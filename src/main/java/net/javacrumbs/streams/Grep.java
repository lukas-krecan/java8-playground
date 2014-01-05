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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grep {
    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get("test.txt"))) {
            System.out.println(
//                    lines.flatMap(line -> split(line)).filter(word-> word.startsWith("s")).collect(Collectors.toList())
//                    lines.flatMap(line -> split(line)).filter(word-> word.startsWith("s")).count()
                    lines.flatMap(line -> split(line)).collect(Collectors.toMap(w -> w, w -> 1, (a, b) -> a + b))
                            .entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toList())
//                    lines.flatMap(line -> split(line)).count()
//                    lines.filter(line -> line.contains("dolor")).count()
            );
        }
    }

    private static Stream<String> split(String line) {
        return Pattern.compile("\\s+").splitAsStream(line.trim());
    }
}
