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
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        return map;
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        map.put("senha", senha);
        map.put("nome", nome);
        map.put("email", email);
        map.put("loggedIn", loggedIn);
        return map;
    }

    @Override
    public void fillEntity(Map<String, Object> values) {
        String nome = (String) values.get("nome");
        String senha = (String) values.get("senha");
        String email = (String) values.get("email");
        Boolean loggedIn = (Boolean) values.get("loggedIn");
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        if (loggedIn != null) {
            this.loggedIn = loggedIn;
        }
    }

    public Fill getValuesTeste() {
        Fill fill = new Fill();
        fill.addAttribute("nome", nome);
        return fill;
    }

    public void fillEntitityTeste(Fill fill) {
        String nome = (String) fill.getAttribute("nome");
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
