package com.learnspring.SecurityRest.configs;

import com.learnspring.SecurityRest.exceptions.ExpiredOrInvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        log.info("Executed Authentication filter");
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(req, res);
        } catch (RuntimeException ex) {
            throw new ExpiredOrInvalidTokenException(ex.getMessage());
        }
    }
}
