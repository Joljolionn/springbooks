package com.joljolionn.postgresjpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joljolionn.postgresjpa.domain.entities.AuthorEntity;

/**
 * AuthorRepository
 */

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    @Override
    List<AuthorEntity> findAll();

    List<AuthorEntity> ageLessThan(int age);

    @Query(value = "SELECT a FROM AuthorEntity a WHERE a.age > ?1", nativeQuery = false)
    List<AuthorEntity> findAgeGreaterThan(int age);

    @Query(value = "SELECT * FROM authors WHERE name = :name", nativeQuery = true)
    List<AuthorEntity> findRose(@Param("name") String name);
}
