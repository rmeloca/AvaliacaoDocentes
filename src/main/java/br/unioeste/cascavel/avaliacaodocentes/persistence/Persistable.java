/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

/**
 *
 * @author romulo
 */
public interface Persistable {

    public Fill getPrimaryKey();

    public Fill getValues();

    public void fillEntity(Fill fill);

}
