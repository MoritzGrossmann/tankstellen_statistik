package com.grossmann.gasstation.collector.database;

import com.grossmann.gasstation.collector.client.Gasstation;
import com.grossmann.gasstation.collector.config.DatabaseConfig;
import com.grossmann.gasstation.collector.model.GasstationPrices;
import com.grossmann.gasstation.collector.program.cli.Logger;
import com.grossmann.gasstation.collector.program.database.GasstationRepository;
import com.grossmann.gasstation.collector.program.database.PriceRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class GasstationEntitites extends DatabaseHelper implements GasstationRepository, PriceRepository {

    static final String ID_PROPERTY = "id";

    static final String UUID_PROPERTY = "uuid";

    static final String NAME_PROPERTY = "name";

    static final String STREET_PROPERTY = "street";

    static final String POSTCODE_PROPERTY = "postcode";

    static final String CITY_PROPERTY = "city";

    static final String GASSTAION_PROPERTY = "gasstation";

    static final String E5_PROPERTY = "e5";

    static final String E10_PROPERTY = "e10";

    static final String DIESEL_PROPERTY = "diesel";

    static final String TIMESTAMP_PROPERTY = "timestamp";

    public GasstationEntitites(DatabaseConfig databaseConfig, Logger logger)
    {
        super(databaseConfig, logger);
    }

    @Override
    public Gasstation push(Gasstation gasstation) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) values (?,?,?,?,?)", GASSTATION_TABLE, UUID_PROPERTY, NAME_PROPERTY, STREET_PROPERTY, POSTCODE_PROPERTY, CITY_PROPERTY);

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

    @Override
    public boolean exist(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",GASSTATION_TABLE, UUID_PROPERTY);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.execute();

        resultSet = statement.getResultSet();
        boolean res = resultSet.next();
        resultSet.close();
        return res;
    }

    @Override
    public int getId(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
    {
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",GASSTATION_TABLE, UUID_PROPERTY);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.execute();

        resultSet = statement.getResultSet();
        if (resultSet.next())
        {
            connection.close();
            return resultSet.getInt(ID_PROPERTY);
        }

        connection.close();
        resultSet.close();

        return 0;
    }

    @Override
    public Gasstation get(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Gasstation gasstation = new Gasstation();
        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",GASSTATION_TABLE, UUID_PROPERTY);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,key);
        statement.execute();

        resultSet = statement.getResultSet();
        if (resultSet.next())
        {
            gasstation.setId(resultSet.getInt(ID_PROPERTY));
            gasstation.setUuid(resultSet.getString(UUID_PROPERTY));
            gasstation.setName(resultSet.getString(NAME_PROPERTY));
            gasstation.setStreet(resultSet.getString(STREET_PROPERTY));
            gasstation.setPostcode(resultSet.getString(POSTCODE_PROPERTY));
            gasstation.setCity(resultSet.getString(CITY_PROPERTY));
        }

        resultSet.close();
        connection.close();
        return gasstation;
    }

    @Override
    public Collection<Gasstation> getAll() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        Collection<Gasstation> gasstations = new ArrayList<>();

        ResultSet resultSet = null;

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("SELECT * FROM %s", GASSTATION_TABLE);

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.execute();

        resultSet = statement.getResultSet();

        while(resultSet.next())
        {
            Gasstation gasstation = new Gasstation();
            gasstation.setId(resultSet.getInt(ID_PROPERTY));
            gasstation.setUuid(resultSet.getString(UUID_PROPERTY));
            gasstation.setName(resultSet.getString(NAME_PROPERTY));
            gasstation.setPostcode(resultSet.getString(POSTCODE_PROPERTY));
            gasstation.setStreet(resultSet.getString(STREET_PROPERTY));
            gasstation.setCity(resultSet.getString(CITY_PROPERTY));
            gasstations.add(gasstation);
        }

        resultSet.close();
        connection.close();
        return gasstations;
    }

    @Override
    public void pushPrices(GasstationPrices gasstaionPrices) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        Class.forName(JDBC_DRIVER).newInstance();

        connection = createConnection();

        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) values (?,?,?,?)", PRICE_TABLE, GASSTAION_PROPERTY, E5_PROPERTY, E10_PROPERTY, DIESEL_PROPERTY);

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, gasstaionPrices.getGasstation());
        statement.setDouble(2, gasstaionPrices.getE5());
        statement.setDouble(3, gasstaionPrices.getE10());
        statement.setDouble(4, gasstaionPrices.getDiesel());

        statement.execute();

        connection.close();
    }
}
