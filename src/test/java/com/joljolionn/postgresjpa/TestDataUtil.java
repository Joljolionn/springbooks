package com.joljolionn.postgresjpa;

import com.joljolionn.postgresjpa.domain.dtos.AuthorDto;
import com.joljolionn.postgresjpa.domain.dtos.BookDto;
import com.joljolionn.postgresjpa.domain.entities.AuthorEntity;
import com.joljolionn.postgresjpa.domain.entities.BookEntity;

/**
 * TestDataUtil
 */
public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static AuthorEntity createTestAuthorEntity() {
        return new AuthorEntity.Builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityA() {
        return new AuthorEntity.Builder()
                .name("Rose Abigail")
                .age(8)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityB() {
        return new AuthorEntity.Builder()
                .name("Abise Rogail")
                .age(40)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityC() {
        return new AuthorEntity.Builder()
                .name("Abiro Gailse")
                .age(4)
                .build();
    }

    public static AuthorDto createTestAuthorDto() {
        return new AuthorDto.Builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return new AuthorDto.Builder()
                .name("Rose Abigail")
                .age(8)
                .build();
    }

    public static AuthorDto createTestAuthorDtoB() {
        return new AuthorDto.Builder()
                .name("Abise Rogail")
                .age(40)
                .build();
    }

    public static AuthorDto createTestAuthorDtoC() {
        return new AuthorDto.Builder()
                .name("Abiro Gailse")
                .age(4)
                .build();
    }

    public static BookEntity createTestBookEntity(final AuthorEntity author) {
        return new BookEntity.Builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .author(author)
                .build();

    }

    public static BookEntity createTestBookEntityA(final AuthorEntity author) {
        return new BookEntity.Builder()
                .isbn("879-1-5432-9786-9")
                .title("The Attic in the Shadow")
                .author(author)
                .build();

    }

    public static BookEntity createTestBookEntityB(final AuthorEntity author) {
        return new BookEntity.Builder()
                .isbn("978-2345-1-6789-0")
                .title("The in Shadow the Attic")
                .author(author)
                .build();

    }

    public static BookEntity createTestBookEntityC(final AuthorEntity author) {
        return new BookEntity.Builder()
                .isbn("978-1-0-2345-6789")
                .title("The shadow Attic in the")
                .author(author)
                .build();

    }

    public static BookDto createTestBookDto(final AuthorDto author) {
        return new BookDto.Builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .author(author)
                .build();

    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return new BookDto.Builder()
                .isbn("879-1-5432-9786-9")
                .title("The Attic in the Shadow")
                .author(author)
                .build();

    }

    public static BookDto createTestBookDtoB(final AuthorDto author) {
        return new BookDto.Builder()
                .isbn("978-2345-1-6789-0")
                .title("The in Shadow the Attic")
                .author(author)
                .build();

    }

    public static BookDto createTestBookDtoC(final AuthorDto author) {
        return new BookDto.Builder()
                .isbn("978-1-0-2345-6789")
                .title("The shadow Attic in the")
                .author(author)
                .build();

    }
}
