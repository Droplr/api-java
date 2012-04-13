package com.droplr.service.operation;

import com.biasedbit.http.processor.DiscardProcessor;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class DeleteDropOperation extends AbstractOperation<Object> {

    // constructors ---------------------------------------------------------------------------------------------------

    public DeleteDropOperation(HttpRequest request, OperationSubmissionHandler submissionHandler) {
        super(request, new DiscardProcessor(), submissionHandler);
    }
}
