package com.xyh.helloworld.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.common.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 1. 获取 HTTP 动词和路径
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 2. 细粒度放行规则（完全无 Token 要求）
        // 规则 A: POST /api/users → 允许注册
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);

        if (isCreateUser) {
            return true;  // 直接放行，无需 Token
        }

        boolean isPageQuery = "GET".equalsIgnoreCase(method) && "/api/users/page".equals(uri);
        if (isPageQuery) {
            return true;   // 无需 token 即可访问
        }
//        // 规则 B: GET /api/users/{id} → 允许查看用户信息
//        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");

        // 3. 其他请求（DELETE、PUT、GET /api/users 列表等）严格校验 Token
        String token = request.getHeader("Authorization");
        if (token == null || token.trim().isEmpty()) {
            // 构造统一错误响应并返回 401
            response.setContentType("application/json;charset=UTF-8");
            Result<Void> errorResult = Result.error(ResultCode.TOKEN_INVALID);
            String json = objectMapper.writeValueAsString(errorResult);
            response.getWriter().write(json);
            return false;
        }

        // 简单演示：只要存在非空 Token 即放行（实际项目可做 JWT 解析）
        return true;
    }
}