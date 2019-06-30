package com.grossmann.gasstation.collector.core;

public class StringUtils {

    public static final String EMPTY = "";

    public static String CleanUp(String value){
        String[] parts = value.split(" ");
        String fin = "";
        for (String part : parts) {
            if (!fin.equals(EMPTY)) fin += " ";
            fin += part.substring(0,1).toUpperCase() + part.substring(1).toLowerCase();
        }
        return fin;
    }
}
