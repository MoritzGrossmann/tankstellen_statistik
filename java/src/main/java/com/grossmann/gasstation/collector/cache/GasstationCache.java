package com.grossmann.gasstation.collector.cache;

import com.grossmann.gasstation.collector.model.Gasstation;
import com.grossmann.gasstation.collector.program.database.GasstationRepository;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mgrossmann on 15.02.2018.
 */
public class GasstationCache implements GasstationRepository {

    private Map<String, Gasstation> gasstations;

    public GasstationCache()
    {
        gasstations = new TreeMap<>();
    }

    @Override
    public Gasstation push(Gasstation gasstation) {
        gasstations.put(gasstation.getUuid(), gasstation);
        return gasstation;
    }

    @Override
    public boolean exist(String key) {
        return gasstations.containsKey(key);
    }

    @Override
    public int getId(String key) {
        if (exist(key))
        {
            return gasstations.get(key).getId();
        } else
        {
            return -1;
        }
    }

    @Override
    public Gasstation get(String key) {
        return gasstations.get(key);
    }

    @Override
    public Collection<Gasstation> getAll() {
        return gasstations.values();
    }
}
