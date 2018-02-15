package config;

import config.model.ApiKey;
import config.model.ConfigModel;
import cli.CliHandler;
import system.NotSupportedException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class ApiConfig extends Config {

    public static final String API_KEY_PROPERTY = "API-KEY";

    private static ApiConfig instance;

    public static ApiConfig getInstance() throws NotSupportedException, ConfigCreatedException, IOException {
        if (instance == null) instance = new ApiConfig();
        return instance;
    }

    private ApiConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        apiKey = new ApiKey<String>(properties.getProperty(API_KEY_PROPERTY));
    }

    private ConfigModel<String> apiKey;

    public ConfigModel<String> getApiKey()
    {
        return this.apiKey;
    }

    @Override
    public void configurate(CliHandler cliHandler) throws NotSupportedException, IOException {

        Properties properties = getConfig();

        cliHandler.printInformation("configure Api-Access");
        cliHandler.printInformation("please type your API-Key:");

        ApiKey<String> ak = new ApiKey<String>(cliHandler.input());

        if (ak.isValid()) {
            properties.setProperty(API_KEY_PROPERTY, ak.getValue());
            FileOutputStream fileOutputStream = new FileOutputStream(getConfigFile());
            properties.store(fileOutputStream, "Example comment");
            cliHandler.printInformation(String.format("Api-Key is set to %s", ak.getValue()));
            fileOutputStream.close();
        } else {
            cliHandler.printWarning("No Api-key was defined");
        }
    }
}
