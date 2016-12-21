/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

/**
 *
 * @author RômuloManciola
 */
public class Administrador extends Usuario {

    public Administrador(String login, String senha, String nome, String email) throws Exception {
        super(login, senha, nome, email);
    }

    public Administrador(String login) throws Exception {
        super(login);
    }

    public void atualizarDados() {
        throw new UnsupportedOperationException();
    }

}
