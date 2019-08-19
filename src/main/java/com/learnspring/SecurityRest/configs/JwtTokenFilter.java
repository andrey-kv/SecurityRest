package com.learnspring.SecurityRest.configs;

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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            log.info("Executed Authentication filter, token = " + token);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(req, res);
        } catch (RuntimeException ex) {
            HttpServletResponse response = (HttpServletResponse) res;
            log.info("Exception: " + ex.getMessage() + ", response commited = " + res.isCommitted());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"timestamp\": \"2019-08-19T22:00:52.001+0000\",\"status\": 401," +
                    " \"error\": \"Unauthorized\", \"message\": \"Unauthorized\"}");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
