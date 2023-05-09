package com.hillel.demo.core.domain.model;

import com.hillel.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
@Setter
public class User implements UserDetails {
    private Long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer age;
    private List<BankAccount> bankAccounts;
    private Hospital hospitalEntity;
    private Set<GrantedAuthority> authoritySet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
