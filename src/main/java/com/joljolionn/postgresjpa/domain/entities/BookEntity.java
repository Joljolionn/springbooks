package com.joljolionn.postgresjpa.domain.entities;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Book
 */
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    // ---------- Construtor privado ----------
    // Impede criação direta fora do Builder
	private BookEntity(String isbn, String title, AuthorEntity author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}

    // ---------- Construtor Público vazio -----
    // Construtor público para permitir que o Hibernate crie as tabelas adequadamente
    public BookEntity(){}

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

	public AuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}

    // ---------- Builder Pattern ----------
	public static class Builder {
		private String isbn;
		private String title;
		private AuthorEntity author;

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
		public Builder author(AuthorEntity author) {
			this.author = author;
			return this;
		}

		// Cria o objeto `Book` final com os valores definidos
		public BookEntity build() {
			return new BookEntity(isbn, title, author);
		}
	}

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity)) return false;
        BookEntity book = (BookEntity) o;
        return Objects.equals(isbn, book.isbn)
            && Objects.equals(title, book.title)
            && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }
}

