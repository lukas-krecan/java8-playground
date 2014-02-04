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
package net.javacrumbs.demos.streams;

import net.javacrumbs.common.Person;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.LongStream.rangeClosed;
import static net.javacrumbs.common.Person.Sex.FEMALE;
import static net.javacrumbs.common.Person.Sex.MALE;

public class Streams {
    private static final List<Person> people = asList(
            new Person("Adam", 30, MALE),
            new Person("Bill", 23, MALE),
            new Person("John", 25, MALE),
            new Person("Jane", 25, FEMALE)
    );

    private static final String TEXT = "Jane, Adam, Bill, John";

    public static void main(String[] args) {
        //print the list
        people.stream().forEach(System.out::println);

        System.out.println("just the names");//mthod inference

        System.out.println("just the names sorted by age");

        System.out.println("max age");

        System.out.println("Number of women");

        System.out.println("Map of people by name");

        System.out.println("People for names");
        List<String> names = asList("John", "Jane");

        System.out.println("First random divisible by 137");

        System.out.println("Average age by gender");
    }
}
