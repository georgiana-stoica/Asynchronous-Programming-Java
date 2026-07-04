package com.asynchronous.app;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Concurrent {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var service = Executors.newFixedThreadPool(2);

        var futureForTask1 = service.submit(Concurrent::invokeTask1);
        var futureForTask2 = service.submit(Concurrent::invokeTask2); //why use method reference instead of lambda

        var task1Result = futureForTask1.get(); //call is blocking
        var task2Result = futureForTask2.get(); //call is blocking

        System.out.println(task1Result);
        System.out.println(task2Result);

        service.shutdown();
    }

    public static String invokeTask1() throws InterruptedException {
        Thread.sleep(5000);
        return "georgiana";
    }

    public static String invokeTask2() throws InterruptedException {
        Thread.sleep(3000);
        return "stoica";
    }
}