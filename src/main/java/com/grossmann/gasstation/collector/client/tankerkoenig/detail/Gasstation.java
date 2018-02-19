package com.grossmann.gasstation.collector.client.tankerkoenig.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mgrossmann on 14.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gasstation {

    private String name;

    private String street;

    private String houseNumber;

    private int postCode;

    private String place;

    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
