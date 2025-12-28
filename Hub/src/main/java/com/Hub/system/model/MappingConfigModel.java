package com.Hub.system.model;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.enums.MappingConfigDataType;
import com.Hub.system.enums.MappingType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "mapping_config",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_mapping_config_system_target",
                        columnNames = {"system_id", "target_attribute"}
                )
        }
)
public class MappingConfigModel {

    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "system_id", nullable = false)
    private SystemModel system;

    @Column(name = "source_attribute")
    private String sourceAttribute;

    @ManyToOne
    @JoinColumn(name = "target_attribute", nullable = false)
    private AccountAttributeModel targetAttribute;

    @Enumerated(EnumType.STRING)
    @Column(name = "datatype", nullable = false)
    private MappingConfigDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "mapping_type", nullable = false)
    private MappingType mappingType;

    @OneToMany(
            mappedBy = "mappingConfig",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<MappingExpressionModel> expressions = new ArrayList<>();

    public MappingConfigModel () {}

    public MappingConfigModel(
            UUID id,
            SystemModel system,
            String sourceAttribute,
            AccountAttributeModel targetAttribute,
            MappingConfigDataType dataType, // ADD THIS
            List<MappingExpressionModel> expressions,
            MappingType mappingType
    ) {
        this.id = id;
        this.system = system;
        this.sourceAttribute = sourceAttribute;
        this.targetAttribute = targetAttribute;
        this.dataType = dataType; // ADD THIS
        this.expressions = expressions;
        this.mappingType = mappingType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SystemModel getSystem() {
        return system;
    }

    public void setSystem(SystemModel system) {
        this.system = system;
    }

    public String getSourceAttribute() {
        return sourceAttribute;
    }

    public void setSourceAttribute(String sourceAttribute) {
        this.sourceAttribute = sourceAttribute;
    }

    public AccountAttributeModel getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(AccountAttributeModel targetAttribute) {
        this.targetAttribute = targetAttribute;
    }

    public List<MappingExpressionModel> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<MappingExpressionModel> expressions) {
        this.expressions = expressions;
    }

    public MappingConfigDataType getDataType() {
        return dataType;
    }

    public void setDataType(MappingConfigDataType dataType) {
        this.dataType = dataType;
    }

    public MappingType getMappingType() {
        return mappingType;
    }

    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }
}
