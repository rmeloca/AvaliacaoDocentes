/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public class ProfessorPersistence extends GraphPersistence<Professor> {

    public ProfessorPersistence() {
        super(Professor.class);
    }

    @Override
    protected Professor buildEntity(Map<String, Object> primaryKey) {
        String login = (String) primaryKey.get("login");
        try {
            return new Professor(login);
        } catch (Exception ex) {
            return null;
        }
    }

}
