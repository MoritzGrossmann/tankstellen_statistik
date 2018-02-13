package config;

import system.NotSupportedException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class ApiConfig extends Config {

    public static final String API_KEY_PROPERTY = "API-KEY";

    public ApiConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        apiKey = properties.getProperty(API_KEY_PROPERTY);
    }

    private String apiKey;

    public String getApiKey()
    {
        return this.apiKey;
    }
}
