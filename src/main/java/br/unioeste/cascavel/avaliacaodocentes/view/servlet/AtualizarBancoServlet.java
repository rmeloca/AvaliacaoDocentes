/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.view.servlet;

import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author romulo
 */
@WebServlet(name = "AtualizarBanco", urlPatterns = {"/AtualizarBanco"})
public class AtualizarBancoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("arquivo");
        InputStream inputStream = part.getInputStream();
        HttpSession session = req.getSession();
        Administrador administrador = (Administrador) session.getAttribute("usuario");
        administrador.atualizarDados(inputStream);
        inputStream.close();
    }
}
