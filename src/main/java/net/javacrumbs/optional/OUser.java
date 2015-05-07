/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.optional;

import java.util.Optional;

public class OUser {
    private OAddress address;

    public Optional<OAddress> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(OAddress address) {
        this.address = address;
    }
}
