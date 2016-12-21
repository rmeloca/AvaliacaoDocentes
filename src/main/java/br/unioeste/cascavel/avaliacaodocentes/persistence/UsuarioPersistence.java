/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import java.util.Map;

/**
 *
 * @author romulo
 */
public class UsuarioPersistence extends GraphPersistence<Usuario> {

    public UsuarioPersistence() {
        super(Usuario.class);
    }

    @Override
    protected Usuario buildEntity(Map<String, Object> primaryKey) {
        Usuario usuario;
        String login = (String) primaryKey.get("login");
        try {
            usuario = new Usuario(login) {
            };
            return usuario;
        } catch (Exception ex) {
            return null;
        }
    }

}
