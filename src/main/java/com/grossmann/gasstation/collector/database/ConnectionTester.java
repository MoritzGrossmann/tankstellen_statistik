package com.grossmann.gasstation.collector.database;

import com.grossmann.gasstation.collector.config.DatabaseConfig;
import com.grossmann.gasstation.collector.program.cli.Logger;
import com.grossmann.gasstation.collector.program.database.DatabaseTester;

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
