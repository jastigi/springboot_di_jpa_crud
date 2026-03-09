package com.jastigi.curso.springboot.app.springboot_crud.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jastigi.curso.springboot.app.springboot_crud.entities.User;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            // TODO: handle exception
        } catch (DatabindException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());

        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        String token = Jwts.builder().subject(username).signWith(SECRET_KEY).compact();

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", "Login exitoso");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

    }

}
