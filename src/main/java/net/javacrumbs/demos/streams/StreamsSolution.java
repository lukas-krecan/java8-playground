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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toMap;
import static net.javacrumbs.common.Person.Sex.FEMALE;
import static net.javacrumbs.common.Person.Sex.MALE;

public class StreamsSolution {
    private static final List<Person> people = asList(
            new Person("Bill", 23, MALE),
            new Person("John", 25, MALE),
            new Person("Jane", 25, FEMALE),
            new Person("Adam", 30, MALE)
    );

    private static final String TEXT = "Jane, Adam, Bill, John";

    public static void main(String[] args) {
        //print the list
        people.stream().forEach(System.out::println);

        System.out.println("just the names");
        people.stream().map(Person::getName).forEach(System.out::println);

        System.out.println("just the names sorted by age");
        people.stream()
                .sorted(comparing(Person::getAge))
                .map(Person::getName)
                .forEach(System.out::println);

        System.out.println("just guys sorted by name");
        people.stream()
                .filter(p -> p.getGender() == MALE)
                .sorted(comparing(Person::getName))
                .forEach(System.out::println);

        System.out.println("max age");
        people.stream()
                .max(comparing(Person::getAge)).ifPresent(System.out::println);

        System.out.println("avg age");
        people.stream()
                .mapToInt(Person::getAge).average().ifPresent(System.out::println);

        System.out.println("Number of women");
        System.out.println(
                people.stream()
                        .filter(p -> p.getGender() == FEMALE)
                        .count()
        );

        System.out.println("just guys sorted by name as list");
        System.out.println(
                people.stream()
                        .filter(p -> p.getGender() == MALE)
                        .sorted(comparing(Person::getName))
                        .collect(Collectors.toList())
        );

        System.out.println("First random divisible by 137");
        new Random().ints().filter(i -> i % 137 == 0).findFirst().ifPresent(System.out::println);

        System.out.println("Only names in TEXT starting at 'J'");
        System.out.println(Pattern.compile(",").splitAsStream(TEXT).map(String::trim).filter(s -> s.startsWith("J")).collect(joining(", ")));


        System.out.println("Males and females in map");
        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getGender))
        );

        System.out.println("Average age by gender");
        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getGender, averagingInt(Person::getAge)))
        );

        System.out.println("Map of people by name");
        System.out.println(people.stream().collect(toMap(Person::getName, p -> p)));


        System.out.println("Max age by gender");
        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getGender, maxBy(comparingInt(Person::getAge))))
        );

        System.out.println("First male older than 24");
        System.out.println("Found: " +
                people.stream().peek(System.out::println).filter(p -> p.getAge() > 24).findFirst().get()
        );

        System.out.println("Most frequent character in the name");
        System.out.println(
                people.stream()
                        .map(Person::getName)
                        .flatMapToInt(String::chars)
                        .mapToObj(i -> Character.valueOf((char) i))
                        .map(Character::toLowerCase)
                        .collect(groupingBy(c -> c, counting()))
                        .entrySet().stream().max(comparingLong(Map.Entry::getValue))
        );

    }
}
