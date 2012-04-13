package com.droplr.service.serialization;

import com.biasedbit.http.processor.HttpResponseProcessor;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Account;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersAccountDeserializer implements HttpResponseProcessor<Account> {

    // internal vars --------------------------------------------------------------------------------------------------

    private Account account;

    // HttpResponseProcessor ------------------------------------------------------------------------------------------

    @Override
    public boolean willProcessResponse(HttpResponse response) throws Exception {
        if (!response.getStatus().equals(HttpResponseStatus.OK)) {
            return false;
        }

        // Get the values from the headers
        String accountId = HeadersSerializationUtils
                .getMandatoryStringField(response, HeadersSerializationUtils.USER_ID);
        Long createdAt = HeadersSerializationUtils
                .getMandatoryLongField(response, HeadersSerializationUtils.USER_CREATED_AT);
        Account.Type type = HeadersSerializationUtils
                .getMandatoryEnumField(response, HeadersSerializationUtils.USER_TYPE, Account.Type.class);
        Long subscriptionEnd = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_SUBSCRIPTION_END);
        Long extraSpace = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_EXTRA_SPACE, 0L);
        Long usedSpace = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_USED_SPACE, 0L);
        String email = HeadersSerializationUtils
                .getMandatoryStringField(response, HeadersSerializationUtils.USER_EMAIL);
        String domain = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.USER_DOMAIN);
        Boolean useDomain = HeadersSerializationUtils
                .getBooleanField(response, HeadersSerializationUtils.USER_USE_DOMAIN);
        String rootRedirect = HeadersSerializationUtils
                .getStringField(response, HeadersSerializationUtils.USER_ROOT_REDIRECT);
        Boolean useRootRedirect = HeadersSerializationUtils
                .getBooleanField(response, HeadersSerializationUtils.USER_USE_ROOT_REDIRECT);
        AbstractDrop.Privacy dropPrivacy = HeadersSerializationUtils
                .getEnumField(response, HeadersSerializationUtils.USER_DROP_PRIVACY,
                              AbstractDrop.Privacy.PUBLIC, AbstractDrop.Privacy.class);
        Long activeDrops = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_ACTIVE_DROPS, 0L);
        Long dropCount = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_DROP_COUNT, 0L);
        Long totalSpace = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_TOTAL_SPACE, 0L);
        Long maxUploadSize = HeadersSerializationUtils
                .getLongField(response, HeadersSerializationUtils.USER_MAX_UPLOAD_SIZE, 0L);

        // And build the account
        this.account = new Account();
        this.account.setAccountId(accountId);
        this.account.setCreatedAt(createdAt);
        this.account.setType(type);
        this.account.setSubscriptionEnd(subscriptionEnd);
        this.account.setExtraSpace(extraSpace);
        this.account.setUsedSpace(usedSpace);
        this.account.setEmail(email);
        this.account.setDomain(domain);
        this.account.setUseDomain(useDomain);
        this.account.setRootRedirect(rootRedirect);
        this.account.setUseRootRedirect(useRootRedirect);
        this.account.setDropPrivacy(dropPrivacy);
        this.account.setActiveDrops(activeDrops);
        this.account.setDropCount(dropCount);
        this.account.setTotalSpace(totalSpace);
        this.account.setMaxUploadSize(maxUploadSize);

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
