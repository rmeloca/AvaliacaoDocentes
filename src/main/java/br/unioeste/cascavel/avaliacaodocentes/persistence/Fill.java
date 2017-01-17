/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Exception.KeyNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author romulo
 */
public class Fill implements Iterable<Entry<String, Object>> {

    private Map<String, Object> valores;

    public Fill() {
        this.valores = new HashMap<>();
    }

    public void addAttribute(String key, Object value) {
        this.valores.put(key, value);
    }

    public Object getAttribute(String key) {
        return valores.get(key);
    }

    protected boolean containsKey(String key) {
        return this.valores.containsKey(key);
    }

    public String getString(String key) {
        return (String) key;
    }

    public Fill getFill(String key) {
        return (Fill) getAttribute(key);
    }

    public Iterable<Fill> getCollection(String key) {
        return (Iterable<Fill>) getAttribute(key);
    }

    public Iterable<Entry<Fill, Fill>> getMap(String key) {
        Map<Fill, Fill> map = (Map<Fill, Fill>) getAttribute(key);
        Iterable iterable = new Iterable() {
            @Override
            public Iterator iterator() {
                return map.entrySet().iterator();
            }
        };
        return iterable;
    }

    @Override
    public Iterator<Entry<String, Object>> iterator() {
        return this.valores.entrySet().iterator();
    }


}
