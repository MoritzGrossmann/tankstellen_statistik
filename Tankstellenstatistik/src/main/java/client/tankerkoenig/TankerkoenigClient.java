package client.tankerkoenig;

import client.tankerkoenig.price.PriceResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class TankerkoenigClient {

    private final String URL = "https://creativecommons.tankerkoenig.de/json";

    private final String PRICE_URL = URL + "/prices.php";

    private final String API_KEY;

    private final String API_KEY_QUERYPARAM = "apikey";

    private final String IDS_QUERYPARAM = "ids";

    public TankerkoenigClient(String apikey)
    {
        API_KEY = apikey;
    }

    private HashMap<String, client.tankerkoenig.price.Gasstation> getPrices(List<String> keys)
    {
        String keystring = keysToString(keys);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(PRICE_URL)
                .queryParam(IDS_QUERYPARAM, keystring)
                .queryParam(API_KEY_QUERYPARAM, API_KEY);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        PriceResponse priceResponse = response.readEntity(PriceResponse.class);

        client.close();

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
        return keystring.concat(keys.get(keys.size()));
    }
}
