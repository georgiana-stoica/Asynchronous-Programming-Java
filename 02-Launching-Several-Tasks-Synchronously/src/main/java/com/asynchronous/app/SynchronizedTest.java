package com.asynchronous.app;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class SynchronizedTest {

    private static final Random random = new Random();
    
    public static void main(String[] args) {
        run();
    }

    public static void run() {

        Callable<Quotation> fetchQuotationA = () -> {
            Thread.sleep(random.nextInt(1, 100));
            return new Quotation(random.nextInt(1, 100), "server-A");
        };

        Callable<Quotation> fetchQuotationB = () -> {
            Thread.sleep(random.nextInt(1, 100));
            return new Quotation(random.nextInt(1, 100), "server-B");
        };

        Callable<Quotation> fetchQuotationC = () -> {
            Thread.sleep(random.nextInt(1, 100));
            return new Quotation(random.nextInt(1, 100), "server-C");
        };

        var startInstance = Instant.now();

        var taskList = List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);

        var bestQuotation = taskList.stream()
                .map(SynchronizedTest::executeTask)
                .min(Comparator.comparing(Quotation::value))
                .orElseThrow();

        var endInstance = Instant.now();

        System.out.println("Best Quotation is [" + bestQuotation.value() + "] " +
                           "Best server is [" + bestQuotation.serverDescription() + "]" +
                           " (millis) " + Duration.between(endInstance, startInstance).toMillis());
    }

    private static Quotation executeTask(Callable<Quotation> task) {
        try {
            return task.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}