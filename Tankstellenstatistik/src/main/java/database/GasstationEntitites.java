package database;

import client.Gasstation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class GasstationEntitites extends DatabaseHelper {

    public GasstationEntitites(String host, String port, String database, String server_timezone, String user, String password)
    {
        this.host = host;
        this.port = port;
        this.database = database;
        this.server_timezone = server_timezone;
        this.user= user;
        this.password = password;
    }

    public Gasstation push(Gasstation gasstation) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = "INSERT INTO ? (uuid, name, street, postcode, city) values (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1,GASSTATION_TABLE);
        statement.setString(2,gasstation.getUuid());
        statement.setString(3,gasstation.getName());
        statement.setString(4,gasstation.getStreet());
        statement.setString(5,gasstation.getPostcode());
        statement.setString(6,gasstation.getCity());

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

        String sql = "SELECT * FROM gasstation WHERE uuid = ?";

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

        String sql = "SELECT * FROM gasstation WHERE uuid = ?";

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
