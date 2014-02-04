/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.demos.async;

import net.javacrumbs.common.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static net.javacrumbs.common.Utils.log;
import static net.javacrumbs.common.Utils.sleep;

public class CreditRatingService2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int userId = 1;
        log("Start");

        CompletableFuture<User> user = supplyAsync(() -> getUser(userId));
        CompletableFuture<CreditRating> rating1 = user.thenApplyAsync(CreditRatingService2::getCreditRatingSystem1);
        CompletableFuture<CreditRating> rating2 = user.thenApplyAsync(CreditRatingService2::getCreditRatingSystem2);
        rating1.thenCombineAsync(rating2, CreditRating::combine).thenAccept(Utils::log);


        log("End");
        Thread.sleep(1000L);
    }

    private static User getUser(int id) {
        sleep(100);
        log("User loaded");
        return new User(id);
    }


    private static CreditRating getCreditRatingSystem1(User user) {
        sleep(100);
        log("Rating 1 loaded");
//        throw new IllegalStateException("Error");
        return new CreditRating(user, 75);
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
