package com.droplr.service.serialization;

import com.droplr.service.util.QueryParams;
import com.droplr.service.domain.DropListFilter;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class ParamsSerializationUtils {

    // constants ------------------------------------------------------------------------------------------------------

    public static final String LIST_SINCE  = "since";
    public static final String LIST_UNTIL  = "until";
    public static final String LIST_AMOUNT = "amount";
    public static final String LIST_OFFSET = "offset";
    public static final String LIST_TYPE   = "type";
    public static final String LIST_SORTBY = "sortBy";
    public static final String LIST_ORDER  = "order";
    public static final String LIST_SEARCH = "search";

    // public static methods ------------------------------------------------------------------------------------------

    public static QueryParams serialize(DropListFilter filter) {
        QueryParams params = new QueryParams();
        if (filter.isEmpty()) {
            return params;
        }

        safeAddToParams(params, LIST_SINCE, filter.getSince());
        safeAddToParams(params, LIST_UNTIL, filter.getUntil());
        safeAddToParams(params, LIST_AMOUNT, filter.getAmount());
        safeAddToParams(params, LIST_OFFSET, filter.getOffset());
        safeAddToParams(params, LIST_TYPE, filter.getType());
        safeAddToParams(params, LIST_SORTBY, filter.getSortBy());
        safeAddToParams(params, LIST_ORDER, filter.getOrder());
        safeAddToParams(params, LIST_SEARCH, filter.getSearch());

        return params;
    }

    private static void safeAddToParams(QueryParams params, String key, Object value) {
        if (value == null) {
            return;
        }

        params.add(key, value.toString().toLowerCase());
    }
}
