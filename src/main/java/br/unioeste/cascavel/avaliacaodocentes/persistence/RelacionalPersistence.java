/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class RelacionalPersistence<T> implements Persistence<T> {

    private final Connection connection;

    public RelacionalPersistence() {
        RDBMSConnection rdbms;
        rdbms = new RDBMSConnection();
        this.connection = rdbms.getConnection();
    }

    @Override
    public List<T> list() {
        List<T> lista = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            st.execute("USE AvaliacaoDocentes;");
            ResultSet rs = st.executeQuery("SELECT * FROM empregado;");
            for (rs.first(); !rs.isAfterLast(); rs.next()) {
                rs.getInt("btEntrar1");
            }
            rs.close();
            st.close();
        } catch (SQLException erro) {
            System.out.println(erro);
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(T t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
