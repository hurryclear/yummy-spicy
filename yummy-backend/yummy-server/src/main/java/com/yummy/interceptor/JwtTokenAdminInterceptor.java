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
 * interceptor of the jwt token validation
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * validate jwt token 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return boolean (true: pass, false: not pass)
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // what has been intercepted? Method of Controller of other resources?
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行 ???
            return true;
        }

        // 1. get the jwt token from the request header
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 2. validate jwt token
        // TODO: not very clear yet
        try {
            log.info("check jwt token:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("id of the current employee：{}", empId);
            BaseContext.setCurrentId(empId);
            // 3. pass => true
            return true;
        } catch (Exception ex) {
            // 4. not pass ==> response 401 status code
            response.setStatus(401);
            return false;
        }
    }
}