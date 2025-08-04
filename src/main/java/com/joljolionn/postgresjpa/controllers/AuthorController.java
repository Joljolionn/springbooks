package com.joljolionn.postgresjpa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;
import com.joljolionn.postgresjpa.services.AuthorService;

/**
 * AuthorController
 */
@RestController
public class AuthorController {

    private AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> findOne(@PathVariable("id") Long id) {
        Optional<AuthorDto> result = authorService.findOne(id);
        return result.map(author -> {
            return new ResponseEntity<>(author, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors() {
        return new ResponseEntity<>(authorService.listAuthors(), HttpStatus.OK);
    }

    @PutMapping(value = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(authorService.fullUpdate(id, authorDto), HttpStatus.OK);
    }
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if(!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(authorService.partialUpdate(id, authorDto), HttpStatus.OK);
        }
    }
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable("id") Long id) {
        if(!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(authorService.deleteAuthor(id), HttpStatus.OK);
        }
    }
}
