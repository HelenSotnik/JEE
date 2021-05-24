package com.hillel.demo.core.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "demo_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "email", unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private Integer age;

    @OneToMany(mappedBy = "userEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<BankAccountEntity> bankAccounts = new ArrayList<>();

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospitalEntity;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities = new HashSet<>();
}
