package com.yummy.interceptor;

import com.yummy.constant.JwtClaimsConstant;
import com.yummy.context.BaseContext;
import com.yummy.properties.JwtProperties;
import com.yummy.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT token interceptor for user authentication
 * Validates JWT tokens for WeChat user requests
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Validate JWT token for user requests
     * 
     * @param request  HTTP request
     * @param response HTTP response  
     * @param handler  request handler
     * @return true if token is valid, false otherwise
     * @throws Exception if validation fails
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Check if the handler is a controller method
        if (!(handler instanceof HandlerMethod)) {
            // Not a controller method, allow request to proceed
            return true;
        }
        // 1. Get JWT token from request header
        String token = request.getHeader(jwtProperties.getUserTokenName());
        // 2. Validate JWT token
        try {
            log.info("Validating user JWT token: {}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("Current user ID: {}", userId);
            // Store user ID in thread local for later use
            BaseContext.setCurrentId(userId);
            // 3. Validation successful, allow request to proceed
            return true;
        } catch (Exception ex) {
            // 4. Validation failed, return 401 Unauthorized
            log.error("User JWT token validation failed: {}", ex.getMessage());
            response.setStatus(401);
            return false;
        }
    }
}