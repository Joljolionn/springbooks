package com.joljolionn.postgresjpa.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;
import com.joljolionn.postgresjpa.domain.entities.AuthorEntity;
import com.joljolionn.postgresjpa.mappers.Mapper;
import com.joljolionn.postgresjpa.repositories.AuthorRepository;
import com.joljolionn.postgresjpa.services.AuthorService;

/**
 * AuthorServiceImpl
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    Mapper<AuthorEntity, AuthorDto> authorMapper;

    AuthorServiceImpl(AuthorRepository authorRepository, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        authorEntity = authorRepository.save(authorEntity);
        return authorMapper.mapTo(authorEntity);
    }

    @Override
    public List<AuthorDto> listAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDto> findOne(Long id) {
        Optional<AuthorEntity> result = authorRepository.findById(id);
        return result.map(authorEntity -> authorMapper.mapTo(authorEntity));
    }

	@Override
	public boolean isExists(Long id) {
        return authorRepository.existsById(id);
	}

	@Override
	public AuthorDto fullUpdate(Long id, AuthorDto author) {
        author.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        authorEntity = authorRepository.save(authorEntity);
        return authorMapper.mapTo(authorEntity);
	}

	@Override
	public AuthorDto deleteAuthor(Long id) {
        AuthorDto authorDto = authorMapper.mapTo(authorRepository.findById(id).get());
        authorRepository.deleteById(id);
        return authorDto;
	}

	@Override
	public AuthorDto partialUpdate(Long id, AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorEntity savedAuthor = authorMapper.mapFrom(authorDto);
        Optional<AuthorEntity> results =  authorRepository.findById(id).map(author -> {
                Optional.ofNullable(savedAuthor.getName()).ifPresent(author::setName);
                Optional.ofNullable(savedAuthor.getAge()).ifPresent(author::setAge);
                return authorRepository.save(author);
        });
        return authorMapper.mapTo(results.get());
	}

}
