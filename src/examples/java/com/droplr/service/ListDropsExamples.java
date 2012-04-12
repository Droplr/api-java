package com.droplr.service;

import com.droplr.http.future.HttpRequestFuture;
import com.droplr.http.future.HttpRequestFutureListener;
import com.droplr.service.auth.AppCredentials;
import com.droplr.service.auth.UserCredentials;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Drop;
import com.droplr.service.domain.DropListFilter;
import com.droplr.service.operation.ListDropsOperation;

import java.util.List;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ListDropsExamples {

    private static void listDropsAsynchronouslyWithFilter() {
        AppCredentials credentials = AppCredentials.credentials("your_public_key", "your_private_key");
        UserCredentials userCredentials = UserCredentials.credentialsWithClearPassword("user@domain.com", "pass");


        DefaultDroplrService service = DefaultDroplrService.developmentService("ListDropsExample/1.0", credentials);

        service.setDefaultUserCredentials(userCredentials);

        // Service must always be initialized
        if (!service.init()) {
            System.err.println("Could not initialize service");
            service.terminate();
            return;
        }

        DropListFilter filter = new DropListFilter();
        filter.setType(AbstractDrop.Type.FILE);
        filter.setAmount(5);
        filter.setSortBy(DropListFilter.SortCriteria.CREATION);
        filter.setOrder(DropListFilter.Order.DESC);

        ListDropsOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.listDrops(filter);
        } catch (Exception e) {
            System.err.println("Could not create operation: " + e.getMessage());
            service.terminate();
            return;
        }

        System.out.println("Launching request...");
        System.out.println(op.getRequest());

        System.out.println("---");

        HttpRequestFuture<List<Drop>> future = op.executeAsync();
        future.addListener(new HttpRequestFutureListener<List<Drop>>() {
            @Override
            public void operationComplete(HttpRequestFuture<List<Drop>> f) throws Exception {
                if (f.isCancelled()) {
                    System.err.println("Request was cancelled.");
                } else if (!f.isSuccess()) {
                    System.err.println("Request failed to execute: " + f.getCause().getMessage());
                } else if (!f.isSuccessfulResponse()) {
                    System.err.println("Server replied with error: " + f.getResponseStatusCode());
                    System.err.println(f.getResponse());
                } else {
                    System.out.println("Request successfully executed; loaded " +
                                       f.getProcessedResult().size() + " drops");
                    for (Drop drop : f.getProcessedResult()) {
                        System.out.println(" - " + drop);
                    }
                    System.out.println(f.getResponse());
                }
            }
        });

        // Lock this thread until request ends; normally this is where you'd go about doing other stuff with your app
        future.awaitUninterruptibly();
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ignored) {
            // ignored
        }

        // When you're done, don't forget to cleanup the service
        System.out.println("---");
        System.out.println("Shutting down service...");
        service.terminate();
    }

    private static void listDropsSynchronouslyWithNoFilter() {
        AppCredentials credentials = AppCredentials.credentials("your_public_key", "your_private_key");
        UserCredentials userCredentials = UserCredentials.credentialsWithClearPassword("user@domain.com", "pass");

        DefaultDroplrService service = DefaultDroplrService.developmentService("ListDropsExample/1.0", credentials);

        service.setDefaultUserCredentials(userCredentials);

        // Service must always be initialized
        if (!service.init()) {
            System.err.println("Could not initialize service");
            service.terminate();
            return;
        }

        ListDropsOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.listDrops();
        } catch (Exception e) {
            System.err.println("Could not create operation: " + e.getMessage());
            service.terminate();
            return;
        }

        System.out.println("Launching request...");
        System.out.println(op.getRequest());

        System.out.println("---");

        HttpRequestFuture<List<Drop>> future = op.executeSync();
        if (future.isCancelled()) {
            System.err.println("Request was cancelled.");
        } else if (!future.isSuccess()) {
            System.err.println("Request failed to execute: " + future.getCause().getMessage());
        } else if (!future.isSuccessfulResponse()) {
            System.err.println("Server replied with error: " + future.getResponseStatusCode());
            System.err.println(future.getResponse());
        } else {
            System.out.println("Request successfully executed; loaded " +
                               future.getProcessedResult().size() + " drops");
            for (Drop drop : future.getProcessedResult()) {
                System.out.println(" - " + drop);
            }
            System.out.println(future.getResponse());
        }

        // When you're done, don't forget to cleanup the service
        System.out.println("---");
        System.out.println("Shutting down service...");
        service.terminate();
    }

    public static void main(String[] args) {
        listDropsAsynchronouslyWithFilter();
        listDropsSynchronouslyWithNoFilter();
    }
}
