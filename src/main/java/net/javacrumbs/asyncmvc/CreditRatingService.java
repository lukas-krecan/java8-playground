/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.asyncmvc;

import net.javacrumbs.util.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static net.javacrumbs.util.Utils.log;
import static net.javacrumbs.util.Utils.sleep;

public class CreditRatingService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int userId = 1;

//        CompletableFuture.supplyAsync(() -> getUser(userId))
//                .thenApply(CreditRatingService::getCreditRatingSystem1)
//                .thenAccept(CreditRatingService::log);
//
//        CompletableFuture.supplyAsync(() -> getUser(userId))
//                .thenApplyAsync(CreditRatingService::getCreditRatingSystem1)
//                .thenAcceptAsync(CreditRatingService::log);

        CompletableFuture<User> user = supplyAsync(() -> getUser(userId));
        CompletableFuture<CreditRating> rating1 = user.thenApplyAsync(CreditRatingService::getCreditRatingSystem1);
        CompletableFuture<CreditRating> rating2 = user.thenApplyAsync(CreditRatingService::getCreditRatingSystem2);
        rating1.thenCombineAsync(rating2, CreditRating::combine).thenAccept(Utils::log);

        // getCreditRating(userId).thenAccept(System.out::println);

        log("End");
        Thread.sleep(1000L);
    }

    public static CompletableFuture<Object> getCreditRating(int userId) {
        CompletableFuture<User> user = supplyAsync(() -> getUser(userId));
        log("Calling rating 1");
        //async is important here
        CompletableFuture<CreditRating> rating1 = user.thenApplyAsync(CreditRatingService::getCreditRatingSystem1);

        log("Calling rating 2");
        //async is important here
        CompletableFuture<CreditRating> rating2 = user.thenApplyAsync(CreditRatingService::getCreditRatingSystem2);

        return rating1.thenCombineAsync(rating2, CreditRating::combine);
    }


    private static User getUser(int id) {
        sleep(150);
        log("User loaded");
        return new User(id);
    }


    private static CreditRating getCreditRatingSystem1(User user) {
        sleep(100);
        log("Rating 1 loaded");
//        throw new IllegalStateException("Error");
        return new CreditRating(user, 100);
    }


    private static CreditRating getCreditRatingSystem2(User user) {
        sleep(50);
        log("Rating 2 loaded");
        return new CreditRating(user, 100);
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
