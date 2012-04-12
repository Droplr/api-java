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

        String link = "http://this-is-a-test.com/wat";

        // Before uploading, always make sure if the upload is valid with the methods in Validations class.
        // This validation will again be performed when creating the operation; if it fails to verify, an
        // IllegalArgumentException will be thrown.
        if (!Validations.isValidLink(link)) {
            System.err.println("Link is not valid");
            service.terminate();
            return;
        }

        CreateDropOperation op;
        try {
            // Creating operations may throw IllegalArgumentExceptions if the input parameters are invalid or
            // IllegalStateException if you don't pass user credentials and you forgot to set default user credentials.
            op = service.shortenLink(link);
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

    private static void uploadInMemoryDataExample() throws Exception {
        // TODO
    }

    private static void createNoteExample() throws Exception {
        // TODO
    }

    public static void main(String[] args) throws Exception {
        shortenLinkExample();
        createNoteExample();
        uploadInMemoryDataExample();
    }
}
