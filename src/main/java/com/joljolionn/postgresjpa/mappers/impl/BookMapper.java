package com.joljolionn.postgresjpa.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.joljolionn.postgresjpa.domain.dtos.BookDto;
import com.joljolionn.postgresjpa.domain.entities.BookEntity;
import com.joljolionn.postgresjpa.mappers.Mapper;

/**
 * BookMapper
 */
@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

	@Override
	public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
	}

	@Override
	public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
	}

    
}
