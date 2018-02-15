package model;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public class GasstationPrices {

    private int gasstation;

    private double e5;

    private double e10;

    private double diesel;

    public GasstationPrices() {
    }

    public GasstationPrices(int gasstation, double e5, double e10, double diesel) {
        this.gasstation = gasstation;
        this.e5 = e5;
        this.e10 = e10;
        this.diesel = diesel;
    }

    public int getGasstation() {
        return gasstation;
    }

    public double getE5() {
        return e5;
    }

    public double getE10() {
        return e10;
    }

    public double getDiesel() {
        return diesel;
    }
}
