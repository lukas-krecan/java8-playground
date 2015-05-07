/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class ProcessOptionals {

    public static void main(String[] args) {
        testOptional();
        testNormal();
    }

    private static void testOptional() {
        System.out.println("Optional:");
        OUser user = new OUser();

        //user.getAddress().get(); //throws NoSuchElementException

        OAddress result = user.getAddress().orElse(new OAddress());

        user.getAddress().ifPresent(System.out::println);

        OAddress address = new OAddress();
        user.setAddress(address);

        System.out.print("map.ifPresent: ");
        // Map returns optional of the result. If the result is optional, returns Optional of Optional
        user.getAddress().map(OAddress::getStreet).ifPresent(System.out::println);

        System.out.println("flatMap.ifPresent: ");
        user.getAddress().flatMap(OAddress::getStreet).map(String::length).ifPresent(System.out::println);

        address.setStreet("Street");

        System.out.print("flatMap.ifPresent2: ");
        user.getAddress().flatMap(OAddress::getStreet).map(String::length).ifPresent(System.out::println);

        user.getAddress().flatMap(OAddress::getStreet).orElse("None");
    }

    private static void testNormal() {
        System.out.println("\nNormal:");
        NormalUser user = new NormalUser();

        NormalAddress result1 = user.getAddress() != null ? user.getAddress() : new NormalAddress();
        NormalAddress result2 = ofNullable(user.getAddress()).orElse(new NormalAddress());

        NormalAddress address = new NormalAddress();
        user.setAddress(address);

        address.setStreet("Street");

        System.out.print("map.ifPresent: ");
        // Map returns optional of the result. If the result is optional, returns Optional of Optional
        ofNullable(user.getAddress()).map(NormalAddress::getStreet).map(String::length).ifPresent(System.out::println);

        if (user.getAddress()!=null){
            if (user.getAddress().getStreet()!=null) {
                System.out.println(user.getAddress().getStreet().length());
            }
        }

        System.out.print("flatMap.ifPresent: ");
        //Optional.ofNullable(user.getAddress()).flatMap(NormalAddress::getStreet).ifPresent(System.out::println);
    }
}
