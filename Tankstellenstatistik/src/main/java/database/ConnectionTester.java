package database;

import config.DatabaseConfig;
import program.information.Logger;

import java.sql.Connection;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class ConnectionTester extends DatabaseHelper {

    public ConnectionTester(DatabaseConfig databaseConfig, Logger logger) {
        super(databaseConfig, logger);
    }

    public boolean testConnection()
    {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection connection = createConnection();
        } catch (Exception e)
        {
            logger.printError(e.getMessage());
            return false;
        }
        return true;
    }
}
