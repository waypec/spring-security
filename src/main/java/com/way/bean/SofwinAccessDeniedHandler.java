package com.way.bean;

import com.alibaba.fastjson.JSON;
import com.way.utils.ResponseResult;
import com.way.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SofwinAccessDeniedHandler  implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(402,"权限不足");
        String resultStr = JSON.toJSONString(result);
        WebUtils.renderString(response,resultStr);
    }
}
