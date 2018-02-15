package program.database;

import config.GasstationConfig;
import program.api.TankerkoenigApiClient;
import program.cli.Logger;

import java.sql.SQLException;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface CacheFiller {

    GasstationRepository generateCache(GasstationRepository database, TankerkoenigApiClient apiClient, Logger logger, GasstationConfig gasstationConfig) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;
}
