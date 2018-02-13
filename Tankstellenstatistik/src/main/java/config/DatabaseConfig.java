package config;

import system.NotSupportedException;

import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig extends Config {

    public static final String HOST_PROPERTY = "DATABASE_HOST";

    public static final String PORT_PROPERTY = "DATABSE_PORT";

    public static final String NAME_PROPERTY = "DATABASE_NAME";

    public static final String USER_PROPERTY = "DATABASE_USER";

    public static final String PASSWORD_PROPERTY = "DATABASE_PASSWORD";

    public static final String TIMEZONE_PROPERTY = "DATABASE_TIMEZONE";

    public DatabaseConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        host = properties.getProperty(HOST_PROPERTY);
        port = properties.getProperty(PORT_PROPERTY);
        name = properties.getProperty(NAME_PROPERTY);
        user = properties.getProperty(USER_PROPERTY);
        password = properties.getProperty(PASSWORD_PROPERTY);
        timezone = properties.getProperty(TIMEZONE_PROPERTY);
        }

    private String host;

    private String port;

    private String name;

    private String user;

    private String password;

    private String timezone;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getTimezone() {
        return timezone;
    }
}
