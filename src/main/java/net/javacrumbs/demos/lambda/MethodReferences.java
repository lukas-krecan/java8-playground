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
import net.javacrumbs.common.Person;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static net.javacrumbs.common.Person.Sex.FEMALE;
import static net.javacrumbs.common.Person.Sex.MALE;

public class MethodReferences {

    public static void main(String[] args) {
        List<Person> people = asList(
                new Person("Bill", 23, MALE),
                new Person("John", 25, MALE),
                new Person("Jane", 25, FEMALE),
                new Person("Adam", 30, MALE)
        );

        //lambda - see the type inference
        people.sort((a, b) -> a.getName().compareTo(b.getName()));
        System.out.println("By name " + people);

        //reference to static method
        people.sort(MethodReferences::compareByAge);
        System.out.println("By age 1 " + people);


        //instance and a parameter
        List<String> strings = asList("Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda");
        strings.sort(String::compareToIgnoreCase);
        System.out.println("Strings ignore case " + strings);

        //reference to method of the instance
        people.sort(Comparator.comparingInt(Person::getAge));
        System.out.println("By age 2 " + people);

        System.out.println(strings.toArray(new String[strings.size()])); //cant do new T[]
        //constructor reference
        System.out.println(strings.stream().toArray(String[]::new));

        //default methods
        //collection.sort() .stream() are default methods
        people.sort(comparingInt(Person::getAge).reversed().thenComparing(comparing(Person::getName)));
        System.out.println("By price and name " + people);
    }

    private static int compareByAge(Person s1, Person s2) {
        return Integer.compare(s1.getAge(), s2.getAge());
    }


}
