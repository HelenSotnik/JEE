package com.hillel.demo.core.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Collection<UserEntity> userEntity;

    @Override
    public String toString() {
        return "authority='" + authority;
    }
}
