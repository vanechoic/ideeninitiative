package awe.ideeninitiative.security;


import awe.ideeninitiative.exception.IdeeninitiativeExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Leitet die Access-Denied-Exceptions auf den IdeeninitiativeExceptionHandler um, die durch mangelnde JWT entstehen.
 * @author https://stackoverflow.com/questions/19767267/handle-spring-security-authentication-exceptions-with-exceptionhandler
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        resolver.resolveException(request, response, null, exception);
    }
}