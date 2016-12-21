/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

/**
 *
 * @author romulo
 */
public class RDBMSConnectionTest {

    public RDBMSConnectionTest() {
    }

    @Test
    public void testSomeMethod() {
        RDBMSConnection conexao = new RDBMSConnection();
        Connection conn = conexao.getConnection();
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("USE AvaliacaoDocentes;");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS empregado(id integer(11) not null, nome varchar(255) not null, telefone varchar(20) not null);");
            st.executeUpdate("INSERT INTO empregado VALUES (1, 'juliano', '55-5555-5555');");
            st.executeUpdate("INSERT INTO empregado VALUES (2, 'romulo', '12-1234-1234');");
            ResultSet rs = st.executeQuery("SELECT * FROM empregado;");
            for (rs.first(); !rs.isAfterLast(); rs.next()) {
                System.out.println(rs.getInt(1) + " --> " + rs.getString(2) + " tel: " + rs.getString(3));
            }
            rs.close();
            st.close();
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }
}
