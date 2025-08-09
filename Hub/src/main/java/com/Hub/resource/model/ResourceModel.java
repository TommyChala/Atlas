package com.Hub.resource.model;

import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import com.Hub.system.model.SystemModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Resource")
public class ResourceModel {

    @Id
    @GeneratedValue
    @Column(name = "Uid", nullable = false, updatable = false, unique = true)
    private UUID resourceUid;
    @Column(name = "Id")
    private String resourceId;
    @Column(name = "ResourceName", nullable = false, unique = true)
    private String resourceName;
    @ManyToOne
    @JoinColumn(name = "SystemId", nullable = false)
    private SystemModel system;
    @OneToMany(mappedBy = "resource")
    private List<ResourceAssignmentModel> assignment;

    public ResourceModel () {}

    public ResourceModel(UUID resourceUid, String resourceId, String resourceName, SystemModel system) {
        this.resourceUid = resourceUid;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.system = system;
    }

    public UUID getResourceUid() {
        return resourceUid;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public SystemModel getSystem() {
        return system;
    }

    public void setSystem(SystemModel system) {
        this.system = system;
    }


}

