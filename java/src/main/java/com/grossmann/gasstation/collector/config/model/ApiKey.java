package com.grossmann.gasstation.collector.config.model;

import com.grossmann.gasstation.collector.core.StringUtils;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class ApiKey<T> implements ConfigModel<T> {

    private T value;

    public ApiKey(T value)
    {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isValid() {
        // TODO validate Api-Key
        if (value instanceof String)
        {
            return !value.equals(StringUtils.EMPTY);
        }

        return value != null;
    }
}
