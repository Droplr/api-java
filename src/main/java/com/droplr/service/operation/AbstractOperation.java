package com.droplr.service.operation;

import com.droplr.http.CannotExecuteRequestException;
import com.droplr.http.future.HttpDataSinkListener;
import com.droplr.http.future.HttpRequestFuture;
import com.droplr.http.processor.HttpResponseProcessor;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public abstract class AbstractOperation<T> {

    // internal vars --------------------------------------------------------------------------------------------------

    protected final HttpRequest                request;
    protected final HttpResponseProcessor<T>   responseProcessor;
    protected final OperationSubmissionHandler submissionHandler;

    // constructors ---------------------------------------------------------------------------------------------------

    protected AbstractOperation(HttpRequest request, HttpResponseProcessor<T> responseProcessor,
                                OperationSubmissionHandler submissionHandler) {

        this.request = request;
        this.responseProcessor = responseProcessor;
        this.submissionHandler = submissionHandler;
    }

    // public methods -------------------------------------------------------------------------------------------------

    public HttpRequestFuture<T> executeAsync() throws CannotExecuteRequestException {
        return this.submissionHandler.submit(this);
    }

    public HttpRequestFuture<T> executeSync() throws CannotExecuteRequestException {
        return this.submissionHandler.submit(this).awaitUninterruptibly();
    }

    /**
     * Upload classes should override this method and return {@code true}. When this method returns {@code true}
     * {@link #getDataSinkListener()} should return a non-null value if and only if the 'Expects: 100-Continue' header
     * was used on the {@link HttpRequest}.
     *
     * @return {@code true} if this operation is an upload, {@code false} otherwise.
     */
    public boolean isUpload() {
        return false;
    }

    /**
     * The {@link HttpDataSinkListener} for to this operation. Typically the subclass will implement the interface
     * directly and act as a data sink listener itself.
     *
     * <code>
     *     public class SomeOperation extends AbstractOperation&lt;SomeType&gt; implements HttpDataSinkListener {
     *         // ...
     *         public HttpDataSinkListener getDataSinkListener() {
     *             return this;
     *         }
     *     }
     * </code>
     *
     * Uploads using the 'Expects: 100-Continue' can be configured to use a data sink to avoid sending the upload body
     * before the server responds with '100-Continue'.
     *
     * @return A {@link HttpDataSinkListener} associated with this request.
     */
    public HttpDataSinkListener getDataSinkListener() {
        return null;
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponseProcessor<T> getResponseProcessor() {
        return responseProcessor;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "AbstractOperation{" +
               "request=" + request +
               '}';
    }
}
