<%-- 
    Document   : avaliacoes
    Created on : Jul 25, 2016, 9:13:48 PM
    Author     : romulo
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Avaliacao"%>
<%@page import="java.util.List"%>
<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Professor"%>
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
        <form action="VisualizarAvaliacao" method="post">
            <table border="1">
                <tr>
                    <th>Disciplina</th>
                    <th>Ano</th>
                    <th>Media</th>
                    <th>Resumo</th>
                </tr>
                <%            Professor professor = (Professor) usuario;
                    List<Avaliacao> avaliacoes = professor.getAvaliacoesNaoLidas();
                    Map<Integer, Avaliacao> idHasAvaliacao = new HashMap<>();
                    int id = 0;
                    for (Avaliacao avaliacao : avaliacoes) {
                        idHasAvaliacao.put(id, avaliacao);
                        out.println("<tr>");
                        out.println("<td>");
                        out.println(avaliacao.getDisciplina().getCodigo());
                        out.println("</td>");
                        out.println("<td>");
                        out.println(avaliacao.getAno());
                        out.println("/");
                        out.println(avaliacao.getSemestre());
                        out.println("</td>");
                        out.println("<td>");
                        out.println(avaliacao.getMedia());
                        out.println("</td>");
                        out.println("<td>");
                        if (avaliacao.getObservacoes().length() > 80) {
                            out.println(avaliacao.getObservacoes().substring(0, 80));
                        } else {
                            out.println(avaliacao.getObservacoes());
                        }
                        out.println("</td>");
                %>
                <td>
                    <button type="submit" name="avaliacao" value="<%=id%>">Visualizar</button>
                </td>
                <%
                        out.println("</tr>");
                        id++;
                    }
                    session.setAttribute("idHasAvaliacao", idHasAvaliacao);
                %>
            </table>
        </form>
    </body>
</html>
