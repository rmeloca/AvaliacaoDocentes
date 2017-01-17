/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence.Exception;

/**
 *
 * @author romulo
 */
public class KeyNotFoundException extends Exception {

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
