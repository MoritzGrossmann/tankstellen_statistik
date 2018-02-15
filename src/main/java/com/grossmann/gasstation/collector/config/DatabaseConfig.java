package com.grossmann.gasstation.collector.config;

import com.grossmann.gasstation.collector.cli.CliHandler;
import com.grossmann.gasstation.collector.config.model.*;
import com.grossmann.gasstation.collector.system.NotSupportedException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig extends Config {

    public static final String HOST_PROPERTY = "DATABASE_HOST";

    public static final String PORT_PROPERTY = "DATABSE_PORT";

    public static final String NAME_PROPERTY = "DATABASE_NAME";

    public static final String USER_PROPERTY = "DATABASE_USER";

    public static final String PASSWORD_PROPERTY = "DATABASE_PASSWORD";

    public static final String TIMEZONE_PROPERTY = "DATABASE_TIMEZONE";

    private static DatabaseConfig instance;

    private DatabaseConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = getConfig();
        host = properties.getProperty(HOST_PROPERTY);
        port = properties.getProperty(PORT_PROPERTY);
        name = properties.getProperty(NAME_PROPERTY);
        user = properties.getProperty(USER_PROPERTY);
        password = properties.getProperty(PASSWORD_PROPERTY);
        timezone = properties.getProperty(TIMEZONE_PROPERTY);
    }

    public static DatabaseConfig getInstance() throws NotSupportedException, ConfigCreatedException, IOException {
        if (instance == null)
            instance = new DatabaseConfig();
        return instance;
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

    public void configurate(CliHandler cliHandler) throws NotSupportedException, IOException {

        Properties properties = getConfig();

        cliHandler.printInformation("configure Database");

        FileOutputStream fileOutputStream = new FileOutputStream(getConfigFile());

        cliHandler.printInformation("please type Database-Host (without port):");
        Host<String> host = new Host<String>(cliHandler.input());
        if (!host.isValid()) cliHandler.printWarning(String.format("Host %s is no valid Host", host.getValue()));

        cliHandler.printInformation("please type Database-Port:");
        Port<String> port = new Port<String>(cliHandler.input());
        if (!port.isValid()) cliHandler.printWarning(String.format("Port %s is no valid Port", port.getValue()));

        cliHandler.printInformation("please type Database-Name:");
        Database<String> name = new Database<String>(cliHandler.input());
        if (!name.isValid())cliHandler.printWarning(String.format("Name %s is no valid Database-Name", name.getValue()));

        cliHandler.printInformation("please type Database-User:");
        User<String> user = new User<String>(cliHandler.input());
        if (!user.isValid())cliHandler.printWarning(String.format("User %s is no valid Database-User", user.getValue()));

        cliHandler.printInformation(String.format("please type Password of User %s:", user.getValue()));
        Password<String> password = new Password<String>(cliHandler.password());
        if (!password.isValid())cliHandler.printWarning("No valid password");

        cliHandler.printInformation("please type Database-Timezone:");
        Timezone<String> timezone = new Timezone<String>(cliHandler.input());
        if (!timezone.isValid())cliHandler.printWarning(String.format("Timezone %s is no valid Database-Timezone", timezone.getValue()));

        properties.setProperty(HOST_PROPERTY, host.getValue());
        properties.setProperty(PORT_PROPERTY, port.getValue());
        properties.setProperty(NAME_PROPERTY, name.getValue());
        properties.setProperty(USER_PROPERTY, user.getValue());
        properties.setProperty(PASSWORD_PROPERTY, password.getValue());
        properties.setProperty(TIMEZONE_PROPERTY, timezone.getValue());

        properties.store(fileOutputStream, "");

        fileOutputStream.close();
    }


}
