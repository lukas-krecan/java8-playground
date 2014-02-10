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
package net.javacrumbs.demos.async;

import net.javacrumbs.common.StockInfo;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;
import static net.javacrumbs.common.Utils.sleep;

public class Stock {

    public static void main(String[] args) {
        List<String> symbols = asList(
                "AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
                "AMZN", "CRAY", "CSCO", "DELL", "GOOG", "INTC", "INTU",
                "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");

        new Stock().doRun(symbols.stream());
    }

    private void doRun(Stream<String> symbols) {
        measure(() ->
                System.out.println(
                        getStockInfo(symbols)
                )
        );
    }

    /**
     * Gets stream of stock info on the input, returns collection of stock info
     *
     * @param symbols
     * @return
     */
    private List<StockInfo> getStockInfo(Stream<String> symbols) {
        return symbols.parallel()
                .map(this::getStockInfo) //slow network operation
                .collect(toList());
    }

    private void doRun2(List<String> symbols) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        measure(() ->
                symbols.stream()

        );

        executorService.shutdown();
    }


    public StockInfo getStockInfo(String symbol) {
        return new StockInfo(symbol, calculatePrice(symbol));
    }

    private double calculatePrice(String symbol) {
        log("Getting price for symbol " + symbol);
        sleep(100);
        return abs(symbol.hashCode()) % 1000.0;
    }

    private <T> T getFutureValue(Future<T> f) {
        try {
            return f.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CompletionException(e);
        }
    }


}
