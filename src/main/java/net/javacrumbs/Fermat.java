/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs;

/**
 * Copyright 2009-2014 the original author or authors.
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.round;

public class Fermat {
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final double THRESHOLD = 0.000_0001;

    public static void main(String[] args) {
        for (int n = 3; n < 10; n++) {
            int x = n;
            executor.execute(() -> findNearestMissFor(x));
        }
        executor.shutdown();
    }

    private static void findNearestMissFor(int n) {
        double ninv = 1d / n;
        int max = 100_000;
        double minDiff = Double.MAX_VALUE;
        Result result = null;

        for (int x = 3; x < max; x++) {
            double xn = pow(x, n);
            for (int y = x; y < max; y++) {
                double sum = xn + pow(y, n);
                double z = pow(sum, ninv);
                int intz = (int) round(z);
                double diff = abs(z - intz);
                if (diff < THRESHOLD && intz != x && intz != y) {
                    double zn = pow(intz, n);
                    double zndiff = abs(sum - zn);
                    if (zndiff < minDiff) {
                        minDiff = zndiff;
                        result = new Result(x, y, n);
                        System.out.println(result);
                    }
                }
            }
        }

        System.err.println(result);
    }

    private static final class Result {
        private final int x;
        private final int y;
        private final int n;

        private Result(int x, int y, int n) {
            this.x = x;
            this.y = y;
            this.n = n;
        }

        @Override
        public String toString() {
            double sum = pow(x, n) + pow(y, n);
            double z = pow(sum, (double) 1 / n);
            long intz = round(z);
            double zn = pow(intz, n);
            return "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", n=" + n +
                    ", diff=" + abs(z - intz) +
                    ", " + sum + " = " + zn +
                    ", " + abs(sum - zn) +
                    ", " + (max(sum, zn) / min(sum, zn) - 1);
        }
    }
}
