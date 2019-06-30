package com.grossmann.gasstation.collector.client.tankerkoenig.price;

import com.grossmann.gasstation.collector.client.tankerkoenig.TankerkoenigResponse;

import java.util.HashMap;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class PriceResponse extends TankerkoenigResponse {

    private HashMap<String, Gasstation> prices;

    public HashMap<String, Gasstation> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<String, Gasstation> prices) {
        this.prices = prices;
    }
}
