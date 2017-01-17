/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import br.unioeste.cascavel.avaliacaodocentes.model.Disciplina;
import br.unioeste.cascavel.avaliacaodocentes.model.Matricula;
import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author romulo
 */
public class GraphPersistenceTest {

    public GraphPersistenceTest() {
    }

    @Test
    public void testSomeMethod() {
        GraphPersistence<Aluno> graphPersistence = new GraphPersistence<Aluno>(Aluno.class) {
            @Override
            protected Aluno buildEntity(Fill primaryKey) {
                String login = (String) primaryKey.getAttribute("login");
                try {
                    Aluno aluno = new Aluno(login);
                    return aluno;
                } catch (Exception ex) {
                    return null;
                }
            }
        };
        try {
            Aluno aluno = new Aluno("rmeloca", "meloca", "Romulo Manciola Meloca", "rmeloca@gmail.com");
            aluno.matricular(new Matricula(2012, 1, aluno));
            aluno.matricular(new Matricula(2012, 2, aluno));
            aluno.matricular(new Matricula(2013, 1, aluno));
            aluno.matricular(new Matricula(2013, 2, aluno));
            aluno.matricular(new Matricula(2014, 1, aluno));
            aluno.matricular(new Matricula(2014, 2, aluno));
            graphPersistence.create(aluno);

            Aluno retrieve = graphPersistence.retrieve(aluno);
            System.out.println(retrieve.getNome());
            assertEquals(retrieve, aluno);
            aluno.getMatricula(2012, 1);
            retrieve.getMatricula(2012, 1);

            graphPersistence.delete(aluno);
        } catch (ItemNotFoundException ex) {
            System.err.println("item not found");
            fail();
        } catch (Exception ex) {
            System.err.println("problem while persiting");
            System.err.println(ex);
            fail();
        }
    }

    @Test
    public void testeMatch() {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/AvaliacaoDocentes", "root", "root");
        OrientGraph graph = factory.getTx();

        StringBuilder strQuery = new StringBuilder();
        strQuery.append("MATCH {class: Matricula, as: matricula, where: (ano = 2014 AND semestre = 2)}.out('edge_aluno') {as: aluno} RETURN matricula, aluno");

        OCommandSQL query = new OCommandSQL(strQuery.toString());
        OCommandRequest command = graph.command(query);
        Iterable<Vertex> resultado = command.execute();

        for (Vertex vertex : resultado) {
            Set<String> propertyKeys = vertex.getPropertyKeys();
            Vertex property = vertex.getProperty("aluno");
            Object property1 = property.getProperty("login");
            System.out.println("");
        }
    }

    @Test
    public void testeMatchMultiple() {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/AvaliacaoDocentes", "root", "root");
        OrientGraph graph = factory.getTx();

        StringBuilder strQuery = new StringBuilder();
        strQuery.append("MATCH {class: Matricula, as: matricula, where: (ano = 2014)} RETURN matricula\n");

        OCommandSQL query = new OCommandSQL(strQuery.toString());
        OCommandRequest command = graph.command(query);
        Iterable<Vertex> resultado = command.execute();

        for (Vertex vertex : resultado) {
            Set<String> propertyKeys = vertex.getPropertyKeys();
            Vertex vertex1 = vertex.getProperty("matricula");
            Object property1 = vertex1.getProperty("semestre");
            System.out.println("");
        }
    }

    @Test
    public void testeConditions() {
        GraphPersistence<Matricula> graphPersistence = new GraphPersistence<Matricula>(Matricula.class) {
            @Override
            protected Matricula buildEntity(Fill primaryKey) {
                try {
                    int ano = (int) primaryKey.getAttribute("ano");
                    int semestre = (int) primaryKey.getAttribute("semestre");
                    Fill alunovalues = (Fill) primaryKey.getAttribute("aluno");
                    String login = (String) alunovalues.getAttribute("login");
                    Aluno aluno = new Aluno(login);
                    aluno.fillEntity(alunovalues);
                    Matricula matricula = new Matricula(ano, semestre, aluno);
                    return matricula;
                } catch (Exception ex) {
                    return null;
                }
            }
        };
        try {
            Aluno aluno = new Aluno("alana");
            Matricula matricula = new Matricula(2014, 2, aluno);
            matricula.addDisciplina(new Disciplina("bcc32a"), new Professor("wiese"));
            graphPersistence.create(matricula);
            Matricula retrieve = graphPersistence.retrieve(matricula);
            graphPersistence.delete(matricula);
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(GraphPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
}
