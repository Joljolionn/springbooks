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
import com.joljolionn.postgresjpa.domain.entities.BookEntity;

/**
 * BookDaoImplIntegrationTest
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryIntegrationTest {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository underTest) {
        this.underTest = underTest;
    }

    @BeforeEach
    void clearDatabase() {
        underTest.deleteAll();
    }

    // NOTAS MUITTÍSSIMOS IMPORTANTES AQUI
    // Existem 3 meios de fazer a criação de um objeto book no meio deste
    // teste, sendo elas:
    // - Book bookA = TestDataUtil.createTestBook(author)
    // Esse jeito é a criação manual do objeto book utilizando os
    // valores dados pelo TestDataUtil e o autor sem id
    // (afinal o ID é gerenciado pelo Hibernate de acordo em como
    // declaramos o model)
    //
    // - Book bookB = underTest.save(book)
    // Esse jeito utiliza o objeto retornado pelo método save() do
    // repositório e recebe os dados atualizados com o id gerenciado
    // pelo Hibernate (de acordo com o que definimos previamente)
    //
    // - Book bookC = underTest.findById(bookA.getIsbn()).get()
    // Esse jeito utiliza o objeto retornado pelo método findById()
    // do repositório e espelha os dados que estão inseridos no banco
    // de dados
    //
    // Todo cuidado deve ser tomado na comparação pra saber se o objeto foi inserido
    // corretamente pois a confusão entre o jeito de criação destes objetos pode
    // gerar erros que se replicam entre partes do código e que podem acabar dando
    // "falsos positivos" na área de testes. Por exemplo:
    //
    // Se eu tirar a lógica do ManyToOne do domain Book e testar o código
    // utilizando o bookB.isEqualTo(bookC), o teste vai passar como
    // funcional porém o id dos dois vai estar como null!
    // Porém, também deve se lembrar que o bookA.getAuthor() não possui o
    // id do autor atualizado com o que foi armazenado no banco de dados e
    // só vai possuir depois que o hibernate gerenciar tudo corretamente
    // (supondo que tenhamos configurado corretamente)
    //
    // Logo, a maneira mais segura de assegurar que os dados do banco foram
    // inseridos corretamente seria utilizando o repositório Autor para criar o
    // autor no banco de dados, o que vai salvar o id atualizado no objeto
    // passado para ser salvo afinal ele estará sendo gerenciado pelo hibernate
    // e só depois utilizar este objeto já gerenciado como parâmetro para a
    // criação do livro, assim garantindo que os dados locais estão todos
    // corretos, que foram todos inseridos corretamente (manualmente e sem
    // depender de falhas em outras partes do código ou "falsos verdadeiros"
    // por confusão na configuração do hibernate)
    //
    // Entretanto, para garantir que estamos fazendo total uso do hibernate e, ainda
    // assim, nos assegurarmos que tais erros não ocorram, foi adicionada a
    // verificação de isNotNull para ter certeza que o id foi corretamente atribuído
    // ao autor e, assim, a comparação entre os valores locais e inseridos no banco
    // ficam coerentes e seguras
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorEntity();

        BookEntity book = TestDataUtil.createTestBookEntity(author);
        book = underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getAuthor().getId()).isNotNull();
        Assertions.assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorEntity();

        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        bookA = underTest.save(bookA);

        BookEntity bookB = TestDataUtil.createTestBookEntityB(author);
        bookB = underTest.save(bookB);

        BookEntity bookC = TestDataUtil.createTestBookEntityC(author);
        bookC = underTest.save(bookC);

        List<BookEntity> results = underTest.findAll();

        Assertions.assertThat(results)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntity();

        BookEntity book = TestDataUtil.createTestBookEntity(author);
        underTest.save(book);

        book.setTitle("UPDATED");
        book = underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntity();
        BookEntity book = TestDataUtil.createTestBookEntity(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        Assertions.assertThat(result).isEmpty();
    }

}
