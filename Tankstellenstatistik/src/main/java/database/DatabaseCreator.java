package database;

import config.DatabaseConfig;
import program.cli.Logger;
import program.database.DatabaseConfigurator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class DatabaseCreator extends DatabaseHelper implements DatabaseConfigurator{

    public DatabaseCreator(DatabaseConfig databaseConfig, Logger logger)
    {
        super(databaseConfig,logger);
    }

    public void create() {
        logger.printInformation("try to create tables");
        try {
            createTables();
            addForeignKey();
        } catch (Exception e)
        {
            logger.printError(String.format("cannot creating tables. Exception: %s", databaseConfig.getName(), e.getMessage()));
        }
    }

    private void createTables() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!tableExist(GASSTATION_TABLE))
        {
            createGasstationTable();
        } else {
            logger.printInformation(String.format("Table %s already exist", GASSTATION_TABLE));
        }

        if (!tableExist(PRICE_TABLE))
        {
            createPriceTable();
        } else {
            logger.printInformation(String.format("Table %s already exist", PRICE_TABLE));
        }
    }

    public void purge()  {
        logger.printInformation("trying to drop Tables");
        try {
            dropTable(PRICE_TABLE);
            dropTable(GASSTATION_TABLE);
        } catch (Exception e)
        {
            logger.printError(String.format("cannot drop tables. Exception: %s", databaseConfig.getName(), e.getMessage()));
        }
    }

    private void dropTable(String table) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("DROP TABLE %s", table);

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to drop table %s", table));
            statement.execute(sql);
            logger.printSuccess(String.format("table %s dropped successful", table));
        } catch (SQLException e) {
            logger.printWarning(String.format("Cannot drop table %s %s", table, e.getMessage()));
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void dropDatabase()  throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("DROP DATABASE %s", databaseConfig.getName());

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to drop database %s", databaseConfig.getName()));
            statement.execute(sql);
            logger.printInformation(String.format("database %s dropped successful", databaseConfig.getName()));
        } catch (SQLException e) {
            logger.printError(String.format("Cannot drop database %s", databaseConfig.getName()));
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void createDatabase() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("CREATE DATABASE %s", databaseConfig.getName());

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create database %s", databaseConfig.getName()));
            statement.execute(sql);
            logger.printInformation(String.format("database %s created successful", databaseConfig.getName()));
        } catch (SQLException e) {
            logger.printError(String.format("cannot create database %s", databaseConfig.getName()));
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void createGasstationTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("CREATE TABLE %s.%s (%s INT NOT NULL AUTO_INCREMENT, %s VARCHAR(50),  %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(5),  %s VARCHAR(50), PRIMARY KEY (%s))",
                databaseConfig.getName(),
                GASSTATION_TABLE,
                GasstationEntitites.ID_PROPERTY,
                GasstationEntitites.UUID_PROPERTY,
                GasstationEntitites.NAME_PROPERTY,
                GasstationEntitites.STREET_PROPERTY,
                GasstationEntitites.POSTCODE_PROPERTY,
                GasstationEntitites.CITY_PROPERTY,
                GasstationEntitites.ID_PROPERTY
        );

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create table %s", GASSTATION_TABLE));
            statement.execute(sql);
            logger.printSuccess(String.format("table %s created successful", GASSTATION_TABLE));
        } catch (SQLException e) {
            logger.printError(String.format("cannot create table %s", GASSTATION_TABLE));
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void createPriceTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("CREATE TABLE %s.%s (%s INT NOT NULL AUTO_INCREMENT, %s INT NOT NULL,  %s DECIMAL(8,3) NOT NULL DEFAULT '0.0', %s DECIMAL(8,3) NOT NULL DEFAULT '0.0', %s DECIMAL(8,3) NOT NULL DEFAULT '0.0',  %s TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), PRIMARY KEY (%s))",
                databaseConfig.getName(),
                PRICE_TABLE,
                GasstationEntitites.ID_PROPERTY,
                GasstationEntitites.GASSTAION_PROPERTY,
                GasstationEntitites.E5_PROPERTY,
                GasstationEntitites.E10_PROPERTY,
                GasstationEntitites.DIESEL_PROPERTY,
                GasstationEntitites.TIMESTAMP_PROPERTY,
                GasstationEntitites.ID_PROPERTY
        );

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create table %s", PRICE_TABLE));
            statement.execute(sql);
            logger.printSuccess(String.format("table %s created successful", PRICE_TABLE));
        } catch (SQLException e) {
            logger.printError(String.format("cannot create table %s", PRICE_TABLE));
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void addForeignKey()  throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("ALTER TABLE %s.%s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s (%s) ON DELETE RESTRICT ON UPDATE RESTRICT", databaseConfig.getName(), PRICE_TABLE, "gasstation" , "gasstation", "gasstations", "id");

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create foreign key  on %s", PRICE_TABLE));
            statement.execute(sql);
            logger.printSuccess("foreign key created successful");
        } catch (SQLException e) {
            logger.printError("cannot create foreign key");
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private boolean tableExist(String tableName) throws SQLException {

        Connection connection = createConnection();
        boolean tExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        } finally {
            connection.close();
        }
        return tExists;
    }
}
