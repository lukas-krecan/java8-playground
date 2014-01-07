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

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

public class MethodReferences {

    public static void main(String[] args) {
        List<StockInfo> stocks = asList(
                new StockInfo("GOOG", 456),
                new StockInfo("IBM", 456),
                new StockInfo("AMZ", 123)
        );

        //lambda - see the type inference
        stocks.sort((a, b) -> a.getTicker().compareTo(b.getTicker()));
        System.out.println("By ticker " + stocks);

        //reference to static method
        stocks.sort(MethodReferences::compareByPrice);
        System.out.println("By price 2 " + stocks);


        //reference to method of the instance
        stocks.sort(comparingDouble(StockInfo::getPrice));
        System.out.println("By price 1 " + stocks);

        //instance and a parameter
        List<String> strings = asList("Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda");
        strings.sort(String::compareToIgnoreCase);
        System.out.println("Strings ignore case " + strings);

        //constructor reference
        System.out.println(strings.stream().toArray(String[]::new));

        //default methods
        //collection.sort() .stream() are default methods
        stocks.sort(comparingDouble(StockInfo::getPrice).reversed().thenComparing(comparing(StockInfo::getTicker)));
        System.out.println("By price and name " + stocks);
    }

    private static int compareByPrice(StockInfo s1, StockInfo s2) {
        return Double.compare(s1.getPrice(), s2.getPrice());
    }


}
