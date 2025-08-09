package com.Keystone.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Conditions {

    private List<Map<UUID, List<String>>> conditions;

    public List<Map<UUID, List<String>>> getConditions() {
        return conditions;
    }

    public void setConditions(List<Map<UUID, List<String>>> conditions) {
        this.conditions = conditions;
    }
}
