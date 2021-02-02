package com.hillel.demo.core.application.dto;

import com.hillel.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class UserDto {
    private Long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private List<BankAccountDto> bankAccounts;
    private HospitalDto hospitalEntity;
    private Collection<AuthorityDto> authorities;
}
