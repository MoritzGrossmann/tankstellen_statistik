package com.grossmann.gasstation.collector.cache;

import com.grossmann.gasstation.collector.core.PostcodeFormatter;
import com.grossmann.gasstation.collector.core.StringUtils;
import com.grossmann.gasstation.collector.model.Gasstation;
import com.grossmann.gasstation.collector.config.GasstationConfig;
import com.grossmann.gasstation.collector.program.api.TankerkoenigApiClient;
import com.grossmann.gasstation.collector.program.cli.Logger;
import com.grossmann.gasstation.collector.program.database.CacheFiller;
import com.grossmann.gasstation.collector.program.database.GasstationRepository;
import com.grossmann.gasstation.collector.system.NotSupportedException;

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
                com.grossmann.gasstation.collector.client.tankerkoenig.detail.Gasstation gs = apiClient.getDetails(uuid);
                Gasstation gasstation = new Gasstation();
                gasstation.setName(StringUtils.CleanUp(gs.getBrand()));
                gasstation.setUuid(uuid);
                gasstation.setStreet(StringUtils.CleanUp(StringUtils.CleanUp(gs.getStreet() + " " + gs.getHouseNumber())));
                gasstation.setCity(StringUtils.CleanUp(StringUtils.CleanUp(gs.getPlace())));
                try {
                    gasstation.setPostcode(new PostcodeFormatter<>(gs.getPostCode()).get());
                } catch (NotSupportedException e) {
                    gasstation.setPostcode("unknown");
                }

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
