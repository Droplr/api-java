package com.droplr.service.domain;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class DropCreation extends AbstractDrop {

    // internal vars --------------------------------------------------------------------------------------------------

    private Long totalSpace;
    private Long usedSpace;

    // constructors ---------------------------------------------------------------------------------------------------

    public DropCreation() {
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public Long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(Long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public Long getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(Long usedSpace) {
        this.usedSpace = usedSpace;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("DropCreation{")
                .append("code='").append(this.code).append('\'')
                .append(", createdAt=").append(this.createdAt)
                .append(", type=").append(this.type)
                .append(", title='").append(this.title).append('\'')
                .append(", size=").append(this.size)
                .append(", shortlink='").append(this.shortlink).append('\'')
                .append(", privacy=").append(this.privacy)
                .append(", totalSpace=").append(this.totalSpace)
                .append(", usedSpace=").append(this.usedSpace)
                .append('}')
                .toString();
    }
}
