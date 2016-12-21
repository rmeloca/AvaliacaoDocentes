/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Persistable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public class Disciplina implements Persistable, Serializable {

    private final String codigo;
    private String nome;

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Disciplina(String codigo) {
        this.codigo = codigo;
        this.nome = null;
    }

    public String getCodigo() {
        return codigo;
    }

    protected void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", codigo);
        map.put("nome", nome);
        return map;
    }

    @Override
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", codigo);
        return map;
    }

    @Override
    public void fillEntity(Map<String, Object> values) {
        String nome = (String) values.get("nome");
        this.nome = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        return this.codigo.equalsIgnoreCase(((Disciplina) obj).codigo);
    }

}
