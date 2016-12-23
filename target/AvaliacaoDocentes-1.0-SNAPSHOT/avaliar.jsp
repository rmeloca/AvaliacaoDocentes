<%-- 
    Document   : disciplina
    Created on : Jul 31, 2016, 10:06:37 PM
    Author     : romulo
--%>

<%@page import="br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Criterio"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Professor"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Disciplina"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.controller.DisciplinaController"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Aluno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Aluno aluno = (Aluno) session.getAttribute("usuario");
            String strDisciplina = request.getParameter("disciplina");
            DisciplinaController disciplinaController = new DisciplinaController();
            try {
                Disciplina disciplina = disciplinaController.get(new Disciplina(strDisciplina));
                Professor professor = aluno.getMatriculaAtual().getProfessor(disciplina);
%>
        <h3>Avaliaçao do professor</h3>

        <h4><%=disciplina.getCodigo()%></h4>
        <h4><%=disciplina.getNome()%></h4>
        <h4><%=professor.getNome()%></h4>

        <form action="Avaliar" method="post">
            <%request.setAttribute("disciplina", disciplina);%>
            <%request.setAttribute("professor", professor);%>
            <input type="hidden" name="disciplina" value="<%=disciplina.getCodigo()%>" />
            <input type="hidden" name="professor" value="<%=professor.getLogin()%>" />
            <%
                for (Criterio criterio : Criterio.values()) {
                    out.print(criterio.toString());
                    for (int i = 1; i <= 5; i++) {
            %>
            <input type="radio" name="<%=criterio.getCodigo()%>" value="<%=i%>" required ><%=i%>
            <%
                    }
                    out.println("<br>");
                }
            %>
            Observaçoes
            <textarea name="observacoes" rows="7" cols="40"></textarea>
            <input type="submit" value="Avaliar"/>
        </form>
        <%
            } catch (ItemNotFoundException ex) {
                session.setAttribute("mensagem", ex.getMessage());
                response.sendRedirect("");
            }
        %>
    </body>
</html>
