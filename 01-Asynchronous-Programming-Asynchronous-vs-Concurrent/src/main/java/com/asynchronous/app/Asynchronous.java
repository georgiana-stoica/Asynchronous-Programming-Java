package com.asynchronous.app;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Asynchronous {


    public static void main(String[] args) throws InterruptedException {
        var task1Future = CompletableFuture.supplyAsync(Asynchronous::invokeTask1);
        var task2Future = CompletableFuture.supplyAsync(Asynchronous::invokeTask2);

        CompletableFuture.allOf(task1Future, task2Future)
                .thenAccept(v -> System.out.println(task2Future.join() + " " + task1Future.join()));

        sleep(1000); //this line is not part of the application
    }

    public static String invokeTask1() {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "georgiana";
    }

    public static String invokeTask2() {
        try {
            sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "stoica";
    }
}
