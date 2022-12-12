package com.app.blog.security;

//CONETA CLASE INICIA EL DESARROLLO DE JWT

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Aqui empieza el desarrollo de Json web token
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //Manejar los errores de que un susario no esta autorizado
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
    }
}
