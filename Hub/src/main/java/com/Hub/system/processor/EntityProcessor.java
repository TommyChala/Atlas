package com.Hub.system.processor;

import com.Hub.system.enums.EntityType;
import com.Hub.system.model.SystemModel;
import com.Hub.system.utility.CollectedEntity;

import java.io.File;
import java.io.IOException;

public interface EntityProcessor<T extends CollectedEntity> {

    EntityType getType();

    void process (File file, SystemModel system) throws IOException;
}
