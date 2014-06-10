/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.asyncmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

import static net.javacrumbs.asyncmvc.CreditRatingService.CreditRating;
import static net.javacrumbs.common.Utils.sleep;


@Controller
@EnableAutoConfiguration
public class ListenableFutureAsyncController {


    @RequestMapping("/")
    @ResponseBody
    DeferredResult<String> home() {
        DeferredResult<String> result = new DeferredResult<>(5000);

        ListenableFuture<CreditRating> creditRatingFuture = getCreditRating(1);
        creditRatingFuture.addCallback(new ListenableFutureCallback<CreditRating>() {
            @Override
            public void onSuccess(CreditRating cr) {
                result.setResult(cr.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setErrorResult(t.getMessage());
            }
        });
        return result;
    }

    private final SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();

    /**
     * Simulates asynchronous network communication.
     *
     * @param userId
     * @return
     */
    public ListenableFuture<CreditRating> getCreditRating(int userId) {
        return executor.submitListenable(
                () -> {
                    sleep(100);
                    return new CreditRating(new CreditRatingService.User(userId), 100);
                }
        );
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ListenableFutureAsyncController.class, args);
    }
}