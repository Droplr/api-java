package com.droplr.service.parsing;

import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Account;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersAccountResponseProcessor implements HttpResponseProcessor<Account> {

    // internal vars --------------------------------------------------------------------------------------------------

    private Account account;

    // HttpResponseProcessor ------------------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!response.getStatus().equals(HttpResponseStatus.OK)) {
            return false;
        }

        this.account = new Account();
        this.account.setAccountId(HeadersParsingUtils.getMandatoryStringField(response, CustomHeaders.USER_ID));
        this.account.setCreatedAt(HeadersParsingUtils.getMandatoryLongField(response, CustomHeaders.USER_CREATED_AT));
        this.account.setType(HeadersParsingUtils.getMandatoryEnumField(response, CustomHeaders.USER_TYPE,
                                                                       Account.Type.class));
        this.account.setSubscriptionEnd(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_SUBSCRIPTION_END));
        this.account.setExtraSpace(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_EXTRA_SPACE, 0L));
        this.account.setUsedSpace(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_USED_SPACE, 0L));
        this.account.setEmail(HeadersParsingUtils.getMandatoryStringField(response, CustomHeaders.USER_EMAIL));
        this.account.setDomain(HeadersParsingUtils.getStringField(response, CustomHeaders.USER_DOMAIN));
        this.account.setUseDomain(HeadersParsingUtils.getBooleanField(response, CustomHeaders.USER_USE_DOMAIN));
        this.account.setRootRedirect(HeadersParsingUtils.getStringField(response, CustomHeaders.USER_ROOT_REDIRECT));
        this.account.setUseRootRedirect(HeadersParsingUtils.getBooleanField(response,
                                                                            CustomHeaders.USER_USE_ROOT_REDIRECT));
        this.account.setDropPrivacy(HeadersParsingUtils.getEnumField(response, CustomHeaders.USER_DROP_PRIVACY,
                                                                     AbstractDrop.Privacy.PUBLIC,
                                                                     AbstractDrop.Privacy.class));
        this.account.setActiveDrops(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_ACTIVE_DROPS, 0L));
        this.account.setDropCount(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_DROP_COUNT, 0L));
        this.account.setTotalSpace(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_TOTAL_SPACE, 0L));
        this.account.setMaxUploadSize(HeadersParsingUtils.getLongField(response, CustomHeaders.USER_MAX_UPLOAD_SIZE, 0L));

        return true;
    }

    @Override
    public void addData(ChannelBuffer content) throws Exception {
        // no-op
    }

    @Override
    public void addLastData(ChannelBuffer content) throws Exception {
        // no-op
    }

    @Override
    public Account getProcessedResponse() {
        return this.account;
    }
}
