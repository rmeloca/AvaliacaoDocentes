/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import br.unioeste.cascavel.avaliacaodocentes.persistence.AdministradorPersistence;

/**
 *
 * @author RômuloManciola
 */
public class AdministradorController extends Controller<Administrador> {

    public AdministradorController() {
        super(new AdministradorPersistence());
    }

}
