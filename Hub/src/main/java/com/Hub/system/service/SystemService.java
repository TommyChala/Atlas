package com.Hub.system.service;

import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    private final SystemRepository systemRepository;

    public SystemService (SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    public SystemModel addNew (SystemModel systemModel) {
       return systemRepository.save(systemModel);
    }

}
