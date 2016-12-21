/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author romulo
 */
public class RDBMSConnection {

    private Connection con;
    private final String driver;
    private final String usuario;
    private final String senha;
    private final String endereco;

    public RDBMSConnection() {
        this.driver = "com.mysql.jdbc.Driver";
        this.usuario = "root";
        this.senha = "root";
        this.endereco = "jdbc:mysql://localhost";
    }

    public Connection getConnection() {
        try {
            Class.forName(this.driver);
            this.con = DriverManager.getConnection(this.endereco, this.usuario, this.senha);
        } catch (ClassNotFoundException erro) {
            System.out.println("Falha ao carregar o driver JDBC/ODBC. " + erro);
            return null;
        } catch (SQLException erro) {
            System.out.println("Falha na conexão, comando sql = " + erro);
            return null;
        } catch (Exception erro) {
            System.out.println("Não foi possível efetuar a conexão!\n" + erro);
            return null;
        }
        return this.con;
    }

}
