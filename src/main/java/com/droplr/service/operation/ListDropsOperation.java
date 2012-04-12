package com.droplr.service.operation;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.Drop;
import org.jboss.netty.handler.codec.http.HttpRequest;

import java.util.List;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ListDropsOperation extends AbstractOperation<List<Drop>> {

    // constructors ---------------------------------------------------------------------------------------------------

    public ListDropsOperation(HttpRequest request,
                              HttpResponseProcessor<List<Drop>> responseProcessor,
                              OperationSubmissionHandler submissionHandler) {
        super(request, responseProcessor, submissionHandler);
    }
}
