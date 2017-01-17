/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Fill;
import br.unioeste.cascavel.avaliacaodocentes.persistence.Persistable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public abstract class Usuario implements Persistable, Serializable {

    private final String login;
    private String senha;
    private String nome;
    private String email;
    private boolean loggedIn;

    public Usuario(String login, String senha, String nome, String email) throws Exception {
        if (login == null || senha == null) {
            throw new Exception("Credenciais nulas");
        }
        if (login.isEmpty() || senha.isEmpty()) {
            throw new Exception("Credenciais vazias");
        }
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.loggedIn = false;
    }

    public Usuario(String login) throws Exception {
        if (login == null) {
            throw new Exception("Credenciais nulas");
        }
        if (login.isEmpty()) {
            throw new Exception("Credenciais vazias");
        }
        this.login = login;
        this.senha = null;
        this.nome = null;
        this.email = null;
        this.loggedIn = false;
    }

    public String getLogin() {
        return login;
    }

    public void login() throws Exception {
        if (loggedIn) {
            this.loggedIn = false;
            throw new Exception("Usuário já efetuou login");
        }
        this.loggedIn = true;
    }

    public void logout() throws Exception {
        if (!loggedIn) {
            throw new Exception("Usuário não está logado");
        }
        this.loggedIn = false;
    }

    public boolean hasCredentials(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public String getNome() {
        return nome;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public Fill getPrimaryKey() {
        Fill fill = new Fill();
        fill.addAttribute("login", login);
        return fill;
    }

    @Override
    public Fill getValues() {
        Fill fill = new Fill();
        fill.addAttribute("login", login);
        fill.addAttribute("senha", senha);
        fill.addAttribute("nome", nome);
        fill.addAttribute("email", email);
        fill.addAttribute("loggedIn", loggedIn);
        return fill;
    }

    @Override
    public void fillEntity(Fill fill) {
        String nome = fill.getString("nome");
        String senha = fill.getString("senha");
        String email = fill.getString("email");
        Boolean loggedIn = (Boolean) fill.getAttribute("loggedIn");
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        if (loggedIn != null) {
            this.loggedIn = loggedIn;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) obj;
        return this.login.equals(usuario.login);
    }

}
