package database;

import config.DatabaseConfig;
import program.cli.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
abstract class DatabaseHelper {

    DatabaseHelper(DatabaseConfig databaseConfig, Logger logger)
    {
        this.databaseConfig = databaseConfig;
        this.logger = logger;
    }

    Connection connection;

    DatabaseConfig databaseConfig;

    protected Logger logger;

    Connection createConnection() throws SQLException
    {
        String databaseString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=%s",
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getName(),
                databaseConfig.getTimezone()
        );

        return DriverManager.getConnection( databaseString, databaseConfig.getUser(), databaseConfig.getPassword() );
    }

    final String GASSTATION_TABLE = "gasstations";

    final String PRICE_TABLE = "prices";

    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
}
