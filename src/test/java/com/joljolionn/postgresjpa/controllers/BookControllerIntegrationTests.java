package com.joljolionn.postgresjpa.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joljolionn.postgresjpa.TestDataUtil;
import com.joljolionn.postgresjpa.domain.dtos.BookDto;
import com.joljolionn.postgresjpa.services.BookService;

import jakarta.transaction.Transactional;

/**
 * BookControllerIntegrationTests
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class BookControllerIntegrationTests {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnHttp201() throws JsonProcessingException, Exception {
        BookDto bookDto = new BookDto.Builder()
                .title("TESTE")
                .author(null)
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" +
                        TestDataUtil.createTestBookDto(null).getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(
                        MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsCorrectBook() throws JsonProcessingException, Exception {
        BookDto bookDto = new BookDto.Builder()
                .title("TESTE")
                .author(null)
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" +
                        TestDataUtil.createTestBookDto(null).getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn")
                                .value(TestDataUtil.createTestBookDto(null).getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title")
                                .value("TESTE"));
    }

    @Test
    public void testThatListBooksSuccessfullyReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListBooksSuccesfullyReturnsListOfBooks() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookService.createUpdateBook(
                TestDataUtil.createTestBookDtoA(null).getIsbn(), bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.content[0].isbn").value(
                                TestDataUtil.createTestBookDtoA(null).getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.content[0].title").value(
                                bookDto.getTitle()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.content[0].author").value(
                                bookDto.getAuthor()));
    }

    @Test
    public void testThatFindOneBookSuccesfullyReturnsHttpStatus200() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindOneBookSuccesfullyReturnsCorrectBook() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookDto.getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn").value(
                                bookDto.getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value(
                                bookDto.getTitle()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.author").value(
                                bookDto.getAuthor()));
    }

    @Test
    public void testThatFullUpdateBookReturnsHttpStatus200() throws JsonProcessingException, Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateBookReturnsCorrectBook() throws JsonProcessingException, Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDto.getAuthor()));
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsHttpSatus200() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Book\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateBookFailedReturnsHttpStatus404() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Book\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsCorrectBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Book\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                        .value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value("Test Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                        .value(bookDto.getAuthor()));

    }

    @Test
    public void testThatDeleteBookSuccessfullyReturnsHttpSatus200() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatDeleteBookFailedReturnsHttpStatus404() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteBookSuccessfullyReturnsCorrectBook() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto = bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                        .value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value(bookDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                        .value(bookDto.getAuthor()));
    }
}
