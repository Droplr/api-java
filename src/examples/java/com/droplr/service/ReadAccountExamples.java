package com.droplr.service;

import com.droplr.http.future.HttpRequestFuture;
import com.droplr.http.future.HttpRequestFutureListener;
import com.droplr.service.domain.Account;
import com.droplr.service.operation.ReadAccountOperation;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ReadAccountExamples {

    public static void synchronousExample() {
        AppCredentials credentials = AppCredentials.credentials("your_public_key", "your_private_key");
        UserCredentials userCredentials = UserCredentials.credentialsWithClearPassword("user@domain.com", "pass");

        DefaultDroplrService service = DefaultDroplrService.developmentService("ReadAccountExample/1.0", credentials);
        service.setDefaultUserCredentials(userCredentials);

        // Service must always be initialized
        if (!service.init()) {
            System.err.println("Could not initialize service");
            service.terminate();
            return;
        }

        ReadAccountOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.readAccount();
        } catch (Exception e) {
            System.err.println("Could not create operation: " + e.getMessage());
            service.terminate();
            return;
        }

        System.out.println("Launching request...");
        System.out.println(op.getRequest());

        System.out.println("---");

        HttpRequestFuture<Account> future = op.executeSync();
        if (future.isCancelled()) {
            System.err.println("Request was cancelled.");
        } else if (!future.isSuccess()) {
            System.err.println("Request failed to execute: " + future.getCause().getMessage());
        } else if (!future.isSuccessfulResponse()) {
            System.err.println("Server replied with error: " + future.getResponseStatusCode());
            System.err.println(future.getResponse());
        } else {
            System.out.println("Request successfully executed:\n" + future.getProcessedResult());
            System.out.println(future.getResponse());
        }

        System.out.println("---");

        // When you're done, don't forget to cleanup the service
        System.out.println("Shutting down service...");
        service.terminate();
    }

    public static void asynchronousExample() {
        AppCredentials credentials = AppCredentials.credentials("your_public_key", "your_private_key");
        UserCredentials userCredentials = UserCredentials.credentialsWithClearPassword("user@domain.com", "pass");

        DefaultDroplrService service = DefaultDroplrService.developmentService("ReadAccountExample/1.0", credentials);
        service.setDefaultUserCredentials(userCredentials);

        // Service must always be initialized
        if (!service.init()) {
            System.err.println("Could not initialize service");
            service.terminate();
            return;
        }

        ReadAccountOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.readAccount();
        } catch (Exception e) {
            System.err.println("Could not create operation: " + e.getMessage());
            service.terminate();
            return;
        }

        System.out.println("Launching request...");
        System.out.println(op.getRequest());

        System.out.println("---");

        HttpRequestFuture<Account> future = op.executeAsync();
        future.addListener(new HttpRequestFutureListener<Account>() {
            @Override
            public void operationComplete(HttpRequestFuture<Account> f) throws Exception {
                if (f.isCancelled()) {
                    System.err.println("Request was cancelled.");
                } else if (!f.isSuccess()) {
                    System.err.println("Request failed to execute: " + f.getCause().getMessage());
                } else if (!f.isSuccessfulResponse()) {
                    System.err.println("Server replied with error: " + f.getResponseStatusCode());
                    System.err.println(f.getResponse());
                } else {
                    System.out.println("Request successfully executed:\n" + f.getProcessedResult());
                    System.out.println(f.getResponse());
                }
            }
        });

        // Lock this thread until request ends; normally this is where you'd go about doing other stuff with your app
        future.awaitUninterruptibly();

        System.out.println("---");

        // When you're done, don't forget to cleanup the service
        System.out.println("Shutting down service...");
        service.terminate();
    }

    public static void main(String[] args) {
        synchronousExample();
        asynchronousExample();
    }
}
