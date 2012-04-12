package com.droplr.service.parsing;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public interface CustomHeaders {

    static final String PREFIX = "x-droplr-";

    static final String ERROR_CODE      = PREFIX + "errorcode";
    static final String ERROR_DETAILS   = PREFIX + "errordetails";
    static final String USER_MAX_UPLOAD_SIZE = PREFIX + "maxuploadsize";
    static final String FILENAME        = PREFIX + "filename";
    static final String DATE            = PREFIX + "date";
    static final String USER_ID                = PREFIX + "id";

    static final String USER_CREATED_AT        = PREFIX + "createdat";
    static final String USER_TYPE              = PREFIX + "type";
    static final String USER_SUBSCRIPTION_END  = PREFIX + "subscriptionend";
    static final String USER_TOTAL_SPACE       = PREFIX + "totalspace";
    static final String USER_EXTRA_SPACE       = PREFIX + "extraspace";
    static final String USER_USED_SPACE        = PREFIX + "usedspace";
    static final String USER_EMAIL             = PREFIX + "email";
    static final String USER_PASSWORD          = PREFIX + "password";
    static final String USER_ACTIVE_DROPS      = PREFIX + "activedrops";
    static final String USER_DROP_COUNT        = PREFIX + "dropcount";
    static final String USER_DOMAIN            = PREFIX + "domain";
    static final String USER_USE_DOMAIN        = PREFIX + "usedomain";
    static final String USER_ROOT_REDIRECT     = PREFIX + "rootredirect";
    static final String USER_USE_ROOT_REDIRECT = PREFIX + "userootredirect";
    static final String USER_DROP_PRIVACY      = PREFIX + "dropprivacy";
    static final String USER_THEME             = PREFIX + "theme";
    static final String DROP_CODE            = PREFIX + "code";

    static final String DROP_CREATED_AT      = PREFIX + "createdat";
    static final String DROP_TYPE            = PREFIX + "type";
    static final String DROP_VARIANT         = PREFIX + "variant";
    static final String DROP_TITLE           = PREFIX + "title";
    static final String DROP_SIZE            = PREFIX + "size";
    static final String DROP_SHORT_LINK      = PREFIX + "shortlink";
    static final String DROP_VIEWS           = PREFIX + "views";
    static final String DROP_LAST_ACCESS     = PREFIX + "lastaccess";
    static final String DROP_FILE_CREATED_AT = PREFIX + "filecreatedat";
    static final String DROP_WIDTH           = PREFIX + "width";
    static final String DROP_HEIGHT          = PREFIX + "height";
    static final String DROP_LENGTH          = PREFIX + "length";
    static final String DROP_PREVIEW_THUMB   = PREFIX + "previewthumb";
    static final String DROP_PREVIEW_SMALL   = PREFIX + "previewsmall";
    static final String DROP_PREVIEW_MEDIUM  = PREFIX + "previewmedium";
    static final String DROP_PRIVACY         = PREFIX + "privacy";
    static final String DROP_PASSWORD        = PREFIX + "password";
    static final String DROP_OBSCURE_CODE    = PREFIX + "obscurecode";
}
