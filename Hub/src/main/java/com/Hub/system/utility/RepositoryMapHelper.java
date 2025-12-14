package com.Hub.system.utility;

import com.Hub.account.repository.IAccountAttributeRepository;
import com.Hub.account.repository.IAccountRepository;
import com.Hub.identity.repository.IdentityRepository;
import com.Hub.resource.repository.IResourceRepository;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapHelper {

    private final IAccountRepository accountRepository;
    private final IResourceRepository resourceRepository;
    private final IdentityRepository identityRepository;
    private final SystemRepository systemRepository;
    private final IAccountAttributeRepository accountAttributeRepository;
    private final IMappingConfigModelRepository mappingConfigModelRepository;

    public RepositoryMapHelper (IAccountRepository accountRepository, IAccountAttributeRepository accountAttributeRepository,
                                IResourceRepository resourceRepository, IdentityRepository identityRepository, SystemRepository systemRepository,
                                IMappingConfigModelRepository mappingConfigModelRepository) {
        this.accountRepository = accountRepository;
        this.accountAttributeRepository = accountAttributeRepository;
        this.resourceRepository = resourceRepository;
        this.identityRepository = identityRepository;
        this.systemRepository = systemRepository;
        this.mappingConfigModelRepository = mappingConfigModelRepository;
    }

    public IAccountRepository getAccountRepository() {
        return accountRepository;
    }

    public IAccountAttributeRepository getAccountAttributeRepository() {
        return accountAttributeRepository;
    }

    public IMappingConfigModelRepository getMappingConfigModelRepository() {
        return mappingConfigModelRepository;
    }

    public IResourceRepository getResourceRepository() {
        return resourceRepository;
    }

    public IdentityRepository getIdentityRepository() {
        return identityRepository;
    }

    public SystemRepository getSystemRepository() {
        return systemRepository;
    }
}
