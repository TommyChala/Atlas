package com.Hub.organization.mapper;

import com.Hub.organization.dto.JobCreateDTO;
import com.Hub.organization.model.JobModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T18:38:50+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public JobModel toModel(JobCreateDTO jobCreateDTO) {
        if ( jobCreateDTO == null ) {
            return null;
        }

        JobModel jobModel = new JobModel();

        jobModel.setName( jobCreateDTO.name() );
        jobModel.setExternalId( jobCreateDTO.externalId() );
        jobModel.setLevel( jobCreateDTO.level() );
        jobModel.setValidTo( jobCreateDTO.validTo() );

        return jobModel;
    }
}
