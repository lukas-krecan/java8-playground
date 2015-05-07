/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;

public class OAddress {
    private String street;

    public Optional<String> getStreet() {
        return Optional.ofNullable(street);
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
