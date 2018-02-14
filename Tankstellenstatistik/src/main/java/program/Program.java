package program;

import client.Gasstation;
import client.tankerkoenig.TankerkoenigClient;
import client.tankerkoenig.TankerkoenigResponse;
import client.tankerkoenig.detail.*;
import config.*;
import database.ConnectionTester;
import database.DatabaseCreator;
import database.GasstationEntitites;
import org.apache.commons.cli.*;
import program.cli.CliHandler;
import system.NotSupportedException;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Program {

    public static final String PROGRAM_NAME = "Tankstellen-Statistik-Collector";

    public static void main(String[] args) {
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
                    new ConfigurationHandler().purge(logger);
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
                    new DatabaseCreator(databaseConfig, logger).dropTables();
                } else {
                    new DatabaseCreator(databaseConfig, logger).create();
                }
            }

            if (commandLine.hasOption("r"))
            {
                gasstationConfig = GasstationConfig.getInstance();

                databaseConfig = DatabaseConfig.getInstance();

                apiConfig = ApiConfig.getInstance();

                TankerkoenigClient client = new TankerkoenigClient(apiConfig.getApiKey().getValue());

                GasstationEntitites gasstationEntitites = new GasstationEntitites(databaseConfig, logger);

                for (String uuid : gasstationConfig.getGasstations())
                {
                    if (!gasstationEntitites.exist(uuid))
                    {
                        logger.printInformation(String.format("trying to collect Data for Gasstation %s", uuid));
                        client.tankerkoenig.detail.Gasstation gs = client.getDetails(uuid).getStation();
                        Gasstation gasstation = new Gasstation();
                        gasstation.setName(gs.getName());
                        gasstation.setUuid(uuid);
                        gasstation.setStreet(gs.getStreet());
                        gasstation.setCity(gs.getPlace());
                        gasstation.setPostcode(String.valueOf(gs.getPostCode()));

                        gasstationEntitites.push(gasstation);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.exit(1);
        } catch (ConfigCreatedException e) {
            logger.printError(e.getMessage());
        } catch (IOException e) {
            logger.printError(e.getMessage());
        } catch (NotSupportedException e) {
            logger.printError(e.getMessage());
        } catch (ConfigNotValidException e) {
            System.exit(1);
        } catch (IllegalAccessException e) {
            logger.printError(e.getMessage());
        } catch (InstantiationException e) {
            logger.printError(e.getMessage());
        } catch (SQLException e) {
            logger.printError(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.printError(e.getMessage());
        }
    }
}
