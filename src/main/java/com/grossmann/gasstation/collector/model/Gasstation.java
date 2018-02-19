package com.grossmann.gasstation.collector.model;

import java.lang.reflect.Field;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Gasstation {

    private int id;

    private String uuid;

    private String name;

    private String street;

    private String postcode;

    private String city;

    private String brand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString()
    {
        String fields = "";

        for (Field field : this.getClass().getDeclaredFields())
        {
            fields = fields.concat(String.format("%s : %s; ",field.getName(),field.toString()));
        }

        return String.format("{%s}", fields);
    }
}
