package config;

import system.NotSupportedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GasstationConfig extends Config {

    public static final String GASSTATIONS_PROPERTY = "GASSTATIONS";

    public GasstationConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        gasstations = new ArrayList<String>();
        gasstations.addAll(Arrays.asList(properties.getProperty(GASSTATIONS_PROPERTY).split(",")));
    }

    private List<String> gasstations;
}
