/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author romulo
 */
public class UsuarioPersistenceTest {

    public UsuarioPersistenceTest() {
    }

    @Test
    public void testSomeMethod() {
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        Usuario usuario;
        try {
            usuario = new Usuario("lpbaiser", "baiser", "", "") {
            };
        } catch (Exception ex) {
            System.err.println(ex);
            fail();
        };
    }

}
