package com.Hub.resource.service;

import com.Hub.resource.model.ResourceModel;
import com.Hub.resource.repository.IResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final IResourceRepository resourceRepository;

    public ResourceService (IResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceModel> findAll() {
        return resourceRepository.findAll();
    }

    public ResourceModel createNew(ResourceModel resourceModel) {
        return resourceRepository.save(resourceModel);
    }

}
