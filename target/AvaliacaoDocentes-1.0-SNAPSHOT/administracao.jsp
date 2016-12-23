<%-- 
    Document   : administracao
    Created on : Jul 25, 2016, 9:14:00 PM
    Author     : romulo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Painel de Adminstração</title>

        <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.1/build/pure-min.css" integrity="sha384-" crossorigin="anonymous">

        <!--[if lte IE 8]>
            <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.1/build/grids-responsive-old-ie-min.css">
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.1/build/grids-responsive-min.css">
        <!--<![endif]-->

        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">

        <!--[if lte IE 8]>
            <link rel="stylesheet" href="css/layouts/marketing-old-ie.css">
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="css/layouts/marketing.css">
        <!--<![endif]-->
    </head>
    <body>
        <div class="content-wrapper">
            <div class="content">
                <%@include file="mensagem.jsp" %>
                <%@include file="options.jsp" %>
                <form class="pure-form pure-form-stacked" action="AtualizarBanco" method="post" enctype="multipart/form-data">
                    <input type="file" name="arquivo" />
                    <input type="submit" name="Atualizar"/>
                </form>
            </div>
        </div>
    </body>
</html>
