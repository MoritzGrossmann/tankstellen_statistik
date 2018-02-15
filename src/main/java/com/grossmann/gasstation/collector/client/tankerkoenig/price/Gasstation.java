package com.grossmann.gasstation.collector.client.tankerkoenig.price;

import java.util.HashMap;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Gasstation {

    private String status;

    private double e5 = 0.0;

    private double e10 = 0.0;

    private double diesel = 0.0;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getE5() {
        return e5;
    }

    public void setE5(double e5) {
        this.e5 = e5;
    }

    public double getE10() {
        return e10;
    }

    public void setE10(double e10) {
        this.e10 = e10;
    }

    public double getDiesel() {
        return diesel;
    }

    public void setDiesel(double diesel) {
        this.diesel = diesel;
    }
}
