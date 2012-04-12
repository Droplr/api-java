package com.droplr.service.operation;

import com.droplr.http.CannotExecuteRequestException;
import com.droplr.http.future.HttpRequestFuture;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public interface OperationSubmissionHandler {

    <T> HttpRequestFuture<T> submit(AbstractOperation<T> operation) throws CannotExecuteRequestException;
}
