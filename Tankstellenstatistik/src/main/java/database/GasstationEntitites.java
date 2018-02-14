package database;

import client.Gasstation;
import config.DatabaseConfig;
import program.information.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class GasstationEntitites extends DatabaseHelper {

    public GasstationEntitites(DatabaseConfig databaseConfig, Logger logger)
    {
        super(databaseConfig, logger);
    }

    public Gasstation push(Gasstation gasstation) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("INSERT INTO %s (uuid, name, street, postcode, city) values (?,?,?,?,?)", GASSTATION_TABLE);

        PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1,gasstation.getUuid());
        statement.setString(2,gasstation.getName());
        statement.setString(3,gasstation.getStreet());
        statement.setString(4,gasstation.getPostcode());
        statement.setString(5,gasstation.getCity());

        statement.execute();

        resultSet = statement.getGeneratedKeys();

        if (resultSet.next())
        {
            gasstation.setId(resultSet.getInt(1));
        }

        return gasstation;
    }

    public boolean exist(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = "SELECT * FROM " + GASSTATION_TABLE +  " WHERE uuid = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.execute();

        resultSet = statement.getResultSet();
        boolean res = resultSet.next();
        resultSet.close();
        return res;
    }

    public int getId(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = "SELECT * FROM " + GASSTATION_TABLE +  " WHERE uuid = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.execute();

        resultSet = statement.getResultSet();
        if (resultSet.next())
        {
            return resultSet.getInt("id");
        }

        return 0;
    }
}
