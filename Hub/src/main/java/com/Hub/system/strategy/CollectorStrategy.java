package com.Hub.system.strategy;

import com.Hub.system.model.CollectorSource;

import java.util.Map;
import java.util.stream.Stream;

public interface CollectorStrategy {
    Stream<Map<String, String>> collect(CollectorSource source) throws Exception;
}
