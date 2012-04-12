package com.droplr.service.domain;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class Account {

    // enums ----------------------------------------------------------------------------------------------------------

    public static enum Type {
        REGULAR, PRO
    }

    // internal vars --------------------------------------------------------------------------------------------------

    private String               accountId;
    private Long                 createdAt;
    private Type                 type;
    private Long                 subscriptionEnd;
    private Long                 extraSpace;
    private Long                 usedSpace;
    private String               email;
    private String               password;
    private String               domain;
    private Boolean              useDomain;
    private String               rootRedirect;
    private Boolean              useRootRedirect;
    private AbstractDrop.Privacy dropPrivacy;
    private Long                 activeDrops;
    private Long                 dropCount;
    private Long                 totalSpace;
    private Long                 maxUploadSize;

    // constructors ---------------------------------------------------------------------------------------------------

    public Account() {
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public void setSubscriptionEnd(Long subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }

    public Long getExtraSpace() {
        return extraSpace;
    }

    public void setExtraSpace(Long extraSpace) {
        this.extraSpace = extraSpace;
    }

    public Long getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(Long usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getUseDomain() {
        return useDomain;
    }

    public void setUseDomain(Boolean useDomain) {
        this.useDomain = useDomain;
    }

    public String getRootRedirect() {
        return rootRedirect;
    }

    public void setRootRedirect(String rootRedirect) {
        this.rootRedirect = rootRedirect;
    }

    public Boolean getUseRootRedirect() {
        return useRootRedirect;
    }

    public void setUseRootRedirect(Boolean useRootRedirect) {
        this.useRootRedirect = useRootRedirect;
    }

    public AbstractDrop.Privacy getDropPrivacy() {
        return dropPrivacy;
    }

    public void setDropPrivacy(AbstractDrop.Privacy dropPrivacy) {
        this.dropPrivacy = dropPrivacy;
    }

    public Long getActiveDrops() {
        return activeDrops;
    }

    public void setActiveDrops(Long activeDrops) {
        this.activeDrops = activeDrops;
    }

    public Long getDropCount() {
        return dropCount;
    }

    public void setDropCount(Long dropCount) {
        this.dropCount = dropCount;
    }

    public Long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(Long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public Long getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(Long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Account{")
                .append("accountId='").append(this.accountId).append('\'')
                .append(", createdAt=").append(this.createdAt)
                .append(", type=").append(this.type)
                .append(", subscriptionEnd=").append(this.subscriptionEnd)
                .append(", extraSpace=").append(this.extraSpace)
                .append(", usedSpace=").append(this.usedSpace)
                .append(", email='").append(this.email).append('\'')
                .append(", domain='").append(this.domain).append('\'')
                .append(", useDomain=").append(this.useDomain)
                .append(", rootRedirect='").append(this.rootRedirect).append('\'')
                .append(", useRootRedirect=").append(this.useRootRedirect)
                .append(", dropPrivacy=").append(this.dropPrivacy)
                .append(", activeDrops=").append(this.activeDrops)
                .append(", dropCount=").append(this.dropCount)
                .append(", totalSpace=").append(this.totalSpace)
                .append(", maxUploadSize=").append(this.maxUploadSize)
                .append('}')
                .toString();
    }
}
