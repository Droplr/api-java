package com.droplr.service.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ContentType {

    // constants ------------------------------------------------------------------------------------------------------

    public static final String ANY_WILDCARD = "*";
    public static final String ANY_SUBTYPE  = ANY_WILDCARD;
    public static final String ANY_TYPE     = ANY_WILDCARD;
    public static final String TEXT_TYPE    = "text";
    public static final String IMAGE_TYPE   = "image";
    public static final String AUDIO_TYPE   = "audio";
    public static final String VIDEO_TYPE   = "video";

    public static final ContentType ANY = new ContentType(true, ANY_TYPE, ANY_SUBTYPE);

    // major types
    public static final ContentType IMAGE_ANY = new ContentType(true, IMAGE_TYPE, ANY_SUBTYPE);
    public static final ContentType AUDIO_ANY = new ContentType(true, AUDIO_TYPE, ANY_SUBTYPE);
    public static final ContentType VIDEO_ANY = new ContentType(true, VIDEO_TYPE, ANY_SUBTYPE);

    // well known types
    public static final ContentType APP_JSON        = new ContentType(true, "application", "json");
    public static final ContentType APP_XML         = new ContentType(true, "application", "xml");
    public static final ContentType TEXT_PLAIN      = new ContentType(true, TEXT_TYPE, "plain");
    public static final ContentType TEXT_MARKDOWN   = new ContentType(true, TEXT_TYPE, "markdown");
    public static final ContentType TEXT_TEXTILE    = new ContentType(true, TEXT_TYPE, "textile");
    public static final ContentType TEXT_CODE       = new ContentType(true, TEXT_TYPE, "code");
    public static final ContentType TEXT_HTML       = new ContentType(true, TEXT_TYPE, "html");
    public static final ContentType APP_OCTETSTREAM = new ContentType(true, "application", "octet-stream");
    public static final ContentType IMAGE_JPEG      = new ContentType(true, IMAGE_TYPE, "jpeg");
    public static final ContentType IMAGE_PNG       = new ContentType(true, IMAGE_TYPE, "png");
    public static final ContentType IMAGE_GIF       = new ContentType(true, IMAGE_TYPE, "gif");
    public static final ContentType IMAGE_BMP       = new ContentType(true, IMAGE_TYPE, "bmp");

    // internal vars --------------------------------------------------------------------------------------------------

    private final boolean constant;
    private final String type;
    private final String subtype;
    private Map<String, String> parameters;

    // constructors ---------------------------------------------------------------------------------------------------

    private ContentType(boolean constant, String type, String subtype) {
        this.constant = constant;
        this.type = type;
        this.subtype = subtype;
    }

    public ContentType(String type, String subtype) {
        this.constant = false;
        this.type = type;
        this.subtype = subtype;
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static ContentType createFromString(String contentTypeString) {
        String[] parts = contentTypeString.split(";");

        String[] typeAndSubtype = parts[0].trim().split("/");
        if (typeAndSubtype.length != 2) {
            return null;
        }

        ContentType contentType = new ContentType(typeAndSubtype[0].toLowerCase(), typeAndSubtype[1].toLowerCase());

        // Strip off the encodings and other useless clutter...
        if (parts.length > 1) {
            for (int i = 1; i < parts.length; i++) {
                String[] keyValue = parts[i].trim().split("=");
                switch (keyValue.length) {
                    case 1:
                        contentType.setParameter(keyValue[0]);
                        break;
                    case 2:
                        contentType.setParameter(keyValue[0], keyValue[1]);
                        break;
                    default:
                        // ignore, it's fucked up
                }
            }
        }

        return contentType;
    }

    public static boolean acceptable(Collection<ContentType> acceptedContentTypes, ContentType contentType) {
        for (ContentType acceptableType : acceptedContentTypes) {
            if (acceptableType.accepts(contentType)) {
                return true;
            }
        }

        return false;
    }

    // public methods -------------------------------------------------------------------------------------------------

    public ContentType copy() {
        ContentType that = new ContentType(this.type, this.subtype);
        if (this.parameters != null) {
            for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
                that.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return that;
    }

    public boolean accepts(ContentType that) {
        return (this.type.equals(ANY_WILDCARD) || this.type.equals(that.type)) &&
               (this.subtype.equals(ANY_WILDCARD) || this.subtype.equals(that.subtype));
    }

    public boolean isParameterSet(String key) {
        return (this.parameters != null) && this.parameters.containsKey(key);
    }

    public void setParameter(String key) {
        if (this.constant) {
            return;
        }

        this.setParameter(key, null);
    }

    public void setParameter(String key, String value) {
        if (this.constant) {
            return;
        }

        if (this.parameters == null) {
            this.parameters = new LinkedHashMap<String, String>();
        }

        this.parameters.put(key, value);
    }

    public void deleteParameter(String key) {
        if (this.constant) {
            return;
        }

        if (this.parameters == null) {
            return;
        }

        this.parameters.remove(key);
    }

    public String getParameter(String key) {
        if (this.parameters == null) {
            return null;
        }

        return this.parameters.get(key);
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public Set<Map.Entry<String, String>> getParameters() {
        if (this.parameters == null) {
            return null;
        }

        return this.parameters.entrySet();
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentType)) {
            return false;
        }

        ContentType that = (ContentType) o;
        return this.subtype.equals(that.subtype) && this.type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = this.type.hashCode();
        result = 31 * result + this.subtype.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(this.type).append('/').append(this.subtype);

        if ((this.parameters != null) && !this.parameters.isEmpty()) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.append("; ").append(entry.getKey());
                if (entry.getValue() != null) {
                    builder.append('=').append(entry.getValue());
                }
            }
        }

        return builder.toString();
    }
}
