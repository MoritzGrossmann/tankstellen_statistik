package program.module;

import client.tankerkoenig.price.Gasstation;
import model.GasstationPrices;
import program.api.TankerkoenigApiClient;
import program.cli.Logger;
import program.database.GasstationRepository;
import program.database.PriceRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public class Collector implements Runnable {

    private GasstationRepository gasstations;

    private PriceRepository prices;

    private Logger logger;

    private TankerkoenigApiClient tankerkoenigApiClient;

    public Collector(GasstationRepository gasstations, PriceRepository prices, Logger logger, TankerkoenigApiClient tankerkoenigApiClient)
    {
        this.gasstations = gasstations;
        this.prices = prices;
        this.logger = logger;
        this.tankerkoenigApiClient = tankerkoenigApiClient;
    }

    private boolean continueWorking = true;

    public void stop()
    {
        continueWorking = false;
    }

    @Override
    public void run() {
        collectData();
    }

    public void collectData() {
        while (continueWorking) {
            List<String> uuids = new ArrayList<>();

            try {
                Collection<client.Gasstation> list = gasstations.getAll();
                for (client.Gasstation gs : list) {
                    uuids.add(gs.getUuid());
                }

                Map<String, Gasstation> requestedPrices = tankerkoenigApiClient.getPrices(uuids);

                for (String key : requestedPrices.keySet()) {
                    int id = gasstations.getId(key);
                    Gasstation gasstationPrices = requestedPrices.get(key);
                    prices.pushPrices(new GasstationPrices(id, gasstationPrices.getE5(), gasstationPrices.getE10(), gasstationPrices.getDiesel()));
                }

            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
                logger.printError(e.getMessage());
            } finally {
                try {
                    logger.printInformation("Wait for 15 minutes");
                    Thread.sleep(900000);
                } catch (InterruptedException e) {
                    logger.printError(e.getMessage());
                }
            }
        }
    }


}
