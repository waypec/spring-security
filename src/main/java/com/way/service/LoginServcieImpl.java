package com.way.service;


import com.way.bean.LoginUser;
import com.way.bean.User;
import com.way.utils.JwtUtil;
import com.way.utils.RedisCache;
import com.way.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServcieImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证接口
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果是空代表认证失败
        if (authenticate == null) {
            throw new RuntimeException("认证失败");
        }
        //认证成功 从中取出
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //使用userID生成jwt返回给前端
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        //authenticate存入redis   超时时间1小时
        redisCache.setCacheObject("login:"+userId,loginUser,1, TimeUnit.HOURS);
        return  new ResponseResult(200,"登录成功",map);
    }
}
