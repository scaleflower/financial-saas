package com.fs.common.config;

import com.fs.common.security.UserAuthInfo;
import com.fs.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * JWT认证拦截器
 */
@Slf4j
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        // 获取Token
        String token = extractToken(request);

        if (token != null) {
            try {
                // 验证Token
                if (JwtUtil.validateToken(token)) {
                    // 解析用户信息
                    Map<String, Object> userInfo = JwtUtil.validateAndGetUserInfo(token);
                    UserAuthInfo userAuth = UserAuthInfo.builder()
                            .userId((Long) userInfo.get("userId"))
                            .tenantId((Long) userInfo.get("tenantId"))
                            .username((String) userInfo.get("username"))
                            .roles(userInfo.get("roles") != null ? (java.util.List<String>) userInfo.get("roles") : null)
                            .build();
                    // 设置到请求属性中（实际应用可以使用ThreadLocal）
                    request.setAttribute("userAuth", userAuth);
                    return true;
                }
            } catch (Exception e) {
                log.warn("JWT验证失败: {}", e.getMessage());
            }
        }

        // 无效Token或未提供Token，返回401
        response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未认证或Token已过期\"}");
        return false;
    }

    /**
     * 从请求头提取Token
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
