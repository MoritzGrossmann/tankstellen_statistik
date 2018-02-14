package client.tankerkoenig.detail;

import client.tankerkoenig.TankerkoenigResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mgrossmann on 14.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailResponse extends TankerkoenigResponse {

    private String status;

    private Gasstation station;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Gasstation getStation() {
        return station;
    }

    public void setStation(Gasstation station) {
        this.station = station;
    }
}
