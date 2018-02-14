package config.model;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public interface ConfigModel<T> {

    T getValue();

    boolean isValid();
}
