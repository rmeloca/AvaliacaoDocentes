/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;

/**
 *
 * @author RômuloManciola
 */
public class AdministradorPersistence extends GraphPersistence<Administrador> {

    public AdministradorPersistence() {
        super(Administrador.class);
    }

    @Override
    protected Administrador buildEntity(Fill primaryKey) {
        String login = primaryKey.getString("login");
        try {
            return new Administrador(login);
        } catch (Exception ex) {
            return null;
        }
    }

}
