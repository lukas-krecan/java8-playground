/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class ProcessOptionals {

    public static void main(String[] args) {
        Optional<String> optional = Stream.of("test").filter(s -> s.startsWith("t")).findFirst();
        if (optional.isPresent()) optional.get(); //may throw NoSuchElementException
        optional.ifPresent(System.out::println);
        System.out.println(optional.orElse("default"));

        testNormal();
        testOptional();
    }

    private static void testNormal() {
        System.out.println("\nNormal:");
        User user = new User();

        Address result1 = user.getAddress() != null ? user.getAddress() : new Address();
        Address result2 = ofNullable(user.getAddress()).orElse(new Address());

        Address address = new Address();
        user.setAddress(address);

        address.setStreet("Street");

        if (user != null) {
            if (user.getAddress() != null) {
                if (user.getAddress().getStreet() != null) {
                    if (user.getAddress().getStreet().length() > 3) {
                        System.out.println(user.getAddress().getStreet());
                    }
                }
            }
        }

        System.out.print("Normal map.ifPresent: ");
        ofNullable(user).map(User::getAddress).map(Address::getStreet)
                .filter(s -> s.length() > 3).ifPresent(System.out::println);
        System.out.println();
    }

    private static void testOptional() {
        System.out.println("Optional:");
        OUser user = new OUser();

        //user.getAddress().get(); //throws NoSuchElementException


        OAddress address = new OAddress();
        user.setAddress(address);

        System.out.print("map.ifPresent: ");
        // Map returns optional of the result. If the result is optional, returns Optional of Optional
        user.getAddress().map(OAddress::getStreet).ifPresent(System.out::println);

        System.out.println("flatMap.ifPresent: ");
        user.getAddress().flatMap(OAddress::getStreet).map(String::length).ifPresent(System.out::println);

    }


}
