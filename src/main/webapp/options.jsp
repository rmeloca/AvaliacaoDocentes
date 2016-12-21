<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        out.println(usuario.getNome());
%>

<form action="Options" method="post">
    <input type="hidden" name="option" value="logout"/>
    <input type="submit" value="Logout"/>
</form>

<%
    }
%>