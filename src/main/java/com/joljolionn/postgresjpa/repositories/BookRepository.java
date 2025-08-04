package com.joljolionn.postgresjpa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.joljolionn.postgresjpa.domain.entities.BookEntity;

/**
 * BookRepository
 */
@Repository
public interface BookRepository
        extends CrudRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String> {
    @Override
    List<BookEntity> findAll();
}
