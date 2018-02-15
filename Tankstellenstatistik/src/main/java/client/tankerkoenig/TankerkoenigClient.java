package client.tankerkoenig;

import client.ApiClient;
import client.tankerkoenig.detail.DetailResponse;
import client.tankerkoenig.detail.Gasstation;
import client.tankerkoenig.price.PriceResponse;
import program.api.TankerkoenigApiClient;
import program.cli.Logger;

import java.util.*;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class TankerkoenigClient extends ApiClient implements TankerkoenigApiClient {

    private final String URL = "https://creativecommons.tankerkoenig.de/json";

    private final String PRICE_URL = URL + "/prices.php";

    private final String DETAIL_URL = URL + "/detail.php";

    private final String API_KEY;

    private final String API_KEY_QUERYPARAM = "apikey";

    private final String IDS_QUERYPARAM = "ids";

    private final String ID_QUERYPARAM = "id";

    public TankerkoenigClient(String apikey, Logger logger)
    {
        API_KEY = apikey;
        this.logger = logger;
    }

    public Map<String, client.tankerkoenig.price.Gasstation> getPrices(List<String> keys)
    {
        String keystring = keysToString(keys);
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put(IDS_QUERYPARAM, keystring);
        queryParams.put(API_KEY_QUERYPARAM, API_KEY);

        PriceResponse priceResponse = invokeGetRequest(PRICE_URL, queryParams, PriceResponse.class);

        return priceResponse.getPrices();
    }

    private String keysToString(List<String> keys)
    {
        String keystring = "";
        for (int i = 0; i <keys.size() -1; i++)
        {
            keystring = keystring.concat(keys.get(i));
            keystring =keystring.concat(",");
        }
        return keystring.concat(keys.get(keys.size() -1));
    }

    public Gasstation getDetails(String uuid)
    {
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put(ID_QUERYPARAM, uuid);
        queryParams.put(API_KEY_QUERYPARAM, API_KEY);

        DetailResponse detailResponse = invokeGetRequest(DETAIL_URL, queryParams, DetailResponse.class);

        return detailResponse.getStation();
    }
}
