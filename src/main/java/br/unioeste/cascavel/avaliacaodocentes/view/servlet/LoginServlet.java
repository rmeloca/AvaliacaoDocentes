/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.view.servlet;

import br.unioeste.cascavel.avaliacaodocentes.controller.UsuarioController;
import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author romulo
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getParameter("login");
        String senha = (String) req.getParameter("senha");
        HttpSession session = req.getSession();
        try {
            UsuarioController usuarioController = new UsuarioController();
            Usuario usuario = usuarioController.login(login, senha);
            session.setAttribute("usuario", usuario);
            resp.sendRedirect("matriculas.jsp");
        } catch (Exception ex) {
            session.setAttribute("mensagem", ex.getMessage());
            resp.sendRedirect("");
        }

    }

}
