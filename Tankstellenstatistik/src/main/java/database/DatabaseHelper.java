package database;

import config.DatabaseConfig;
import program.information.Logger;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public abstract class DatabaseHelper {

    public DatabaseHelper(DatabaseConfig databaseConfig, Logger logger)
    {
        this.databaseConfig = databaseConfig;
        this.logger = logger;
    }

    protected Connection connection;

    protected DatabaseConfig databaseConfig;

    protected Logger logger;

    protected Connection createConnection() throws SQLException
    {
        String databaseString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=%s",
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getName(),
                databaseConfig.getTimezone()
        );

        return DriverManager.getConnection( databaseString, databaseConfig.getUser(), databaseConfig.getPassword() );
    }

    protected final String GASSTATION_TABLE = "gasstations";

    protected final String PRICE_TABLE = "prices";

    protected final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
}
