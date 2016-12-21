/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author romulo
 * @param <T>
 */
public class EclipseLinkPersistence<T> implements Persistence<T> {

    private final EntityManager entityManager;
    private final Class<T> classe;

    public EclipseLinkPersistence(Class<T> classe) {
        this.classe = classe;
        this.entityManager = javax.persistence.Persistence.createEntityManagerFactory("MysqlUP").createEntityManager();
    }

    @Override
    public List<T> list() {
        return entityManager.createQuery("SELECT e FROM " + classe.getSimpleName() + " e").getResultList();
    }

    @Override
    public void create(T t) {
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
    }

    @Override
    public T retrieve(T t) throws ItemNotFoundException {
        entityManager.clear();
        return (T) entityManager.find(classe, t);
    }

    @Override
    public void update(T t) throws ItemNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(t);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public void delete(T t) throws ItemNotFoundException {
        if (t != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(t);
            entityManager.getTransaction().commit();
        }
    }

}
