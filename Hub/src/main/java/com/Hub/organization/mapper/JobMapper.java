package com.Hub.organization.mapper;

import com.Hub.organization.dto.JobCreateDTO;
import com.Hub.organization.model.JobModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "externalId", source = "externalId")
    @Mapping(target = "level", source = "level")
    @Mapping(target = "validTo", source = "validTo")

    public JobModel toModel (JobCreateDTO jobCreateDTO);

}
