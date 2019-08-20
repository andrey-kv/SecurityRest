package com.learnspring.SecurityRest.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnspring.SecurityRest.models.ErrorResponse;
import io.jsonwebtoken.JwtException;
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
        } catch (JwtException ex) {
            log.info("Exception: " + ex.getMessage() + ", response commited = " + res.isCommitted());
            prepareResponse(res, HttpServletResponse.SC_UNAUTHORIZED, ex);
        } catch (RuntimeException ex) {
            log.info("Exception: " + ex.getMessage());
            prepareResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void prepareResponse(ServletResponse res, int status, RuntimeException ex) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(status, ex);
        res.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

        if (res instanceof HttpServletResponse) {
            ((HttpServletResponse) res).setStatus(status);
         }

        res.getWriter().flush();
        res.getWriter().close();
    }
}
