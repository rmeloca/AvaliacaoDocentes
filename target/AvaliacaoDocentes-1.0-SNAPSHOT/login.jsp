<%-- 
    Document   : login
    Created on : Jul 16, 2016, 4:48:50 PM
    Author     : romulo
--%>

<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Administrador"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Professor"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Aluno"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 100%">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avaliação do Docente</title>
        <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.1/build/pure-min.css">
    </head>
    <body>

        <%@include file="mensagem.jsp" %>

        <% Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario != null) {
                if (usuario instanceof Aluno) {
                    response.sendRedirect("matriculas.jsp");
                } else if (usuario instanceof Professor) {
                    response.sendRedirect("avaliacoes.jsp");
                } else if (usuario instanceof Administrador) {
                    response.sendRedirect("administracao.jsp");
                }
            }
            if (usuario == null) {
        %>
        <div class="pure-g" style="padding: 10%">
            <div class="pure-u-1-3" style="margin: auto">
                <form action="Login" method="post" class="pure-form pure-form-stacked">
                    <fieldset>
                        <legend>Insira suas credenciais</legend>
                        <input type="text" name="login" placeholder="Login" required autofocus>
                        <input type="password" name="senha" placeholder="Senha" required>
                        <input type="submit" value="Entrar" class="pure-button pure-button-primary"/>
                    </fieldset>
                </form>
            </div>
        </div>
        <%
            }
        %>
    </body>
</html>
