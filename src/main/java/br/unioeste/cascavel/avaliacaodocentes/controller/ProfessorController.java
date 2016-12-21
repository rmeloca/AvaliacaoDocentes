/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import br.unioeste.cascavel.avaliacaodocentes.persistence.ProfessorPersistence;

/**
 *
 * @author RômuloManciola
 */
public class ProfessorController extends Controller<Professor> {

    public ProfessorController() {
        super(new ProfessorPersistence());
    }

}
