package config;

import config.model.ConfigModel;

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
        // TODO validate Gasstation
        return true;
    }
}
