/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author romulo
 * @param <T>
 */
public abstract class DocumentPersistence<T extends Persistable> implements Persistence<T> {

    private final ODatabaseDocumentTx db;
    private final Class<T> classe;

    public DocumentPersistence(Class<T> classe) {
        this.classe = classe;
        this.db = new ODatabaseDocumentTx("remote:localhost/AvaliacaoDocentes").open("root", "root");
    }

    protected abstract T buildEntity(Map<String, Object> primaryKey);

    @Override
    public List<T> list() {
        List<T> lista = new ArrayList<>();
        ORecordIteratorClass<ODocument> browseClass = db.browseClass(classe.getSimpleName());
        Map<String, Object> values;
        for (ODocument document : browseClass) {
            values = new HashMap<>();
//            System.out.println(Arrays.toString(document.getDirtyFields()));
//            Object field = document.field("teste");
            Iterator<Map.Entry<String, Object>> iterator = document.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                values.put(next.getKey(), next.getValue());
            }
            T entity = buildEntity(values);
            entity.fillEntity(values);
            lista.add(entity);
        }
        return lista;
    }

    @Override
    public void create(T t) {
        Map<String, Object> values = t.getValues();
        ODocument doc = new ODocument(t.getClass().getSimpleName());
        doc.field("name", "Luke");
        doc.field("surname", "Skywalker");
        doc.field("city", new ODocument("City").field("name", "Rome").field("country", "Italy"));
        doc.save();
        db.close();
    }

    @Override
    public T retrieve(T t) throws ItemNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(T t) throws ItemNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(T t) throws ItemNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
