<%@page import="br.unioeste.cascavel.avaliacaodocentes.model.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario != null) {
        out.println(usuario.getNome());
%>

<div class="header">
    <div class="home-menu pure-menu pure-menu-horizontal pure-menu-fixed">
        <a class="pure-menu-heading" href="">Avaliação do Docente</a>

        <ul class="pure-menu-list">
            <li class="pure-menu-item pure-menu-selected"><a href="#" class="pure-menu-link">Home</a></li>
            <li class="pure-menu-item"><a href="Options?option=logout" class="pure-menu-link">Logout</a></li>
        </ul>
    </div>
</div>


<%
    }
%>