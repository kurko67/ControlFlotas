package com.kurko67.controlflotas.util.paginator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/vehicles/my"); // Redirige a /vehicles/my para usuarios normales
                return;
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/"); // Redirige a / para administradores
                return;
            }
        }

        // Si el rol no coincide con ninguno de los esperados, redirige a una página de inicio genérica
        response.sendRedirect("/inicio");
    }
}
