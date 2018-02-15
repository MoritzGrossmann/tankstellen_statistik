package com.grossmann.gasstation.collector.program.database;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public interface DatabaseConfigurator {

    void create();

    void purge();
}
