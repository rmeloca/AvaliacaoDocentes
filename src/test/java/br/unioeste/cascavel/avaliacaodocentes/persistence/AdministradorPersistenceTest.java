/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author romulo
 */
public class AdministradorPersistenceTest {

    public AdministradorPersistenceTest() {
    }

    @Test
    public void testSomeMethod() {
        AdministradorPersistence administradorPersistence = new AdministradorPersistence();
        try {
            administradorPersistence.create(new Administrador("admin", "admin", "Administrador", "rmeloca@gmail.com"));
        } catch (Exception ex) {
            Logger.getLogger(AdministradorPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

}
