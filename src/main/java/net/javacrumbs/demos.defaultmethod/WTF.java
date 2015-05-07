/*
 * Copyright (C) 2007-2015, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.demos.defaultmethod;

public class WTF {
    public static void main(String[] args) {
        new EmptySpeakImpl().speak();
        new EmptySpeakImplChild().speak();
    }
}

interface ISpeak {
    default void speak() {
        System.out.println("ISpeak Speaking!");
    }
}

interface ISpeak2 extends ISpeak {
    default void speak() {
        System.out.println("ISpeak2 Speaking!");
    }
}

class EmptySpeakImpl implements ISpeak2 {}
class EmptySpeakImplChild extends EmptySpeakImpl implements ISpeak {}
