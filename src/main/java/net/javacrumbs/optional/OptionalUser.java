/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;

public class OptionalUser {
    private OptionalAddress address;

    public Optional<OptionalAddress> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(OptionalAddress address) {
        this.address = address;
    }
}
