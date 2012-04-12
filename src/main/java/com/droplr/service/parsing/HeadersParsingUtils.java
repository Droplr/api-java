package com.droplr.service.parsing;

import com.droplr.common.TextUtils;
import org.jboss.netty.handler.codec.http.HttpResponse;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class HeadersParsingUtils {

    // constructors ---------------------------------------------------------------------------------------------------

    private HeadersParsingUtils() {
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
            return (E) Enum.valueOf(enumClass, (String) value);
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
            return (E) Enum.valueOf(enumClass, (String) value);
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
