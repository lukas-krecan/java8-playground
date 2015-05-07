/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.demos.streams;

import net.javacrumbs.common.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static net.javacrumbs.common.Person.Sex.FEMALE;
import static net.javacrumbs.common.Person.Sex.MALE;


// LambdaMetafactory
public class StreamsDeepDive {

    public static void lambdas() {
        Set<Runnable> set1 = new HashSet<>();
        Set<Runnable> set2 = new HashSet<>();
        int j = 9;

        IntStream.range(0, 10).forEach(i -> {
            set1.add(() -> System.out.println("Hi"));
            set2.add(() -> System.out.println("Hi" + j));
        });

        // Implementation specific
        System.out.println("Simple lambdas: " + set1.size());
        System.out.println("Enclosing lambdas: " + set2.size());
    }


    public static void lambdas2() {
        //uglyyy
        Set<Runnable> set1 = IntStream.range(0, 10)
                .mapToObj(i -> (Runnable) () -> System.out.println("Hi"))
                .collect(toSet());

        Set<Runnable> set2 = IntStream.range(0, 10)
                .mapToObj(i -> (Runnable) () -> System.out.println("Hi " + i))
                .collect(toSet());

        // Implementation specific
        System.out.println("Simple lambdas: " + set1.size());
        System.out.println("Enclosing lambdas: " + set2.size());

    }


    public static void streams() {
        List<Person> people = asList(
                new Person("Eve", 23, MALE),
                new Person("John", 25, MALE),
                new Person("Billy", 25, FEMALE),
                new Person(null, 30, MALE)
        );

        people.stream()
                .filter(p -> p.getName() != null)
                .mapToInt(p -> p.getName().length())
                .max()
                .ifPresent(System.out::println);

        people.stream()
                .map(Person::getName)
   //             .filter(Objects::nonNull)
                .mapToInt(String::length)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        lambdas();
        streams();
    }

    public static int length(String string) {
        return string.length();
    }
}
