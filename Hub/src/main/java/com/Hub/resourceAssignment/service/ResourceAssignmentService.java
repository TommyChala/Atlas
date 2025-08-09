package com.Hub.resourceAssignment.service;

import com.Hub.account.model.AccountModel;
import com.Hub.account.repository.IAccountRepository;
import com.Hub.resource.model.ResourceModel;
import com.Hub.resource.repository.IResourceRepository;
import com.Hub.resourceAssignment.dto.ResourceAssignmentCreateDTO;
import com.Hub.resourceAssignment.mapper.ResourceAssignmentMapper;
import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import com.Hub.resourceAssignment.repository.ResourceAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceAssignmentService {

    private IResourceRepository iResourceRepository;
    private IAccountRepository iAccountRepository;
    private ResourceAssignmentRepository resourceAssignmentRepository;
    private ResourceAssignmentMapper resourceAssignmentMapper;

    public ResourceAssignmentService (IResourceRepository iResourceRepository, IAccountRepository iAccountRepository,
                                      ResourceAssignmentRepository resourceAssignmentRepository, ResourceAssignmentMapper resourceAssignmentMapper) {
        this.iResourceRepository = iResourceRepository;
        this.iAccountRepository = iAccountRepository;
        this.resourceAssignmentRepository = resourceAssignmentRepository;
        this.resourceAssignmentMapper = resourceAssignmentMapper;
    }

    public ResourceAssignmentModel createResourceAssignment (ResourceAssignmentCreateDTO createRequest) {
        ResourceAssignmentModel newResourceAssignment = resourceAssignmentMapper.CreateDTOToIdentity(createRequest);
        newResourceAssignment.setResource(iResourceRepository.findByResourceId(createRequest.getResourceId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find resource by id: " + createRequest.getResourceId())
                )
        );
        newResourceAssignment.setAccount(iAccountRepository.findByAccountId(createRequest.getAccountId())
                        .orElseThrow(() -> new EntityNotFoundException("Could not find account by id: " + createRequest.getAccountId())
                        )
        );
        return resourceAssignmentRepository.save(newResourceAssignment);
    }

}
