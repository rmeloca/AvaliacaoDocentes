<%
    String erro = (String) session.getAttribute("mensagem");
    if (erro != null) {
        out.println(erro);
        session.setAttribute("mensagem", null);
    }
%>