/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.model.Disciplina;
import br.unioeste.cascavel.avaliacaodocentes.persistence.DisciplinaPersistence;

/**
 *
 * @author romulo
 */
public class DisciplinaController extends Controller<Disciplina> {

    public DisciplinaController() {
        super(new DisciplinaPersistence());
    }

}
