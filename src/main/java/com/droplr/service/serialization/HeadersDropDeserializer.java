package com.droplr.service.serialization;

import com.droplr.http.processor.AbstractAccumulatorProcessor;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Drop;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersDropDeserializer extends AbstractAccumulatorProcessor<Drop> {

    // internal vars --------------------------------------------------------------------------------------------------

    private Drop drop;

    // AbstractAccumulatorProcessor -----------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!super.willProcessResponse(response)) {
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
        Long views = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.DROP_VIEWS, 0L);
        Integer width = HeadersSerializationUtils
                .getIntegerField(response, HeadersSerializationUtils.DROP_WIDTH);
        Integer height = HeadersSerializationUtils
                .getIntegerField(response, HeadersSerializationUtils.DROP_HEIGHT);
        Integer length = HeadersSerializationUtils
                .getIntegerField(response, HeadersSerializationUtils.DROP_LENGTH);
        String thumbPreview = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.DROP_PREVIEW_THUMB);
        String smallPreview = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.DROP_PREVIEW_SMALL);
        String mediumPreview = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.DROP_PREVIEW_MEDIUM);

        // Read note on convertBufferToResult()
        if (this.drop == null) {
            this.drop = new Drop();
        }

        // And build the drop
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
        this.drop.setViews(views);
        this.drop.setWidth(width);
        this.drop.setHeight(height);
        this.drop.setLength(length);
        this.drop.setThumbPreview(thumbPreview);
        this.drop.setSmallPreview(smallPreview);
        this.drop.setMediumPreview(mediumPreview);

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
