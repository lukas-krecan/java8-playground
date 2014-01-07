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
package net.javacrumbs.common;

public class StockInfo {
    private final String ticker;

    private final double price;

    public StockInfo(final String symbol, final double thePrice) {
        ticker = symbol;
        price = thePrice;
    }

    public double getPrice() {
        return price;
    }

    public String getTicker() {
        return ticker;
    }

    public String toString() {
        return String.format("%s: %g", ticker, price);
    }
}
