package com.hillel.demo.core.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
