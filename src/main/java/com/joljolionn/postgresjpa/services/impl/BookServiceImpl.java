package com.joljolionn.postgresjpa.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joljolionn.postgresjpa.domain.dtos.BookDto;
import com.joljolionn.postgresjpa.domain.entities.BookEntity;
import com.joljolionn.postgresjpa.mappers.Mapper;
import com.joljolionn.postgresjpa.repositories.BookRepository;
import com.joljolionn.postgresjpa.services.BookService;

/**
 * BookServiceImpl
 */
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private Mapper<BookEntity, BookDto> bookMapper;


    BookServiceImpl(BookRepository bookRepository, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

	@Override
	public BookDto createUpdateBook(String isbn, BookDto bookDto) {
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity = bookRepository.save(bookEntity);
        return bookMapper.mapTo(bookEntity);
	}

	@Override
	public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::mapTo).collect(Collectors.toList());
	}

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::mapTo);
    }

	@Override
	public Optional<BookDto> findOne(String isbn) {
        Optional<BookEntity> result = bookRepository.findById(isbn);
        return result.map(book -> bookMapper.mapTo(book));
	}

	@Override
	public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
	}

	@Override
	public BookDto deleteBook(String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn).get();
        bookRepository.deleteById(isbn);
        return bookMapper.mapTo(bookEntity);
	}

	@Override
	public BookDto partialUpdate(String isbn, BookDto bookDto) {
        bookDto.setIsbn(isbn);
        BookEntity savedBook = bookMapper.mapFrom(bookDto);
        Optional<BookEntity> result = bookRepository.findById(isbn).map(book -> {
           Optional.ofNullable(savedBook.getTitle()).ifPresent(book::setTitle);
           Optional.ofNullable(savedBook.getAuthor()).ifPresent(book::setAuthor);
           return bookRepository.save(book);
        });
        return bookMapper.mapTo(result.get());
	}
}
