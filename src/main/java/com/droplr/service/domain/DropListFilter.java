package com.droplr.service.domain;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class DropListFilter {

    // enums ----------------------------------------------------------------------------------------------------------

    public static enum Order {
        ASC, DESC
    }

    public static enum SortCriteria {
        CODE, CREATION, TITLE, SIZE, ACTIVITY, VIEWS,
    }

    // internal vars --------------------------------------------------------------------------------------------------

    private Long         since;
    private Long         until;
    private Integer      offset;
    private Integer      amount;
    private Drop.Type    type;
    private SortCriteria sortBy;
    private Order        order;
    private String       search;

    // constructors ---------------------------------------------------------------------------------------------------

    public DropListFilter() {
    }

    public DropListFilter(long since) {
        this.since = since;
    }

    public DropListFilter(int startIndex, int amount, Drop.Type type) {
        this.offset = startIndex;
        this.amount = amount;
        this.type = type;
    }

    // public methods -------------------------------------------------------------------------------------------------

    public boolean isEmpty() {
        return (this.since == null) &&
               (this.until == null) &&
               (this.amount == null) &&
               (this.offset == null) &&
               (this.type == null) &&
               (this.sortBy == null) &&
               (this.order == null) &&
               (this.search == null);
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public Long getSince() {
        return since;
    }

    public void setSince(Long since) {
        this.since = since;
    }

    public Long getUntil() {
        return until;
    }

    public void setUntil(Long until) {
        this.until = until;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Drop.Type getType() {
        return type;
    }

    public void setType(Drop.Type type) {
        this.type = type;
    }

    public SortCriteria getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortCriteria sortBy) {
        this.sortBy = sortBy;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    // low level overrides --------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ListFilter{")
                .append("since=").append(this.since)
                .append(", until=").append(this.until)
                .append(", offset=").append(this.offset)
                .append(", amount=").append(this.amount)
                .append(", type='").append(this.type).append('\'')
                .append(", sortBy='").append(this.sortBy).append('\'')
                .append(", order='").append(this.order).append('\'')
                .append(", search='").append(this.search).append('\'')
                .append('}').toString();
    }
}
