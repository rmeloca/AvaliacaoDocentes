/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.view.servlet;

import br.unioeste.cascavel.avaliacaodocentes.controller.ProfessorController;
import br.unioeste.cascavel.avaliacaodocentes.model.Avaliacao;
import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "VisualizarAvaliacao", urlPatterns = {"/VisualizarAvaliacao"})
public class VisualizarAvaliacaoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer, Avaliacao> idHasAvaliacao = (Map<Integer, Avaliacao>) session.getAttribute("idHasAvaliacao");
        String strId = req.getParameter("avaliacao");
        Avaliacao avaliacao = idHasAvaliacao.get(Integer.valueOf(strId));
        session.setAttribute("avaliacao", avaliacao);
        Professor professor = (Professor) session.getAttribute("usuario");
        try {
            professor.lerAvaliacao(avaliacao);
            ProfessorController professorController = new ProfessorController();
            professorController.update(professor);
        } catch (Exception ex) {
            Logger.getLogger(VisualizarAvaliacaoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        resp.sendRedirect("avaliacao.jsp");
    }

}
