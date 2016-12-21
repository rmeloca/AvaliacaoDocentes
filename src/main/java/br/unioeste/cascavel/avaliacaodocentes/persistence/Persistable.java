/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import java.util.Map;

/**
 *
 * @author romulo
 */
public interface Persistable {

    public Map<String, Object> getPrimaryKey();

    public Map<String, Object> getValues();

    public void fillEntity(Map<String, Object> values);

}
