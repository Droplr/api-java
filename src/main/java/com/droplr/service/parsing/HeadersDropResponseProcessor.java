package com.droplr.service.parsing;

import com.droplr.http.processor.AbstractAccumulatorProcessor;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Drop;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersDropResponseProcessor extends AbstractAccumulatorProcessor<Drop> {

    // internal vars --------------------------------------------------------------------------------------------------

    private Drop drop;

    // AbstractAccumulatorProcessor -----------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!super.willProcessResponse(response)) {
            return false;
        }

        // Read note on convertBufferToResult()
        if (this.drop == null) {
            this.drop = new Drop();
        }

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
        this.drop.setViews(HeadersParsingUtils.getLongField(response, CustomHeaders.DROP_VIEWS, 0L));
        this.drop.setWidth(HeadersParsingUtils.getIntegerField(response, CustomHeaders.DROP_WIDTH, null));
        this.drop.setHeight(HeadersParsingUtils.getIntegerField(response, CustomHeaders.DROP_HEIGHT, null));
        this.drop.setLength(HeadersParsingUtils.getIntegerField(response, CustomHeaders.DROP_LENGTH, null));
        this.drop.setThumbPreview(HeadersParsingUtils.getStringField(response, CustomHeaders.DROP_PREVIEW_THUMB));
        this.drop.setSmallPreview(HeadersParsingUtils.getStringField(response, CustomHeaders.DROP_PREVIEW_SMALL));
        this.drop.setMediumPreview(HeadersParsingUtils.getStringField(response, CustomHeaders.DROP_PREVIEW_MEDIUM));

        return true;
    }

    @Override
    protected Drop convertBufferToResult(ChannelBuffer buffer) {
        // Drop may be null if the content is fully present when willProcessResponse() is called.
        // When this happens, willProcessResponse method of the AbstractAccumulatorProcessor will call
        // convertBufferToResult directly and return true. Therefore we need to double check whether the drop is null
        // before setting its content. The rest of the fields will be set later on willProcessResponse
        if (this.drop == null) {
            this.drop = new Drop();
        }

        // Convert the body's content to string and set the final field
        this.drop.setContent(buffer.toString(CharsetUtil.UTF_8));

        // This will make this.result and this.drop point to the same object; a double reference but it's acceptable
        return this.drop;
    }
}
