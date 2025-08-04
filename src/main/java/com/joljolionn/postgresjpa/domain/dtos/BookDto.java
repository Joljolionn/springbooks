package com.joljolionn.postgresjpa.domain.dtos;

import java.util.Objects;

/**
 * Book
 */
public class BookDto {

    private String isbn;

    private String title;

    private AuthorDto author;

    // ---------- Construtor privado ----------
    // Impede criação direta fora do Builder
	private BookDto(String isbn, String title, AuthorDto author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

    // ---------- Construtor Público vazio -----
    // Construtor público para permitir que o Hibernate crie as tabelas adequadamente
    public BookDto(){}

    // ---------- Getters e Setters ----------
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AuthorDto getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDto author) {
		this.author = author;
	}

    // ---------- Builder Pattern ----------
	public static class Builder {
		private String isbn;
		private String title;
		private AuthorDto author;

		// Define o campo `isbn` e retorna o builder
		public Builder isbn(String isbn) {
			this.isbn = isbn;
			return this;
		}

		// Define o campo `title` e retorna o builder
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		// Define o campo `author` e retorna o builder
		public Builder author(AuthorDto author) {
			this.author = author;
			return this;
		}

		// Cria o objeto `Book` final com os valores definidos
		public BookDto build() {
			return new BookDto(isbn, title, author);
		}
	}

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto)) return false;
        BookDto book = (BookDto) o;
        return Objects.equals(isbn, book.isbn)
            && Objects.equals(title, book.title)
            && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }
}

