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
package net.javacrumbs.geecon;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.measure;
import static net.javacrumbs.common.Utils.sleep;

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

import net.javacrumbs.common.StockInfo;

public class Example1StockFuturesCollectExecutor {

    private static final List<String> SYMBOLS = asList(
            "AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
            "AMZN", "CRAY", "CSCO", "DELL", "GOOG", "INTC", "INTU",
            "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");

    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        new Example1StockFuturesCollectExecutor().doRun(SYMBOLS);
        executorService.shutdown();
    }


    private void doRun(List<String> symbols) {
        measure(() ->
                symbols.stream()
                        .map(this::getStockInfo)
                        .collect(toList())
                        .stream()
                        .map(this::getFutureValue)
                        .max(Comparator.comparingDouble(StockInfo::getPrice))
                        .ifPresent(System.out::println)
        );
    }

    public Future<StockInfo> getStockInfo(String symbol) {
        return CompletableFuture.supplyAsync(() -> new StockInfo(symbol, calculatePrice(symbol)), executorService);
        // or even better
        // return executorService.submit(() -> new StockInfo(symbol, calculatePrice(symbol));
    }

    // Simulating long network task
    private double calculatePrice(String symbol) {
        log("Getting price for symbol " + symbol);
        sleep(100);
        return abs(symbol.hashCode()) % 1000.0;
    }

    private StockInfo getFutureValue(Future<StockInfo> f) {
        try {
            return f.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CompletionException(e);
        }
    }
}
