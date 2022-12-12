package com.app.blog.security;
//Generar token, obtener claims

import com.app.blog.exception.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
//Paso 2, desarrollo de Jwt
@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date FechaExpiration = new Date(fechaActual.getTime()+jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(FechaExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    public String obtenerUsernameDelJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validarToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return  true;
        }catch(SignatureException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Firma JWT inválida!");
        }

        catch(MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT inválido!");
        }

        catch(ExpiredJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT expirado!");
        }

        catch(UnsupportedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT incompatible!");
        }

        catch(IllegalArgumentException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT está vacía!");
        }
    }

}
