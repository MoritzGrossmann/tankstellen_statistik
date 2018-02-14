package database;

import config.DatabaseConfig;
import program.information.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class DatabaseCreator extends DatabaseHelper {

    public DatabaseCreator(DatabaseConfig databaseConfig, Logger logger)
    {
        super(databaseConfig,logger);
    }

    public void create()
    {
        logger.printInformation("try to create tables");
        try {
            createTables();
            addForeignKey();
        } catch (Exception e)
        {
            logger.printError(String.format("cannot creating tables. Exception: %s", databaseConfig.getName(), e.getMessage()));
        }
        logger.printInformation("tables created successful");
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

    public void dropTables()  {
        logger.printInformation("trying to drop Tables");
        try {
            dropTable(PRICE_TABLE);
            dropTable(GASSTATION_TABLE);
        } catch (Exception e)
        {
            logger.printError(String.format("cannot drop tables. Exception: %s", databaseConfig.getName(), e.getMessage()));
        }
        logger.printInformation("tables dropped successful");
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
            logger.printInformation(String.format("table %s dropped successful", table));
        } catch (SQLException e) {
            logger.printError(String.format("Cannot drop table %s", table));
            throw e;
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

    private void createDatabase() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
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

        String sql = "CREATE TABLE " + databaseConfig.getName() + "." + GASSTATION_TABLE + " ( " +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "uuid VARCHAR(50), " +
                "name VARCHAR(50), " +
                "street VARCHAR(50), " +
                "postcode VARCHAR(5), " +
                "city VARCHAR(50), " +
                "PRIMARY KEY (id)" +
                ")";

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create table %s", GASSTATION_TABLE));
            statement.execute(sql);
            logger.printInformation(String.format("table %s created successful", GASSTATION_TABLE));
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

        String sql = "CREATE TABLE " + databaseConfig.getName() + "." + PRICE_TABLE + " ( " +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "gasstation INT NOT NULL, " +
                "e5 DECIMAL(8,3) NOT NULL DEFAULT '0.0', " +
                "e1 DECIMAL(8,3) NOT NULL DEFAULT '0.0', " +
                "diesel DECIMAL(8,3) NOT NULL DEFAULT '0.0', " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP() , " +
                "PRIMARY KEY (id)" +
                ")";

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create table %s", PRICE_TABLE));
            statement.execute(sql);
            logger.printInformation(String.format("table %s created successful", PRICE_TABLE));
        } catch (SQLException e) {
            logger.printError(String.format("cannot create table %s", PRICE_TABLE));
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void addForeignKey()  throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        Class.forName(JDBC_DRIVER).newInstance();

        Statement statement = null;
        Connection connection = null;

        String sql = String.format("ALTER TABLE %s.%s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s (%s) ON DELETE RESTRICT ON UPDATE RESTRICT", databaseConfig.getName(), PRICE_TABLE, "gasstation" , "gasstation", "gasstations", "id");

        try {
            connection = createConnection();
            statement = connection.createStatement();
            logger.printInformation(String.format("trying to create foreign key  on %s", PRICE_TABLE));
            statement.execute(sql);
            logger.printInformation("foreign key created successful");
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
