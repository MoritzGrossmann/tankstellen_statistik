package config;

import config.model.Gasstation;
import org.apache.commons.lang3.StringUtils;
import cli.CliHandler;
import system.NotSupportedException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GasstationConfig extends Config {

    public static final String GASSTATIONS_PROPERTY = "GASSTATIONS";

    private static GasstationConfig instance;

    public static GasstationConfig getInstance() throws NotSupportedException, ConfigCreatedException, IOException {
        if (instance == null) instance = new GasstationConfig();
        return instance;
    }

    private GasstationConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        gasstations = new ArrayList<String>();
        gasstations.addAll(Arrays.asList(properties.getProperty(GASSTATIONS_PROPERTY).split(",")));
    }

    private List<String> gasstations;

    public List<String> getGasstations() {
        return gasstations;
    }

    public void configurate(CliHandler cliHandler) throws NotSupportedException, IOException {

        Properties properties = getConfig();

        String gasstationstring = StringUtils.EMPTY;

        cliHandler.printInformation("configure gasstations");

        FileOutputStream fileOutputStream = new FileOutputStream(getConfigFile());

        cliHandler.printInformation("please type uuid of gasstation:");
        Gasstation<String> gasstation = new Gasstation<>(cliHandler.input());
        if (!gasstation.isValid())cliHandler.printWarning("No valid gasstation");

        gasstationstring += gasstation.getValue();

        boolean continueWithGasstation = false;

        cliHandler.printOption("Do you want add more gasstation?");
        continueWithGasstation = cliHandler.question();

        while (continueWithGasstation)
        {
            cliHandler.printInformation("please type uuid of gasstation:");
            gasstation = new Gasstation<>(cliHandler.input());
            if (!gasstation.isValid())cliHandler.printWarning("No valid gasstation");

            gasstationstring += ("," + gasstation.getValue());
            cliHandler.printOption("Do you want add more gasstation?");
            continueWithGasstation = cliHandler.question();
        }

        properties.setProperty(GASSTATIONS_PROPERTY, gasstationstring);

        properties.store(fileOutputStream, "");

        fileOutputStream.close();
    }
}
