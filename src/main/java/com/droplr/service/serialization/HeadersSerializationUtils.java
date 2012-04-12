package com.droplr.service.serialization;

import com.droplr.common.TextUtils;
import org.jboss.netty.handler.codec.http.HttpResponse;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersSerializationUtils {

    // constants ------------------------------------------------------------------------------------------------------

    public static final String CUSTOM_HEADER_PREFIX = "x-droplr-";

    public static final String ERROR_CODE    = CUSTOM_HEADER_PREFIX + "errorcode";
    public static final String ERROR_DETAILS = CUSTOM_HEADER_PREFIX + "errordetails";
    public static final String FILENAME      = CUSTOM_HEADER_PREFIX + "filename";
    public static final String DATE          = CUSTOM_HEADER_PREFIX + "date";

    public static final String USER_ID                = CUSTOM_HEADER_PREFIX + "id";
    public static final String USER_CREATED_AT        = CUSTOM_HEADER_PREFIX + "createdat";
    public static final String USER_TYPE              = CUSTOM_HEADER_PREFIX + "type";
    public static final String USER_SUBSCRIPTION_END  = CUSTOM_HEADER_PREFIX + "subscriptionend";
    public static final String USER_TOTAL_SPACE       = CUSTOM_HEADER_PREFIX + "totalspace";
    public static final String USER_EXTRA_SPACE       = CUSTOM_HEADER_PREFIX + "extraspace";
    public static final String USER_USED_SPACE        = CUSTOM_HEADER_PREFIX + "usedspace";
    public static final String USER_MAX_UPLOAD_SIZE   = CUSTOM_HEADER_PREFIX + "maxuploadsize";
    public static final String USER_EMAIL             = CUSTOM_HEADER_PREFIX + "email";
    public static final String USER_PASSWORD          = CUSTOM_HEADER_PREFIX + "password";
    public static final String USER_ACTIVE_DROPS      = CUSTOM_HEADER_PREFIX + "activedrops";
    public static final String USER_DROP_COUNT        = CUSTOM_HEADER_PREFIX + "dropcount";
    public static final String USER_DOMAIN            = CUSTOM_HEADER_PREFIX + "domain";
    public static final String USER_USE_DOMAIN        = CUSTOM_HEADER_PREFIX + "usedomain";
    public static final String USER_ROOT_REDIRECT     = CUSTOM_HEADER_PREFIX + "rootredirect";
    public static final String USER_USE_ROOT_REDIRECT = CUSTOM_HEADER_PREFIX + "userootredirect";
    public static final String USER_DROP_PRIVACY      = CUSTOM_HEADER_PREFIX + "dropprivacy";
    public static final String USER_THEME             = CUSTOM_HEADER_PREFIX + "theme";

    static final String DROP_CODE            = CUSTOM_HEADER_PREFIX + "code";
    static final String DROP_CREATED_AT      = CUSTOM_HEADER_PREFIX + "createdat";
    static final String DROP_TYPE            = CUSTOM_HEADER_PREFIX + "type";
    static final String DROP_VARIANT         = CUSTOM_HEADER_PREFIX + "variant";
    static final String DROP_TITLE           = CUSTOM_HEADER_PREFIX + "title";
    static final String DROP_SIZE            = CUSTOM_HEADER_PREFIX + "size";
    static final String DROP_SHORT_LINK      = CUSTOM_HEADER_PREFIX + "shortlink";
    static final String DROP_VIEWS           = CUSTOM_HEADER_PREFIX + "views";
    static final String DROP_LAST_ACCESS     = CUSTOM_HEADER_PREFIX + "lastaccess";
    static final String DROP_FILE_CREATED_AT = CUSTOM_HEADER_PREFIX + "filecreatedat";
    static final String DROP_WIDTH           = CUSTOM_HEADER_PREFIX + "width";
    static final String DROP_HEIGHT          = CUSTOM_HEADER_PREFIX + "height";
    static final String DROP_LENGTH          = CUSTOM_HEADER_PREFIX + "length";
    static final String DROP_PREVIEW_THUMB   = CUSTOM_HEADER_PREFIX + "previewthumb";
    static final String DROP_PREVIEW_SMALL   = CUSTOM_HEADER_PREFIX + "previewsmall";
    static final String DROP_PREVIEW_MEDIUM  = CUSTOM_HEADER_PREFIX + "previewmedium";
    static final String DROP_PRIVACY         = CUSTOM_HEADER_PREFIX + "privacy";
    static final String DROP_PASSWORD        = CUSTOM_HEADER_PREFIX + "password";
    static final String DROP_OBSCURE_CODE    = CUSTOM_HEADER_PREFIX + "obscurecode";

    // constructors ---------------------------------------------------------------------------------------------------

    private HeadersSerializationUtils() {
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static String getStringField(HttpResponse response, String fieldName) {
        return getStringField(response, fieldName, null);
    }

    public static String getStringField(HttpResponse response, String fieldName, String defaultValue) {
        String value = response.getHeader(fieldName);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public static String getMandatoryStringField(HttpResponse response, String fieldName)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        return value;
    }

    public static String getMandatoryBase64StringField(HttpResponse response, String fieldName)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        try {
            return TextUtils.base64Decode(value);
        } catch (RuntimeException e) {
            throw new MandatoryFieldException("Could not decode Base64 in field '" + fieldName + "'");
        }
    }

    public static Long getLongField(HttpResponse response, String fieldName) {
        return getLongField(response, fieldName, null);
    }

    public static Long getLongField(HttpResponse response, String fieldName, Long defaultIfNull) {
        String value = response.getHeader(fieldName);
        if (value == null) {
            return defaultIfNull;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultIfNull;
        }
    }

    public static Long getMandatoryLongField(HttpResponse response, String fieldName)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new MandatoryFieldException("Cannot convert '" + value +
                                              "' to Long for field '" + fieldName + "'");
        }
    }

    public static Integer getIntegerField(HttpResponse response, String fieldName) {
        return getIntegerField(response, fieldName, null);
    }

    public static Integer getIntegerField(HttpResponse response, String fieldName, Integer defaultIfNull) {
        String value = response.getHeader(fieldName);
        if (value == null) {
            return defaultIfNull;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultIfNull;
        }
    }

    public static Integer getMandatoryIntegerField(HttpResponse response, String fieldName)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new MandatoryFieldException("Cannot convert '" + value +
                                              "' to Integer for field '" + fieldName + "'");
        }
    }

    public static Boolean getBooleanField(HttpResponse response, String fieldName) {
        return getBooleanField(response, fieldName, null);
    }

    public static Boolean getBooleanField(HttpResponse response, String fieldName, Boolean defaultIfNull) {
        String value = response.getHeader(fieldName);
        if (value == null) {
            return defaultIfNull;
        }

        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            return defaultIfNull;
        }
    }

    public static Boolean getMandatoryBooleanField(HttpResponse response, String fieldName)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            throw new MandatoryFieldException("Cannot convert '" + value +
                                              "' to Boolean for field '" + fieldName + "'");
        }
    }

    public static <E extends Enum> E getEnumField(HttpResponse response, String fieldName, E defaultIfNull,
                                                  Class<E> enumClass) {
        String value = response.getHeader(fieldName);
        if (value == null) {
            return defaultIfNull;
        }

        try {
            //noinspection RedundantCast
            return (E) Enum.valueOf(enumClass, value.toUpperCase());
        } catch (Exception e) {
            return defaultIfNull;
        }
    }

    public static <E extends Enum> E getMandatoryEnumField(HttpResponse response, String fieldName, Class<E> enumClass)
            throws MandatoryFieldException {
        String value = response.getHeader(fieldName);
        ensureFieldIsNotNull(value, fieldName);

        try {
            //noinspection RedundantCast
            return (E) Enum.valueOf(enumClass, value.toUpperCase());
        } catch (Exception e) {
            throw new MandatoryFieldException("Cannot convert '" + value +
                                              "' to " + enumClass.getSimpleName() +
                                              " for field '" + fieldName + "'");
        }
    }

    // private static helpers -----------------------------------------------------------------------------------------

    private static void ensureFieldIsNotNull(Object value, String fieldName) throws MandatoryFieldException {
        if (value == null) {
            throw new MandatoryFieldException("Field '" + fieldName + "' not found in response");
        }
    }
}
