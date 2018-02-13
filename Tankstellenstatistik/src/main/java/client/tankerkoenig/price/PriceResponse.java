package client.tankerkoenig.price;

import client.tankerkoenig.TankerkoenigResponse;

import java.util.HashMap;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class PriceResponse extends TankerkoenigResponse{

    private HashMap<String, Gasstation> prices;

    public HashMap<String, Gasstation> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<String, Gasstation> prices) {
        this.prices = prices;
    }
}
