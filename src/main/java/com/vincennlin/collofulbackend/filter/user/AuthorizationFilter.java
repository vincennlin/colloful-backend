package com.vincennlin.collofulbackend.filter.user;

import com.vincennlin.collofulbackend.security.JwtClaimsParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {

        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null) return null;

        String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "").trim();
        String tokenSecret = environment.getProperty("token.secret");

        if (tokenSecret == null) return null;

        JwtClaimsParser jwtClaimsParser = new JwtClaimsParser(token, tokenSecret);
        Long userId = Long.parseLong(jwtClaimsParser.getJwtSubject());

        return new UsernamePasswordAuthenticationToken(userId, authorizationHeader, jwtClaimsParser.getUserAuthorities());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = request.getRequestURI().contains("auth");
        return shouldNotFilter;
    }
}
