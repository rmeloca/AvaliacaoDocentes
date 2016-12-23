/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.view.servlet;

import br.unioeste.cascavel.avaliacaodocentes.controller.AlunoController;
import br.unioeste.cascavel.avaliacaodocentes.controller.DisciplinaController;
import br.unioeste.cascavel.avaliacaodocentes.controller.ProfessorController;
import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import br.unioeste.cascavel.avaliacaodocentes.model.Avaliacao;
import br.unioeste.cascavel.avaliacaodocentes.model.Criterio;
import br.unioeste.cascavel.avaliacaodocentes.model.Disciplina;
import br.unioeste.cascavel.avaliacaodocentes.model.Matricula;
import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
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
@WebServlet(name = "Avaliar", urlPatterns = {"/Avaliar"})
public class AvaliarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Aluno aluno = (Aluno) session.getAttribute("usuario");
        String strDisciplina = req.getParameter("disciplina");
        String strProfessor = req.getParameter("professor");
        DisciplinaController disciplinaController = new DisciplinaController();
        ProfessorController professorController = new ProfessorController();
        try {
            Disciplina disciplina = disciplinaController.get(new Disciplina(strDisciplina));
            Professor professor = professorController.get(new Professor(strProfessor));
            Matricula matricula = aluno.getMatriculaAtual();
            Avaliacao avaliacao = new Avaliacao(matricula, disciplina, professor);
            String strNota;
            for (Criterio criterio : Criterio.values()) {
                strNota = req.getParameter(criterio.getCodigo());
                avaliacao.avaliarCriterio(criterio, Integer.valueOf(strNota));
            }
            String observacoes = req.getParameter("observacoes");
            avaliacao.setObservacoes(observacoes);
            aluno.avaliar(avaliacao);
            professorController.update(professor);
            AlunoController alunoController = new AlunoController();
            alunoController.update(aluno);
            session.setAttribute("mensagem", "Professor avaliado");
        } catch (Exception ex) {
            session.setAttribute("mensagem", ex.getMessage());
        } finally {
            resp.sendRedirect("matriculas.jsp");
        }

    }

}
