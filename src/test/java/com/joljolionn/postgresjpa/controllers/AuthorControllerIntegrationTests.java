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
import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;
import com.joljolionn.postgresjpa.services.AuthorService;

import jakarta.transaction.Transactional;

/**
 * AuthorControllerIntegrationTests
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccesfullyReturnsHttp201Created() throws JsonProcessingException, Exception {
        AuthorDto authorDto = new AuthorDto.Builder()
                .name("TEST")
                .age(19)
                .build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAuthorSuccesfullyReturnsCorrectAuthor() throws JsonProcessingException, Exception {
        AuthorDto authorDto = new AuthorDto.Builder()
                .name("TEST")
                .age(19)
                .build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id")
                                .isNumber())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name")
                                .value("TEST"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.age")
                                .value(19));
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorDto authorDtoA = TestDataUtil.createTestAuthorDtoA();
        authorDtoA = authorService.createAuthor(authorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(authorDtoA.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(authorDtoA.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(authorDtoA.getAge()));

    }

    @Test
    public void testThatFindAuthorSuccessfullyReturnsHttpStatus200() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindAuthorFailedReturnsHttpStatus404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFindAuthorSuccesfullyReturnsCorrectAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));

    }

    @Test
    public void testThatFullUpdateAuthorSuccesfullyReturnsHttpStatus200() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFullUpdateAuthorFailedReturnsHttpStatus404() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsCorrectAuthor() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);
        authorDto.setName("UPDATED");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age")
                        .value(authorDto.getAge()));
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttpSatus200() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"age\": 44}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateAuthorFailedReturnsHttpStatus404() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"age\": 44}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsCorrectAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + authorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"age\": 44}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age")
                        .value(44));

    }

    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsHttpSatus200() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + authorDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatDeleteAuthorFailedReturnsHttpStatus404() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsCorrectAuthor() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto = authorService.createAuthor(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age")
                        .value(authorDto.getAge()));
    }
}
