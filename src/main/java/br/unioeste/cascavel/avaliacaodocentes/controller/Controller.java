/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.controller;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemAlreadyExistException;
import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import br.unioeste.cascavel.avaliacaodocentes.persistence.Persistence;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author RômuloManciola
 * @param <T>
 */
public class Controller<T> {

    private final Persistence<T> persistence;

    public Controller(Persistence<T> persistence) {
        if (persistence == null) {
            throw new RuntimeException("Persistencia nula");
        }
        this.persistence = persistence;
    }

    public void add(T t) throws ItemAlreadyExistException, IOException {
        try {
            get(t);
            throw new ItemAlreadyExistException("Item ja cadastrado");
        } catch (ItemNotFoundException ex) {
            persistence.create(t);
        }
    }

    public void remove(T t) throws ItemNotFoundException {
        persistence.delete(t);
    }

    public void update(T t) throws ItemNotFoundException {
        persistence.update(t);
    }

    public T get(T t) throws ItemNotFoundException {
        return persistence.retrieve(t);
    }

    public Collection<T> getItems() {
        return persistence.list();
    }

}
