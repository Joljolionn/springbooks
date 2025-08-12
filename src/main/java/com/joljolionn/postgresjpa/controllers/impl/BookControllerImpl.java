package com.joljolionn.postgresjpa.controllers.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joljolionn.postgresjpa.controllers.docs.BookControllerDocs;
import com.joljolionn.postgresjpa.domain.dtos.BookDto;
import com.joljolionn.postgresjpa.services.BookService;

/**
 * BookControllerImpl
 */
@RestController
public class BookControllerImpl implements BookControllerDocs {

    private BookService bookService;

    BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto book) {
        if (bookService.isExists(isbn)) {
            return new ResponseEntity<>(bookService.createUpdateBook(isbn, book), HttpStatus.OK);
        }
        return new ResponseEntity<>(bookService.createUpdateBook(isbn, book), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public ResponseEntity<Page<BookDto>> listBooks(Pageable pageable) {
        return new ResponseEntity<>(bookService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(
            @PathVariable("isbn") String isbn) {
        Optional<BookDto> result = bookService.findOne(isbn);
        return result.map(book -> {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.partialUpdate(isbn, bookDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(
            @PathVariable("isbn") String isbn) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.deleteBook(isbn), HttpStatus.OK);

    }

}
