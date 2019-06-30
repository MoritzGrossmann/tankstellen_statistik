package com.grossmann.gasstation.collector.core;

import com.grossmann.gasstation.collector.system.NotSupportedException;

public class PostcodeFormatter<T> {

    private T value;

    public PostcodeFormatter(T value) {
        this.value = value;
    }

    public String get() throws NotSupportedException {
        if (value instanceof  Integer)
        {
            String val = String.valueOf(value);
            while(val.length() < 5)
            {
                val = "0".concat(val);
            }
            return val;
        } else if (value instanceof String) {
            String val = String.valueOf(value);
            while(val.length() < 5)
            {
                val = "0".concat(val);
            }
            return val;
        } else {
            throw new NotSupportedException("Parsing from Postcode actually only allowed from String or Integer");
        }
    }
}
