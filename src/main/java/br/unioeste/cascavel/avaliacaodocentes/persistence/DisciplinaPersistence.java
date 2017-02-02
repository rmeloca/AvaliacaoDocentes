/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Disciplina;

/**
 *
 * @author romulo
 */
public class DisciplinaPersistence extends GraphPersistence<Disciplina> {

    public DisciplinaPersistence() {
        super(Disciplina.class);
    }

    @Override
    protected Disciplina buildEntity(Fill primaryKey) {
        String codigo = primaryKey.getString("codigo");
        Disciplina disciplina = new Disciplina(codigo);
        return disciplina;
    }

}
