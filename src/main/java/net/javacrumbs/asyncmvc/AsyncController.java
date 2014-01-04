/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.asyncmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import static net.javacrumbs.asyncmvc.CreditRatingService.getCreditRating;

@Controller
@EnableAutoConfiguration
public class AsyncController {

    @RequestMapping("/")
    @ResponseBody
    DeferredResult<String> home() {
        DeferredResult<String> result = new DeferredResult<>(5000);
        getCreditRating(1)
                .whenComplete((cr, e) -> {
                    if (e == null) {
                        result.setResult(cr.toString());
                    } else {
                        result.setErrorResult(e.getMessage());
                    }
                });

        return result;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(AsyncController.class, args);
    }
}