package com.droplr.service.parsing;

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
public class HeadersDropCreationResponseProcessor implements HttpResponseProcessor<DropCreation> {

    // internal vars --------------------------------------------------------------------------------------------------

    private DropCreation drop;

    // HttpResponseProcessor ------------------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!HttpResponseStatus.CREATED.equals(response.getStatus())) {
            return false;
        }

        this.drop = new DropCreation();
        this.drop.setCode(HeadersParsingUtils.getMandatoryStringField(response, CustomHeaders.DROP_CODE));
        this.drop.setCreatedAt(HeadersParsingUtils.getMandatoryLongField(response, CustomHeaders.DROP_CREATED_AT));
        this.drop.setType(HeadersParsingUtils.getMandatoryEnumField(response, CustomHeaders.DROP_TYPE, Drop.Type.class));
        this.drop.setVariant(HeadersParsingUtils.getStringField(response, CustomHeaders.DROP_VARIANT, null));
        this.drop.setTitle(HeadersParsingUtils.getMandatoryBase64StringField(response, CustomHeaders.DROP_TITLE));
        this.drop.setSize(HeadersParsingUtils.getMandatoryIntegerField(response, CustomHeaders.DROP_SIZE));
        this.drop.setShortlink(HeadersParsingUtils.getMandatoryStringField(response, CustomHeaders.DROP_SHORT_LINK));
        this.drop.setFileCreatedAt(HeadersParsingUtils.getLongField(response, CustomHeaders.DROP_FILE_CREATED_AT));
        this.drop.setPrivacy(HeadersParsingUtils.getEnumField(response, CustomHeaders.DROP_PRIVACY,
                                                              AbstractDrop.Privacy.PUBLIC, AbstractDrop.Privacy.class));
        this.drop.setObscureCode(HeadersParsingUtils.getMandatoryStringField(response, CustomHeaders.DROP_OBSCURE_CODE));
        this.drop.setPassword(HeadersParsingUtils.getStringField(response, CustomHeaders.DROP_PASSWORD, null));
        this.drop.setTotalSpace(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_TOTAL_SPACE, 0L));
        this.drop.setUsedSpace(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_USED_SPACE, 0L));

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
