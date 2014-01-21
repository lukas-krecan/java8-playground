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

import net.javacrumbs.common.Person;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;
import static net.javacrumbs.common.Person.Sex.FEMALE;
import static net.javacrumbs.common.Person.Sex.MALE;

public class LambdasSolution {

    public static void main(String[] args) {
        List<Person> people = asList(
                new Person("Bill", 23, MALE),
                new Person("John", 25, MALE),
                new Person("Jane", 25, FEMALE),
                new Person("Adam", 30, MALE)
        );

        people.sort(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
            }
        });

        people.sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));

        System.out.println(people);

        Comparator<Person> s1 = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());

        BiFunction<Person, Person, Integer> s2 = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());

        String hallo = "Hallo";
        Runnable r = () -> System.out.println(hallo); //scoping
        r.run();

        //hallo = "";
    }
}