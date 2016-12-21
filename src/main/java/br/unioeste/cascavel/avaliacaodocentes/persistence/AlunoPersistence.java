/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public class AlunoPersistence extends GraphPersistence<Aluno> {

    public AlunoPersistence() {
        super(Aluno.class);
    }

    @Override
    protected Aluno buildEntity(Map<String, Object> primaryKey) {
        String login = (String) primaryKey.get("login");
        try {
            Aluno aluno = new Aluno(login);
            return aluno;
        } catch (Exception ex) {
            return null;
        }
    }

}
