package com.grossmann.gasstation.collector.program.database;

import com.grossmann.gasstation.collector.model.Gasstation;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface GasstationRepository {

    Gasstation push(Gasstation gasstation) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    boolean exist(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    int getId(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    Gasstation get(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    Collection<Gasstation> getAll() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

}
