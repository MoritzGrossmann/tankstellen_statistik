package client;

import program.cli.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public abstract class ApiClient {

    protected Logger logger;

    protected <T> T invokeGetRequest(String path, Map<String, String> queryParams, Class<T> entityClass) {
        T entity = null;

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(path);

        for (String key : queryParams.keySet())
        {
            webTarget = webTarget.queryParam(key, queryParams.get(key));
        }

        logger.printInformation(String.format("Requesting GET %s", webTarget.getUri()));

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        switch(response.getStatusInfo().getFamily())
        {
            case SUCCESSFUL:
                logger.printSuccess("Request was successful");
                break;
            case INFORMATIONAL:
                logger.printSuccess("Request was successful with Information");
                break;
            case CLIENT_ERROR:
                logger.printError("Client-Error while Request: ");
                logger.printInformation(response.getEntity().toString());
                break;
            case SERVER_ERROR:
                logger.printError("Server-Error while Request: ");
                logger.printInformation(response.getEntity().toString());
                break;
            case REDIRECTION:
                logger.printSuccess("Request was successful with Redirect");
                break;
            case OTHER:
                logger.printWarning("Unrecognized State of Request");
                break;
        }

        if (response.getStatus() == Response.Status.OK.getStatusCode())
        {
            entity = response.readEntity(entityClass);
        }

        client.close();
        return entity;
    }
}
