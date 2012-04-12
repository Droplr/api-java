package com.droplr.service;

import com.droplr.http.future.HttpRequestFuture;
import com.droplr.service.domain.DropCreation;
import com.droplr.service.operation.CreateDropOperation;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class CreateDropExamples {

    private static void shortenLinkExample() {
        AppCredentials credentials = AppCredentials.credentials("your_public_key", "your_private_key");
        UserCredentials userCredentials = UserCredentials.credentialsWithClearPassword("user@domain.com", "pass");

        DefaultDroplrService service = DefaultDroplrService.developmentService("ShortenLinkExample/1.0", credentials);
        service.setDefaultUserCredentials(userCredentials);

        // Service must always be initialized
        if (!service.init()) {
            System.err.println("Could not initialize service");
            service.terminate();
            return;
        }

        CreateDropOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.shortenLink("http://this-is-a-test.com/wat");
        } catch (Exception e) {
            System.err.println("Could not create operation: " + e.getMessage());
            service.terminate();
            return;
        }

        System.out.println("Launching request...");
        System.out.println(op.getRequest());

        System.out.println("---");

        HttpRequestFuture<DropCreation> future = op.executeSync();
        if (future.isCancelled()) {
            System.err.println("Request was cancelled.");
        } else if (!future.isSuccess()) {
            System.err.println("Request failed to execute: " + future.getCause().getMessage());
            future.getCause().printStackTrace();
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

    private static void uploadInMemoryDataExample() {
        // TODO
    }

    private static void createNoteExample() {
        // TODO
    }

    public static void main(String[] args) {
        shortenLinkExample();
        createNoteExample();
        uploadInMemoryDataExample();
    }
}
