package com.hillel.demo.core.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "hospital")
public class HospitalEntity {

    @Id
    private Long id;

    @Column
    private String name;

    @OneToOne(mappedBy = "hospitalEntity", optional = false)
    private UserEntity userEntity;

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
