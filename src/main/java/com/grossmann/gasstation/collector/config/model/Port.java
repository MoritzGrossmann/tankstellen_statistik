package com.grossmann.gasstation.collector.config.model;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class Port<T> implements ConfigModel<T> {

    private T value;

    public Port(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isValid() {
        //TODO validate Port
        if (value instanceof String)
        {
            try {
                int p = Integer.parseInt((String)value);
                return p >= 0 && p <= 65535;
            } catch (Exception e)
            {
                return false;
            }
        }

        if (value instanceof Integer)
        {
            Integer val = (Integer)value;
            return val >= 0 && val <= 65535;
        }
        return true;
    }
}
