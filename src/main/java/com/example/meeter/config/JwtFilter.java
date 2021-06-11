package com.example.meeter.config;

import com.example.meeter.exceptions.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends BasicAuthenticationFilter {

    private final String SECRET = "Lj1xiAOz/D+E{E%";

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        try {
            UsernamePasswordAuthenticationToken authResult = getAuthByToken(header);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        } catch (JwtException | NullPointerException e) {
            response.setStatus(400);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthByToken(String header) throws JwtException {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(header.replace("Bearer ", ""));
        String username = claimsJws.getBody().get("sub").toString();
        return new UsernamePasswordAuthenticationToken(username, null, null);
    }
}
