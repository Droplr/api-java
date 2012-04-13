package com.droplr.service.operation;

import com.biasedbit.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.Drop;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ReadDropOperation extends AbstractOperation<Drop> {

    // constructors ---------------------------------------------------------------------------------------------------

    public ReadDropOperation(HttpRequest request, HttpResponseProcessor<Drop> dropResponseProcessor,
                             OperationSubmissionHandler submissionHandler) {
        super(request, dropResponseProcessor, submissionHandler);
    }
}
