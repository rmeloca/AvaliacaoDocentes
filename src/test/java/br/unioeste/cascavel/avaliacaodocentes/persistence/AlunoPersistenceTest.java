/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author romulo
 */
public class AlunoPersistenceTest {

    public AlunoPersistenceTest() {
    }

    @Test
    public void testSomeMethod() {

        DocumentPersistence<Aluno> doc = new DocumentPersistence<Aluno>(Aluno.class) {
            @Override
            protected Aluno buildEntity(Map<String, Object> primaryKey) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        try {
            doc.list();
        } catch (Exception ex) {
            fail();
        }

    }

}
