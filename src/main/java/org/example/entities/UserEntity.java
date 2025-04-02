package org.example.entities;

import java.util.UUID;

public class UserEntity extends Entities{

    private String nome;
    private String email;
    private String senha;

    public UserEntity(String nome, String email, String senha) {
        super(UUID.randomUUID());
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public UserEntity(UUID uuid, String nome, String email, String senha) {
        super(uuid);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
