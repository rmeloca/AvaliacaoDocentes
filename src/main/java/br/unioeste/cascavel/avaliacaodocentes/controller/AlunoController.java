/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import br.unioeste.cascavel.avaliacaodocentes.persistence.AlunoPersistence;

/**
 *
 * @author RômuloManciola
 */
public class AlunoController extends Controller<Aluno> {

    public AlunoController() {
        super(new AlunoPersistence());
    }

}
