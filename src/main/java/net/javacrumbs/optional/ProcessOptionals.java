/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;

public class ProcessOptionals {

    public static void main(String[] args) {
        testOptional();
        testNormal();
    }

    private static void testOptional() {
        System.out.println("Optional:");
        OptionalUser user = new OptionalUser();

        //user.getAddress().get(); //throws NoSuchElementException

        OptionalAddress result = user.getAddress().orElse(new OptionalAddress());

        user.getAddress().ifPresent(System.out::println);

        OptionalAddress address = new OptionalAddress();
        user.setAddress(address);

        System.out.print("map.ifPresent: ");
        // Map returns optional of the result. If the result is optional, returns Optional of Optional
        user.getAddress().map(OptionalAddress::getStreet).ifPresent(System.out::println);

        System.out.println("flatMap.ifPresent: ");
        user.getAddress().flatMap(OptionalAddress::getStreet).map(String::length).ifPresent(System.out::println);

        address.setStreet("Street");

        System.out.print("flatMap.ifPresent2: ");
        user.getAddress().flatMap(OptionalAddress::getStreet).map(String::length).ifPresent(System.out::println);

        user.getAddress().flatMap(OptionalAddress::getStreet).orElse("None");
    }

    private static void testNormal() {
        System.out.println("\nNormal:");
        NormalUser user = new NormalUser();

        NormalAddress result1 = user.getAddress() != null ? user.getAddress() : new NormalAddress();
        NormalAddress result2 = Optional.ofNullable(user.getAddress()).orElse(new NormalAddress());

        NormalAddress address = new NormalAddress();
        user.setAddress(address);

        address.setStreet("Street");

        System.out.print("map.ifPresent: ");
        // Map returns optional of the result. If the result is optional, returns Optional of Optional
        Optional.ofNullable(user.getAddress()).map(NormalAddress::getStreet).map(String::length).ifPresent(System.out::println);

        System.out.print("flatMap.ifPresent: ");
        //Optional.ofNullable(user.getAddress()).flatMap(NormalAddress::getStreet).ifPresent(System.out::println);
    }
}
