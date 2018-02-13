package database;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public abstract class DatabaseHelper {

    protected Connection connection;

    protected String host;

    protected String port;

    protected String database;

    protected String server_timezone;

    protected String user;

    protected String password;

    protected Connection createConnection() throws SQLException
    {
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=%s",host, port, database, server_timezone), user, password );
    }

    protected final String GASSTATION_TABLE = "gasstations";

    protected final String PRICE_TABLE = "prices";

    protected final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
}
