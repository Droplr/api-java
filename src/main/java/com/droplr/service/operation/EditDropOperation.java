package com.droplr.service.operation;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.Drop;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class EditDropOperation extends AbstractOperation<Drop> {

    // constructors ---------------------------------------------------------------------------------------------------

    public EditDropOperation(HttpRequest request,
                             HttpResponseProcessor<Drop> responseProcessor,
                             OperationSubmissionHandler submissionHandler) {
        super(request, responseProcessor, submissionHandler);
    }
}
