package com.grossmann.gasstation.collector;

import com.grossmann.gasstation.collector.cache.GasstationCacheFiller;
import com.grossmann.gasstation.collector.cli.CliHandler;
import com.grossmann.gasstation.collector.client.tankerkoenig.TankerkoenigClient;
import com.grossmann.gasstation.collector.config.*;
import com.grossmann.gasstation.collector.database.ConnectionTester;
import com.grossmann.gasstation.collector.database.DatabaseCreator;
import com.grossmann.gasstation.collector.database.GasstationEntitites;
import com.grossmann.gasstation.collector.program.ConfigurationHandler;
import com.grossmann.gasstation.collector.program.OptionHandler;
import com.grossmann.gasstation.collector.program.api.TankerkoenigApiClient;
import com.grossmann.gasstation.collector.program.database.DatabaseTester;
import com.grossmann.gasstation.collector.program.database.GasstationRepository;
import com.grossmann.gasstation.collector.program.database.PriceRepository;
import com.grossmann.gasstation.collector.program.module.Collector;
import com.grossmann.gasstation.collector.system.NotSupportedException;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String PROGRAM_NAME = "Tankstellen-Statistik-Collector";

    public static void main(String[] args) throws InterruptedException {
        CommandLine commandLine;

        CliHandler logger = new CliHandler();

        DatabaseConfig databaseConfig;

        GasstationConfig gasstationConfig;

        ApiConfig apiConfig;

        try {
            commandLine = new OptionHandler().parseOptions(args);

            if (commandLine.hasOption("c"))
            {
                if (commandLine.hasOption("p"))
                {
                    logger.printOption("Do you really want to purge the Configuration?");
                    if (logger.question())
                    {
                        new ConfigurationHandler().purge(logger);
                    }
                } else {
                    new ConfigurationHandler().configure(logger);
                }

            }

            if (commandLine.hasOption("d"))
            {
                databaseConfig = DatabaseConfig.getInstance();

                if (!new ConnectionTester(databaseConfig, logger).testConnection())
                {
                    throw new ConfigNotValidException("No Connection to Database");
                }

                if (commandLine.hasOption("p")) {
                    logger.printOption("do you really want to purge the Database?");
                    if (logger.question())
                    {
                        new DatabaseCreator(databaseConfig, logger).purge();
                    }
                } else {
                    new DatabaseCreator(databaseConfig, logger).create();
                }
            }

            if (commandLine.hasOption("r"))
            {
                gasstationConfig = GasstationConfig.getInstance();

                databaseConfig = DatabaseConfig.getInstance();

                apiConfig = ApiConfig.getInstance();

                DatabaseTester connectionTester = new ConnectionTester(databaseConfig, logger);

                if (connectionTester.testConnection())
                {
                    TankerkoenigApiClient client = new TankerkoenigClient(apiConfig.getApiKey().getValue(), logger);

                    GasstationRepository gasstationEntitites = new GasstationEntitites(databaseConfig, logger);

                    PriceRepository priceRepository = new GasstationEntitites(databaseConfig, logger);

                    GasstationRepository cache = new GasstationCacheFiller().generateCache(gasstationEntitites, client, logger, gasstationConfig);

                    logger.printSuccess("cache filled");

                    logger.printOption("Do you want to run the Collector?");
                    boolean collect = logger.question();
                    if (collect)
                    {
                        new Collector(cache, priceRepository,logger, client).collectData();
                    }
                }
            }
        } catch (IllegalArgumentException | ConfigNotValidException e) {
            System.exit(1);
        } catch (ConfigCreatedException e) {
            logger.printError(e.getMessage());
        } catch (IOException e) {
            logger.printError(e.getMessage());
        } catch (NotSupportedException e) {
            logger.printError(e.getMessage());
        } catch (InstantiationException e) {
            logger.printError(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.printError(e.getMessage());
        } catch (SQLException e) {
            logger.printError(e.getMessage() + e.getSQLState() + e.getLocalizedMessage() + e.getStackTrace());
        } catch (ClassNotFoundException e) {
            logger.printError(e.getMessage());
        } finally {
            Thread.sleep(20);
        }
    }
}
