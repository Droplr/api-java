package com.droplr.service.serialization;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Drop;
import com.droplr.service.domain.DropCreation;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersDropCreationDeserializer implements HttpResponseProcessor<DropCreation> {

    // internal vars --------------------------------------------------------------------------------------------------

    private DropCreation drop;

    // HttpResponseProcessor ------------------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!HttpResponseStatus.CREATED.equals(response.getStatus())) {
            return false;
        }

        // Get the values from the headers
        String code = HeadersSerializationUtils
                .getMandatoryStringField(response, HeadersSerializationUtils.DROP_CODE);
        Long createdAt = HeadersSerializationUtils
                .getMandatoryLongField(response, HeadersSerializationUtils.DROP_CREATED_AT);
        AbstractDrop.Type type = HeadersSerializationUtils
                .getMandatoryEnumField(response, HeadersSerializationUtils.DROP_TYPE, Drop.Type.class);
        String variant = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.DROP_VARIANT);
        String title = HeadersSerializationUtils
                .getMandatoryBase64StringField(response, HeadersSerializationUtils.DROP_TITLE);
        Integer size = HeadersSerializationUtils
                .getMandatoryIntegerField(response, HeadersSerializationUtils.DROP_SIZE);
        String shortlink = HeadersSerializationUtils
                .getMandatoryStringField(response, HeadersSerializationUtils.DROP_SHORT_LINK);
        Long fileCreatedAt = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.DROP_FILE_CREATED_AT);
        AbstractDrop.Privacy privacy = HeadersSerializationUtils
                .getEnumField(response, HeadersSerializationUtils.DROP_PRIVACY,
                              AbstractDrop.Privacy.PUBLIC, AbstractDrop.Privacy.class);
        String obscureCode = HeadersSerializationUtils
                .getMandatoryStringField(response, HeadersSerializationUtils.DROP_OBSCURE_CODE);
        String password = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.DROP_PASSWORD);
        Long totalSpace = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_TOTAL_SPACE, 0L);
        Long usedSpace = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_USED_SPACE, 0L);

        // And build the drop
        this.drop = new DropCreation();
        this.drop.setCode(code);
        this.drop.setCreatedAt(createdAt);
        this.drop.setType(type);
        this.drop.setVariant(variant);
        this.drop.setTitle(title);
        this.drop.setSize(size);
        this.drop.setShortlink(shortlink);
        this.drop.setFileCreatedAt(fileCreatedAt);
        this.drop.setPrivacy(privacy);
        this.drop.setObscureCode(obscureCode);
        this.drop.setPassword(password);
        this.drop.setTotalSpace(totalSpace);
        this.drop.setUsedSpace(usedSpace);

        return true;
    }

    @Override
    public void addData(ChannelBuffer channelBuffer) throws Exception {
        // no-op
    }

    @Override
    public void addLastData(ChannelBuffer channelBuffer) throws Exception {
        // no-op
    }

    @Override
    public DropCreation getProcessedResponse() {
        return this.drop;
    }
}
