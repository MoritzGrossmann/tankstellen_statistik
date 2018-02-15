package com.grossmann.gasstation.collector.program.database;

import com.grossmann.gasstation.collector.model.GasstationPrices;

import java.sql.SQLException;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface PriceRepository {

    void pushPrices(GasstationPrices gasstaionPrices) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;
}
