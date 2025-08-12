package com.joljolionn.postgresjpa.controllers.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * AuthorControllerDocs
 */
@Tag(name = "Autores", description = "Endpoints para gerenciamento de autores. Permite criar, listar, buscar, atualizar e deletar autores.")
public interface AuthorControllerDocs {

    @Operation(summary = "Cria um novo autor", description = "Recebe os dados de um autor e o adiciona ao sistema. Retorna o autor criado.", responses = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto);

    @Operation(summary = "Busca um autor pelo ID", description = "Retorna um autor com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> findOne(@PathVariable("id") Long id);

    @Operation(summary = "Lista todos os autores", description = "Retorna uma lista com todos os autores cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors();

    @Operation(summary = "Atualiza completamente um autor", description = "Substitui todas as informações do autor pelo ID informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping(value = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto);

    @Operation(summary = "Atualiza parcialmente um autor", description = "Modifica apenas os campos informados no corpo da requisição.", responses = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado parcialmente com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto);

    @Operation(summary = "Deleta um autor", description = "Remove um autor do sistema pelo ID fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Autor deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(
            @PathVariable("id") Long id);
}
