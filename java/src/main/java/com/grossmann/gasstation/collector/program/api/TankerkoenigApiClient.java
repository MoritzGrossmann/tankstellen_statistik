package com.grossmann.gasstation.collector.program.api;

import com.grossmann.gasstation.collector.client.tankerkoenig.detail.Gasstation;

import java.util.List;
import java.util.Map;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface TankerkoenigApiClient {

    /**
     * Länge welche das Array ener Tankstellenid bei String.split(-) haben muss
     */
    int ARRAY_LENGTH = 5;

    int[] PART_LENGTHS = {8,4,4,4,12};

    Gasstation getDetails(String uuid);

    Map<String, com.grossmann.gasstation.collector.client.tankerkoenig.price.Gasstation> getPrices(List<String> keys);

}
