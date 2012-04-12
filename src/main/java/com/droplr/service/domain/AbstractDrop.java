package com.droplr.service.domain;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public abstract class AbstractDrop {

    // enums ----------------------------------------------------------------------------------------------------------

    public static enum Type {
        LINK, NOTE, AUDIO, VIDEO, IMAGE, FILE
    }

    public static enum NoteType {
        PLAIN, CODE, MARKDOWN, TEXTILE, HTML
    }

    public static enum Privacy {
        PUBLIC, OBSCURE, PRIVATE
    }

    // internal vars --------------------------------------------------------------------------------------------------

    protected String  code;
    protected Long    createdAt;
    protected Type    type;
    protected String  variant;
    protected String  title;
    protected Integer size;
    protected String  shortlink;
    protected Long    fileCreatedAt;
    protected Privacy privacy;
    protected String  obscureCode;
    protected String  password;

    // constructors ---------------------------------------------------------------------------------------------------

    protected AbstractDrop() {
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getShortlink() {
        return shortlink;
    }

    public void setShortlink(String shortlink) {
        this.shortlink = shortlink;
    }

    public Long getFileCreatedAt() {
        return fileCreatedAt;
    }

    public void setFileCreatedAt(Long fileCreatedAt) {
        this.fileCreatedAt = fileCreatedAt;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public String getObscureCode() {
        return obscureCode;
    }

    public void setObscureCode(String obscureCode) {
        this.obscureCode = obscureCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AbstractDrop{")
                .append("code='").append(this.code).append('\'')
                .append(", createdAt=").append(this.createdAt)
                .append(", type=").append(this.type)
                .append(", title='").append(this.title).append('\'')
                .append(", size=").append(this.size)
                .append(", shortlink='").append(this.shortlink).append('\'')
                .append(", privacy=").append(this.privacy)
                .append('}')
                .toString();
    }
}
