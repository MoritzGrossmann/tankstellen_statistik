package com.grossmann.gasstation.collector.program.database;

import com.grossmann.gasstation.collector.config.GasstationConfig;
import com.grossmann.gasstation.collector.program.api.TankerkoenigApiClient;
import com.grossmann.gasstation.collector.program.cli.Logger;

import java.sql.SQLException;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface CacheFiller {

    GasstationRepository generateCache(GasstationRepository database, TankerkoenigApiClient apiClient, Logger logger, GasstationConfig gasstationConfig) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;
}
