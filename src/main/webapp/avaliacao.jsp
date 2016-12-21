<%-- 
    Document   : avaliacao
    Created on : Aug 3, 2016, 12:53:36 AM
    Author     : romulo
--%>

<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Avaliacao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            Avaliacao avaliacao = (Avaliacao) session.getAttribute("avaliacao");
            out.println(avaliacao.getDisciplina().getCodigo());
            out.println(avaliacao.getDisciplina().getNome());
            out.println(avaliacao.getAno());
            out.println(avaliacao.getSemestre());
            out.println(avaliacao.getMedia());
            out.println(avaliacao.getObservacoes());
        %>
        <a href="avaliacoes.jsp">Voltar</a>
    </body>
</html>
