package com.joljolionn.postgresjpa.domain.dtos;

import java.util.Objects;

/**
 * Author
 */
public class AuthorDto {

    private Long id; 

    private String name; 

    private Integer age; 

    // ---------- Construtor privado ----------
    // Construtor privado para evitar criação direta e forçar uso do Builder
    private AuthorDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // ---------- Construtor Público vazio -----
    // Construtor público para permitir que o Hibernate crie as tabelas adequadamente
    public AuthorDto(){}

    // ---------- Getters e Setters ----------
    // Métodos públicos para acessar e modificar os atributos privados

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // ---------- Builder Pattern ----------
    // Classe estática usada para construir objetos Author de forma mais controlada
    public static class Builder {
        private Long id;
        private String name;
        private Integer age;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        // Define o campo `name` e retorna o builder
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        // Define o campo `age` e retorna o builder
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        // Cria o objeto `Author` final usando os valores definidos
        public AuthorDto build() {
            return new AuthorDto(id, name, age);
        }
    }

    @Override
    public boolean equals(Object o) {
        // Verifica se o objeto que está sendo comparado é o MESMO que esse atual.
        // Se sim, eles são obviamente iguais.
        if (this == o)
            return true;

        // Verifica se o objeto passado é nulo ou se ele não é da mesma classe que esse
        // objeto.
        // Se for diferente (por exemplo: Author vs Book), então não são iguais.
        if (!(o instanceof AuthorDto))
            return false;

        // Faz um cast do objeto genérico "o" para um Author, pra poder comparar os
        // campos.
        AuthorDto author = (AuthorDto) o;

        // Compara os campos individualmente usando Objects.equals()
        // Isso evita NullPointerException (ex: se name for null)
        // Só será "true" se TODOS os campos forem iguais (id, name, age)
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(age, author.age);
    }

    @Override
    public int hashCode() {
        // Gera um valor numérico baseado nos mesmos campos usados no equals()
        // Esse valor é usado por estruturas de dados tipo HashMap, HashSet, etc.
        // Se dois objetos forem "iguais" no equals(), eles DEVEM ter o mesmo hashCode.

        return Objects.hash(id, name, age);
    }

}
