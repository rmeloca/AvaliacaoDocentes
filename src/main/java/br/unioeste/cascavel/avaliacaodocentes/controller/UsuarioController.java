/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import br.unioeste.cascavel.avaliacaodocentes.persistence.UsuarioPersistence;

/**
 *
 * @author romulo
 */
public class UsuarioController extends Controller<Usuario> {

    public UsuarioController() {
        super(new UsuarioPersistence());
    }

    public Usuario login(String login, String senha) throws Exception {
        if (login == null || senha == null) {
            throw new Exception("Credenciais nulas");
        }
        if (login.isEmpty() || senha.isEmpty()) {
            throw new Exception("Credenciais vazias");
        }
        if (getItems().isEmpty()) {
            throw new Exception("Nenhum usuario encontrado");
        }

        Usuario resultado = null;

        AlunoController alunoController = new AlunoController();
        ProfessorController professorController = new ProfessorController();
        AdministradorController administradorController = new AdministradorController();

        try {
            resultado = alunoController.get(new Aluno(login));
        } catch (ItemNotFoundException ex) {
        }

        try {
            resultado = professorController.get(new Professor(login));
        } catch (ItemNotFoundException ex) {
        }

        try {
            resultado = administradorController.get(new Administrador(login));
        } catch (ItemNotFoundException ex) {
        }

        if (resultado == null) {
            throw new Exception("Credenciais invalidas");
        }
        if (!resultado.hasCredentials(login, senha)) {
            throw new Exception("Credenciais invalidas");
        }

        return resultado;
    }
}
