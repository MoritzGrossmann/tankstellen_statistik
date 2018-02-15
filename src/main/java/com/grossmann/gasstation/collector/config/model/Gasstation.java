package com.grossmann.gasstation.collector.config.model;

import com.grossmann.gasstation.collector.program.api.TankerkoenigApiClient;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class Gasstation<T> implements ConfigModel<T> {

    private T value;

    public Gasstation(T value)
    {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean isValid() {
        if (value == null)
        {
            return false;
        } else {
            String val = (String)value;
            String[] arr = val.split("-");

            if (arr.length != TankerkoenigApiClient.ARRAY_LENGTH)
            {
                return false;
            }

            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].length() != TankerkoenigApiClient.PART_LENGTHS[i])
                {
                    return false;
                }
            }

            return true;
        }
    }
}
