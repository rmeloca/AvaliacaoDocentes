/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.controller.AdministradorController;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author romulo
 */
public class AdministradorTest {

    public AdministradorTest() {
    }

    @Test
    public void testSomeMethod() {
        InputStream inputStream = getClass().getResourceAsStream("/users.txt");
        AdministradorController administradorController = new AdministradorController();
        Administrador administrador;
        try {
            administrador = new Administrador("admin");
            administradorController.add(administrador);
            administrador = administradorController.get(administrador);
            administrador.atualizarDados(inputStream);
            inputStream.close();
        } catch (Exception ex) {
            fail();
        }
    }

}
