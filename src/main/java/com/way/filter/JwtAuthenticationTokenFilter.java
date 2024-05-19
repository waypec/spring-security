package com.way.filter;

import com.way.bean.LoginUser;
import com.way.utils.JwtUtil;
import com.way.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 认证过滤器 (如果已经登录过就直接设置setAuthentication()方法)
 */
@Component
public class JwtAuthenticationTokenFilter  extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        //如果token为空
        if (StringUtils.isBlank(token)) {
            //直接放行
            filterChain.doFilter(request,response);
            return;
        }
        String userId = "";
        //将token解析
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //通过userId获取redis中值
        String redisKey = "login:"+userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser == null) {
            throw new RuntimeException("用户未登录或登录超时，请重新登录");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}