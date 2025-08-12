package com.joljolionn.postgresjpa.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.joljolionn.postgresjpa.domain.dtos.BookDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * BookControllerDocs
 */
@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros. Permite criar, listar, buscar, atualizar e deletar livros.")
public interface BookControllerDocs {

    @Operation(summary = "Cria/Atualiza completamente um livro", description = "Recebe os dados de um livro e cria um novo livro se o isbn fornecido na url não estiver relacionado com nenhum livro no sistema, caso contrário irá sobrescrever o livro com o isbn informado pelos dados recebidos.", responses = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    })
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @Parameter(description = "ISBN do livro a ser inserido/atualizado", example = "1") @PathVariable("isbn") String isbn,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados completos do livro para inserção/atualização", required = true, content = @Content(schema = @Schema(implementation = BookDto.class))) @RequestBody BookDto book);

    @Operation(summary = "Lista todos os livros", description = "Retorna uma lista paginada com todos os livros cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornados de forma paginada com sucesso", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(path = "/books")
    public ResponseEntity<Page<BookDto>> listBooks(Pageable pageable);

    @Operation(summary = "Busca um livro pelo ISBN", description = "Retorna um livro com base no ISBN fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(
            @Parameter(description = "ISBN do livro a ser buscado", example = "1") @PathVariable("isbn") String isbn);

    @Operation(summary = "Atualiza parcialmente um livro pelo isbn", description = "Atualiza um livro com base nos campos fornecidos", responses = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado e atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", description = "Livro não foi encontrado")
    })
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @Parameter(description = "ISBN do livro a ser atualizado", example = "1") @PathVariable("isbn") String isbn,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo os dados do livro a ser atualizado", required = true, content = @Content(schema = @Schema(implementation = BookDto.class))) @RequestBody BookDto bookDto);

    @Operation(summary = "Deleta um livro", description = "Deleta um livro pelo ISBN fornecido", responses = {
            @ApiResponse(responseCode = "200", description = "Livro deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", description = "Livro não foi encontrado")
    })
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(
            @Parameter(description = "ISBN do livro a ser deletado", example = "1") @PathVariable("isbn") String isbn);
}
