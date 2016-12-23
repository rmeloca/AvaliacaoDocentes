<%-- 
    Document   : matriculas
    Created on : Jul 25, 2016, 2:19:44 AM
    Author     : romulo
--%>

<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Disciplina"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Professor"%>
<%@page import="java.util.Map"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Aluno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="options.jsp" %>

        <%@include file="mensagem.jsp" %>

        <%            try {
                Aluno aluno = (Aluno) usuario;
                out.println("<ul>");
                for (Map.Entry<Disciplina, Professor> entry : aluno.getPossiveisAvaliacoes()) {
                    Professor professor = entry.getValue();
                    Disciplina disciplina = entry.getKey();
                    out.println("<li>");
                    out.println("<a href=\"avaliar.jsp?disciplina=" + disciplina.getCodigo() + "\">");
                    out.println(disciplina.getNome());
                    out.println("</a>");
                    out.println(professor.getNome());
                    out.println("</li>");
                }
                out.println("</ul>");
            } catch (Exception ex) {
                out.println("Erro: " + ex.getMessage());
            }
        %>

        <h1>Hello World!</h1>
    </body>
</html>
