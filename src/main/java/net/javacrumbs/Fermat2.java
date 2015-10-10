/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class Fermat2 {

    public static void main(String[] args) {
        printDetails(7364, 83692, 3);
        printDetails(1782, 1841, 12);
    }

    private static void printDetails(int x, int y, int n) {
        double sum = pow(x, n) + pow(y, n);
        double z = pow(sum, 1d/n);
        System.out.printf("x=%s\ty=%s\t(x^n+y^n)/z^n=%s\tz=%s\n", x, y, sum/pow(round(z), n), z);
    }
}
