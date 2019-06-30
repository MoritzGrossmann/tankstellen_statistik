package com.grossmann.gasstation.collector.client.tankerkoenig;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class TankerkoenigResponse {

    protected boolean ok;

    protected String license;

    protected String data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
