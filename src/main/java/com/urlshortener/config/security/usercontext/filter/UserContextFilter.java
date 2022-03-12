package com.urlshortener.config.security.usercontext.filter;

import com.urlshortener.config.security.usercontext.UserContext;
import com.urlshortener.config.security.usercontext.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserContextFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var userContext = new UserContext();
        var correlationId = httpRequest.getHeader(UserContext.CORRELATION_ID);
        userContext.setCorrelationId(correlationId);
        LOG.info("Correlation id is: {}", correlationId);
        var authToken = httpRequest.getHeader(UserContext.AUTH_TOKEN);
        userContext.setAuthToken(authToken);
        var userId = httpRequest.getHeader(UserContext.USER_ID);
        userContext.setUserId(userId);

        UserContextHolder.setUserContextThreadLocal(userContext);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
