package com.grossmann.gasstation.collector.config.model;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class User<T> implements ConfigModel<T> {

    private T value;

    public User(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isValid() {
        //TODO validate Host

        return true;
    }
}
