package program;

import client.tankerkoenig.TankerkoenigClient;
import config.*;
import database.ConnectionTester;
import database.DatabaseCreator;
import org.apache.commons.cli.*;
import program.cli.CliHandler;
import system.NotSupportedException;

import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Program {

    public static final String PROGRAM_NAME = "Tankstellen-Statistik-Collector";

    public static void main(String[] args) {
        CommandLine commandLine;

        CliHandler logger = new CliHandler();

        DatabaseConfig databaseConfig;

        try {
            commandLine = new OptionHandler().parseOptions(args);

            if (commandLine.hasOption("c"))
            {
                if (commandLine.hasOption("p"))
                {
                    new ConfigurationHandler().configure(logger);
                } else {
                    new ConfigurationHandler().purge(logger);
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
                GasstationConfig gasstationConfig = new GasstationConfig();

                databaseConfig = DatabaseConfig.getInstance();

                ApiConfig apiConfig = ApiConfig.getInstance();
            }
        } catch (IllegalArgumentException e) {
            System.exit(1);
        } catch (ConfigCreatedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            logger.printError(e.getMessage());
        } catch (ConfigNotValidException e) {
            System.exit(1);
        }
    }
}
