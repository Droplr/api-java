package com.droplr.service.operation;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.Account;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ReadAccountOperation extends AbstractOperation<Account> {

    // constructors ---------------------------------------------------------------------------------------------------

    public ReadAccountOperation(HttpRequest request, HttpResponseProcessor<Account> accountResponseProcessor,
                                OperationSubmissionHandler submissionHandler) {
        super(request, accountResponseProcessor, submissionHandler);
    }
}
