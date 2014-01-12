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

import net.javacrumbs.common.StockInfo;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;

public class LambdasSolution {

    public static void main(String[] args) {
        List<StockInfo> stocks = asList(
                new StockInfo("GOOG", 456),
                new StockInfo("IBM", 456),
                new StockInfo("AMZ", 123)
        );

        stocks.sort(new Comparator<StockInfo>() {
            @Override
            public int compare(StockInfo o1, StockInfo o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        stocks.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));

        Comparator<StockInfo> s1 = (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice());

        BiFunction<StockInfo, StockInfo, Integer> s2 = (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice());

        String hallo = "Hallo";
        Runnable r = () -> System.out.println(hallo); //scoping
        r.run();

        //hallo = "";
    }
}
