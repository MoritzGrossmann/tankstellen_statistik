package config;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class ConfigCreatedException extends Exception {

    public ConfigCreatedException(String message)
    {
        super(message);
    }

    public ConfigCreatedException() { super(); }
}
