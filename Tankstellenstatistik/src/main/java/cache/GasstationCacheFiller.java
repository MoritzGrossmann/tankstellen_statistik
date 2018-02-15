package cache;

import client.Gasstation;
import config.GasstationConfig;
import program.api.TankerkoenigApiClient;
import program.cli.Logger;
import program.database.CacheFiller;
import program.database.GasstationRepository;

import java.sql.SQLException;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public class GasstationCacheFiller implements CacheFiller {
    @Override
    public GasstationRepository generateCache(GasstationRepository database, TankerkoenigApiClient apiClient, Logger logger, GasstationConfig gasstationConfig) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        for (String uuid : gasstationConfig.getGasstations())
        {
            if (!database.exist(uuid))
            {
                logger.printInformation(String.format("trying to collect Data for Gasstation %s", uuid));
                client.tankerkoenig.detail.Gasstation gs = apiClient.getDetails(uuid);
                Gasstation gasstation = new Gasstation();
                gasstation.setName(gs.getName());
                gasstation.setUuid(uuid);
                gasstation.setStreet(gs.getStreet() + " " + gs.getHouseNumber());
                gasstation.setCity(gs.getPlace());
                gasstation.setPostcode(String.valueOf(gs.getPostCode()));

                database.push(gasstation);
            }
        }

        GasstationRepository cache = new GasstationCache();

        for (Gasstation gasstation :  database.getAll())
        {
            cache.push(gasstation);
        }

        return cache;
    }
}
