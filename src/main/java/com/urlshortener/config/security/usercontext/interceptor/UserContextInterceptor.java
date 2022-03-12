package com.urlshortener.config.security.usercontext.interceptor;

import com.urlshortener.config.security.usercontext.UserContext;
import com.urlshortener.config.security.usercontext.UserContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getUserContextThreadLocal().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getUserContextThreadLocal().getAuthToken());

        UserContextHolder.clean();

        return execution.execute(request, body);
    }
}
