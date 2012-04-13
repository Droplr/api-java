package com.droplr.service.operation;

import com.biasedbit.http.connection.HttpDataSink;
import com.biasedbit.http.future.HttpDataSinkListener;
import com.biasedbit.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.DropCreation;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class CreateDropOperation extends AbstractOperation<DropCreation> implements HttpDataSinkListener {

    // constants ------------------------------------------------------------------------------------------------------

    public static final int DEFAULT_CHUNK_SIZE = 8192;

    // configuration --------------------------------------------------------------------------------------------------

    private int chunkSize;

    // internal vars --------------------------------------------------------------------------------------------------

    private ChannelBuffer data;

    // constructors ---------------------------------------------------------------------------------------------------

    public CreateDropOperation(HttpRequest request, byte[] dataToSend,
                               HttpResponseProcessor<DropCreation> responseProcessor,
                               OperationSubmissionHandler submissionHandler) {
        super(request, responseProcessor, submissionHandler);
        this.data = ChannelBuffers.wrappedBuffer(dataToSend);

        // Chunk size defaults to the same default size as the one in AbstractHttpClient; make sure you change this
        // via the setter if you change it on the HttpClient instance that's going to run this request.
        this.chunkSize = DEFAULT_CHUNK_SIZE;
    }

    // AbstractOperation ----------------------------------------------------------------------------------------------

    @Override
    public boolean isUpload() {
        return true;
    }

    @Override
    public HttpDataSinkListener getDataSinkListener() {
        return this;
    }

    // HttpDataSinkListener -------------------------------------------------------------------------------------------

    @Override
    public void readyToSendData(HttpDataSink sink) {
        ChannelBuffer chunk = this.readChunk();
        sink.sendData(chunk, !this.hasMoreDataToSend());
    }

    @Override
    public void writeComplete(HttpDataSink sink, long bytesWritten) {
        ChannelBuffer chunk = this.readChunk();
        sink.sendData(chunk, !this.hasMoreDataToSend());
    }

    // private helpers ------------------------------------------------------------------------------------------------

    private ChannelBuffer readChunk() {
        if (!this.hasMoreDataToSend()) {
            return null;
        }

        int bytesToTransfer = Math.min(this.chunkSize, this.data.readableBytes());
        ChannelBuffer buffer = ChannelBuffers.buffer(bytesToTransfer);
        this.data.readBytes(buffer);

        return buffer;
    }

    private boolean hasMoreDataToSend() {
        return this.data.readableBytes() > 0;
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }
}
