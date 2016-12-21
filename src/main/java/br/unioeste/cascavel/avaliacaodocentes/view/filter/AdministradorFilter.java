package br.unioeste.cascavel.avaliacaodocentes.view.filter;

import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AdministradorFilter", urlPatterns = {"/administracao.jsp"})
public class AdministradorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        Usuario usuario = null;
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuario");
        }

        if (usuario == null || !(usuario instanceof Administrador)) {
            ((HttpServletResponse) response).sendRedirect("");
        } else if (usuario instanceof Administrador) {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
