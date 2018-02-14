package config.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class Database<T> implements ConfigModel<T> {

    private T value;

    public Database(T value)
    {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isValid() {
        return true;
    }
}
