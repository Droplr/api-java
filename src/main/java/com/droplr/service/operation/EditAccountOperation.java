package com.droplr.service.operation;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.Account;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class EditAccountOperation extends AbstractOperation<Account> {

    // constructors ---------------------------------------------------------------------------------------------------

    public EditAccountOperation(HttpRequest request,
                                HttpResponseProcessor<Account> responseProcessor,
                                OperationSubmissionHandler submissionHandler) {
        super(request, responseProcessor, submissionHandler);
    }
}
