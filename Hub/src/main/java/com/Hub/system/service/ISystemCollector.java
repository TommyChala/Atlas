package com.Hub.system.service;

import com.Hub.system.model.SystemModel;

import java.util.Map;
import java.util.stream.Stream;

public interface ISystemCollector<T, S> {
    boolean testConnection (SystemModel system);

    Stream<T> collectData(S source) throws Exception;

}
