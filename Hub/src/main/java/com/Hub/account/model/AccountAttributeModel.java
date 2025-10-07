package com.Hub.account.model;

import com.Hub.account.enums.DataType;
import jakarta.persistence.*;
import org.hibernate.annotations.Columns;

import java.util.UUID;

@Entity
@Table(name = "accountAdditionalAttributeModel")
public class AccountAdditionalAttributeModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "displayName")
    private String displayName;

    @Column(name = "dataType", nullable = false)
    private DataType dataType;


}
