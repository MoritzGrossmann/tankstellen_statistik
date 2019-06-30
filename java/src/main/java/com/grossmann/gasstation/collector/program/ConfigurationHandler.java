package com.grossmann.gasstation.collector.program;

import com.grossmann.gasstation.collector.cli.CliHandler;
import com.grossmann.gasstation.collector.config.ApiConfig;
import com.grossmann.gasstation.collector.config.ConfigCreatedException;
import com.grossmann.gasstation.collector.config.DatabaseConfig;
import com.grossmann.gasstation.collector.config.GasstationConfig;
import com.grossmann.gasstation.collector.system.NotSupportedException;

import java.io.IOException;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class ConfigurationHandler {

    public void configure(CliHandler cliHandler) throws NotSupportedException, ConfigCreatedException, IOException {
        cliHandler.printInformation("configure program");
        cliHandler.printOption("Do you want to configure the Api-Key?");

        boolean choice = cliHandler.question();

        if (choice)
        {
            ApiConfig.getInstance().configurate(cliHandler);
        }

        cliHandler.printOption("Do you want to configure the Database?");
        choice = cliHandler.question();

        if (choice)
        {
            DatabaseConfig.getInstance().configurate(cliHandler);
        }

        cliHandler.printOption("Do you want to configure the gasstations?");
        choice = cliHandler.question();

        if (choice)
        {
            GasstationConfig.getInstance().configurate(cliHandler);
        }
    }

    public void purge(CliHandler cliHandler) throws NotSupportedException
    {
        throw new NotSupportedException("purge is not supported yet");
    }
}
