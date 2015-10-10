/*
 * Copyright (C) 2007-2013, GoodData(R) Corporation. All rights reserved.
 */
package net.javacrumbs.asyncmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import static net.javacrumbs.common.Utils.log;


@Controller
@EnableAutoConfiguration
public class ListenableFutureAsyncController {
    private final AsyncRestTemplate restTemplate = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

    @RequestMapping("/")
    @ResponseBody
    DeferredResult<String> home() {
        DeferredResult<String> result = new DeferredResult<>(5000);

        ListenableFuture<ResponseEntity<String>> creditRatingFuture = restTemplate.getForEntity("http://www.google.com", String.class);
        creditRatingFuture.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String>  response) {
                log("Success");
                result.setResult(response.getBody());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setErrorResult(t.getMessage());
            }
        });
        return result;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ListenableFutureAsyncController.class, args);
    }
}