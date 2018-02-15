package program;

import cache.GasstationCache;
import cache.GasstationCacheFiller;
import client.Gasstation;
import client.tankerkoenig.TankerkoenigClient;
import config.*;
import database.ConnectionTester;
import database.DatabaseCreator;
import database.GasstationEntitites;
import org.apache.commons.cli.*;
import cli.CliHandler;
import program.api.TankerkoenigApiClient;
import program.database.DatabaseTester;
import program.database.GasstationRepository;
import program.database.PriceRepository;
import program.module.Collector;
import system.NotSupportedException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Program {

    static final String PROGRAM_NAME = "Tankstellen-Statistik-Collector";

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
        } catch (ConfigCreatedException | IOException | NotSupportedException | InstantiationException | IllegalAccessException | SQLException e) {
            logger.printError(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.printError(e.getMessage());
        } finally {
            Thread.sleep(20);
        }
    }
}
