package com.Hub.identity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "IdentityStatus")
public class IdentityStatusModel {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private String value;

}
