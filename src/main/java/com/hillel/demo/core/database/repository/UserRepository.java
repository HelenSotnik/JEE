package com.hillel.demo.core.database.repository;

import com.hillel.demo.core.database.entity.Gender;
import com.hillel.demo.core.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByOrderByFirstNameAsc(Pageable pageable);

    Page<UserEntity> findAllByOrderByFirstNameDesc(Pageable pageable);

    Page<UserEntity> findAllByGender(Gender gender, Pageable pageable);

    List<UserEntity> findAllByOrderByAgeAsc();

    List<UserEntity> findAllByOrderByAgeDesc();

    List<UserEntity> findAllByOrderByLastNameAsc();

    List<UserEntity> findAllByOrderByLastNameDesc();

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}
