package com.joljolionn.postgresjpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joljolionn.postgresjpa.domain.dtos.BookDto;

/**
 * BookService
 */
public interface BookService {

    BookDto createUpdateBook(String isbn, BookDto bookDto);

    List<BookDto> findAll();

    Page<BookDto> findAll(Pageable pageable);

	Optional<BookDto> findOne(String isbn);

	boolean isExists(String isbn);

	BookDto deleteBook(String isbn);

	BookDto partialUpdate(String isbn, BookDto bookDto);

}
