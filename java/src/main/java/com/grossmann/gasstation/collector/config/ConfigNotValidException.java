package com.grossmann.gasstation.collector.config;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class ConfigNotValidException extends Exception {

    public ConfigNotValidException()
    {
        super();
    }

    public ConfigNotValidException(String message)
    {
        super(message);
    }
}
