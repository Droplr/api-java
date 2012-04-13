package com.droplr.service.operation;

import com.biasedbit.http.CannotExecuteRequestException;
import com.biasedbit.http.future.HttpRequestFuture;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public interface OperationSubmissionHandler {

    <T> HttpRequestFuture<T> submit(AbstractOperation<T> operation) throws CannotExecuteRequestException;
}
