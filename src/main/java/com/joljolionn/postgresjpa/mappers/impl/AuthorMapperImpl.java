package com.joljolionn.postgresjpa.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;
import com.joljolionn.postgresjpa.domain.entities.AuthorEntity;
import com.joljolionn.postgresjpa.mappers.Mapper;

/**
 * AuthorMapper
 */
@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

	@Override
	public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
	}

	@Override
	public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
	}

    
}
