/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.demos.async;

import net.javacrumbs.common.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.sleep;

public class CreditRatingService2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new CreditRatingService2().run();


    }

    private void run() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        log("Start");
        int userId = 1;
//        supplyAsync(() -> getUser(userId), executor)
//                .thenApply(this::getCreditRatingFromSystem1)
//                .thenAccept(Utils::log);

//
//
//        CompletableFuture<User> user = supplyAsync(() -> getUser(userId), executor);
//        CompletableFuture<CreditRating> rating1 = user.thenApply(this::getCreditRatingFromSystem1);
//        CompletableFuture<CreditRating> rating2 = user.thenApply(this::getCreditRatingFromSystem2);
//        rating1.thenCombine(rating2, CreditRating::combine).thenAccept(Utils::log);
////
//        CompletableFuture<User> user = supplyAsync(() -> getUser(userId), executor);
//        CompletableFuture<CreditRating> rating1 = user.thenApplyAsync(this::getCreditRatingFromSystem1, executor);
//        CompletableFuture<CreditRating> rating2 = user.thenApplyAsync(this::getCreditRatingFromSystem2, executor);
//        rating1.thenCombine(rating2, CreditRating::combine).thenAccept(Utils::log);
//
//        rating1.acceptEither(rating2, Utils::log);
//
        supplyAsync(() -> getUser(userId), executor).thenApply(this::getCreditRatingFromSystem3)
                .thenAccept(Utils::log);

//        supplyAsync(() -> getUser(userId), executor).thenApply(this::getCreditRatingFromSystem3)
//                .thenAccept(Utils::log).whenComplete((x, e) -> e.printStackTrace());
//
//        CompletableFuture<User> uf = new CompletableFuture<>();
//        uf.complete(new User(1));


        log("End");
        Thread.sleep(1000L);
        executor.shutdown();
    }

    private User getUser(int id) {
        log("User load start");
        sleep(100);
        log("User load end");
        return new User(id);
    }


    private CreditRating getCreditRatingFromSystem1(User user) {
        log("Rating 1 load start");
        sleep(100);
        log("Rating 1 load end");
        return new CreditRating(user, 75);
    }


    private CreditRating getCreditRatingFromSystem2(User user) {
        log("Rating 2 load start");
        sleep(150);
        log("Rating 2 load end");
        return new CreditRating(user, 100);
    }

    private CreditRating getCreditRatingFromSystem3(User user) {
        log("Rating 3 load start");
        sleep(100);
        log("Rating 3 load end");
        throw new RuntimeException("Error");
    }

    public static class User {
        private final int id;

        public User(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    '}';
        }
    }

    public static class CreditRating {
        private final User user;
        private final int rating;

        public CreditRating(User user, int rating) {
            this.user = user;
            this.rating = rating;
        }

        public CreditRating combine(CreditRating other) {
            log("Combine");
            return this.rating < other.rating ? this : other;
        }

        @Override
        public String toString() {
            return "CreditRating{" +
                    "user=" + user +
                    ", rating=" + rating +
                    '}';
        }
    }
}
