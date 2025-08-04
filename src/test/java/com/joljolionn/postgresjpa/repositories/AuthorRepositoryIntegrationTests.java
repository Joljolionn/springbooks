package com.joljolionn.postgresjpa.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joljolionn.postgresjpa.TestDataUtil;
import com.joljolionn.postgresjpa.domain.entities.AuthorEntity;

/**
 * AuthorDaoImplIntegrationTest
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorRepositoryIntegrationTests {
    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @BeforeEach
    void clearDatabase() {
        underTest.deleteAll();
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntity();
        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);

        AuthorEntity authorB = TestDataUtil.createTestAuthorEntityB();
        underTest.save(authorB);

        AuthorEntity authorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(authorC);

        List<AuthorEntity> results = underTest.findAll();
        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);

    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntity();
        underTest.save(author);

        author.setName("UPDATED");

        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntity();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<AuthorEntity> result = underTest.findById(author.getId());

        Assertions.assertThat(result).isEmpty();
    }

    // Teste de query personalizada criada totalmente pelo JPA sem escrever nenhuma
    // linha de SQL (disponível na interface)
    @Test
    public void testThatAgeLessThanWorks() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorEntityB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(authorC);

        List<AuthorEntity> result = underTest.ageLessThan(10);

        Assertions.assertThat(result)
                .hasSize(2)
                .containsExactly(authorA, authorC);

    }

    // Teste de query personalizada usando HQL
    @Test
    public void testThatFindsAuthorsWithAgeGratherThan() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorEntityB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(authorC);

        List<AuthorEntity> result = underTest.findAgeGreaterThan(10);

        Assertions.assertThat(result)
                    .hasSize(1)
                    .containsExactly(authorB);
    }

    // Teste de query personalizada usando SQL
    @Test
    public void testThatFindRoseWorks() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorEntityB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(authorC);

        List<AuthorEntity> result = underTest.findRose(authorA.getName());

        Assertions.assertThat(result)
                    .hasSize(1)
                    .containsExactly(authorA);
    }
    
}
