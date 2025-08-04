package com.joljolionn.postgresjpa.services;


import java.util.List;
import java.util.Optional;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;

/**
 * AuthorService
 */
public interface AuthorService {

    AuthorDto createAuthor(AuthorDto author);

	// Optional<AuthorDto> getAuthorById(Long id);

    List<AuthorDto> listAuthors();

	Optional<AuthorDto> findOne(Long id);

	boolean isExists(Long id);

	AuthorDto fullUpdate(Long id, AuthorDto author);

	AuthorDto deleteAuthor(Long id);

	AuthorDto partialUpdate(Long id, AuthorDto authorDto);
}
