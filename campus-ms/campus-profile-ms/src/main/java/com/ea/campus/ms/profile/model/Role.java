package com.ea.campus.ms.profile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "roles")
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


}
