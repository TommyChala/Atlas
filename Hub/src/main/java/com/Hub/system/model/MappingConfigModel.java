package com.Hub.system.model;

import com.Hub.system.enums.MappingConfigDataType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "mapping_config"
)
public class MappingConfigModel {

    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "system_id", nullable = false)
    private SystemModel system;

    @Column(name = "source_attribute", nullable = false)
    private String sourceAttribute;

    @Column(name = "target_attribute", nullable = false, unique = true)
    private String targetAttribute;

    @Column(name = "datatype", nullable = false)
    private MappingConfigDataType dataType;

    @OneToMany(mappedBy = "mappingConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MappingExpressionModel> expressions = new ArrayList<>();

    public MappingConfigModel () {}

    public MappingConfigModel(UUID id, SystemModel system, String sourceAttribute, String targetAttribute, List<MappingExpressionModel> expressions) {
        this.id = id;
        this.system = system;
        this.sourceAttribute = sourceAttribute;
        this.targetAttribute = targetAttribute;
        this.expressions = expressions;
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

    public String getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(String targetAttribute) {
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
}
