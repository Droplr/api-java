package com.droplr.service.domain;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class Drop extends AbstractDrop {

    // internal vars --------------------------------------------------------------------------------------------------

    private Long    views;
    private String  content;
    private Integer width;
    private Integer height;
    private Integer length;
    private String  thumbPreview;
    private String  smallPreview;
    private String  mediumPreview;

    // constructors ---------------------------------------------------------------------------------------------------

    public Drop() {
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getThumbPreview() {
        return thumbPreview;
    }

    public void setThumbPreview(String thumbPreview) {
        this.thumbPreview = thumbPreview;
    }

    public String getSmallPreview() {
        return smallPreview;
    }

    public void setSmallPreview(String smallPreview) {
        this.smallPreview = smallPreview;
    }

    public String getMediumPreview() {
        return mediumPreview;
    }

    public void setMediumPreview(String mediumPreview) {
        this.mediumPreview = mediumPreview;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Drop{")
                .append("code='").append(this.code).append('\'')
                .append(", createdAt=").append(this.createdAt)
                .append(", type=").append(this.type)
                .append(", title='").append(this.title).append('\'')
                .append(", size=").append(this.size)
                .append(", shortlink='").append(this.shortlink).append('\'')
                .append(", privacy=").append(this.privacy)
                .append(", views=").append(this.views)
//                .append(", content='").append(this.content).append('\'')
                .append(", width=").append(this.width)
                .append(", height=").append(this.height)
                .append(", length=").append(this.length)
                .append('}')
                .toString();
    }
}
