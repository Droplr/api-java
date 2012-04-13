package com.droplr.service.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Non-thread safe.
 *
 * @author <a href="http://biasedbit.com/>Bruno de Carvalho</a>
 */
public class QueryParams {

    // internal vars --------------------------------------------------------------------------------------------------

    private final Map<String, List<String>> parameters;
    private       int                       count;

    // constructors ---------------------------------------------------------------------------------------------------

    public QueryParams() {
        this.parameters = new LinkedHashMap<String, List<String>>();
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static QueryParams parseFromString(String query) {
        QueryParams params = new QueryParams();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            // discards anything other than key=value (I think this isn't fully RFC compatible...)
            if (pair.length != 2) {
                continue;
            }
            String key;
            String value;
            try {
                key = URLDecoder.decode(pair[0], "UTF-8");
                value = URLDecoder.decode(pair[1], "UTF-8");
                params.add(key, value);
            } catch (UnsupportedEncodingException e) {
                // This never happens...
            }
        }

        return params;
    }

    // public methods -------------------------------------------------------------------------------------------------

    public void add(String key, String value) {
        List<String> valuesForKey = this.parameters.get(key);
        if (valuesForKey == null) {
            valuesForKey = new ArrayList<String>();
            this.parameters.put(key, valuesForKey);
        }
        this.count++;
        valuesForKey.add(value);
    }

    public void removeAllWithKey(String key) {
        List<String> values = this.parameters.remove(key);
        if (values != null) {
            this.count -= values.size();
        }
    }

    public void remove(String key, String value) {
        List<String> valuesForKey = this.parameters.get(key);
        if (valuesForKey == null) {
            return;
        }

        if (valuesForKey.remove(value)) {
            this.count--;
            if (valuesForKey.isEmpty()) {
                this.parameters.remove(key);
            }
        }
    }

    public String get(String key) {
        List<String> valuesForKey = this.parameters.get(key);
        if (valuesForKey == null) {
            return null;
        }

        return (valuesForKey.isEmpty() ? null : valuesForKey.get(0));
    }

    public String get(String key, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }

        List<String> valuesForKey = this.parameters.get(key);
        if (valuesForKey == null) {
            return null;
        }

        return (index < valuesForKey.size() ? valuesForKey.get(index) : null);
    }

    public List<String> getAllForKey(String key) {
        return this.parameters.get(key);
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public String toEncodedQueryString() {
        if (this.count == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : this.parameters.entrySet()) {
            for (String value : entry.getValue()) {
                try {
                    builder.append('&').append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                           .append('=').append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // Never happens...
                }
            }
        }

        return builder.deleteCharAt(0).toString();
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        if (this.count == 0) {
            return "QueryParams{}";
        }

        StringBuilder builder = new StringBuilder("QueryParams{");
        for (Map.Entry<String, List<String>> entry : this.parameters.entrySet()) {
            for (String value : entry.getValue()) {
                builder.append('&').append(entry.getKey()).append('=').append(value);
            }
        }
        return builder.deleteCharAt(12).append("}").toString();
    }
}
