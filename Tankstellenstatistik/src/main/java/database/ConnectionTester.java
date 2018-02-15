package database;

import config.DatabaseConfig;
import program.cli.Logger;
import program.database.DatabaseTester;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class ConnectionTester extends DatabaseHelper implements DatabaseTester {

    public ConnectionTester(DatabaseConfig databaseConfig, Logger logger) {
        super(databaseConfig, logger);
    }

    public boolean testConnection()
    {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            createConnection();
        } catch (Exception e)
        {
            logger.printError(e.getMessage());
            return false;
        }
        return true;
    }
}
