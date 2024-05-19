package com.way.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.way.bean.LoginUser;
import com.way.bean.User;
import com.way.mapper.MenuMapper;
import com.way.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中


        //todo 根据用户查询权限信息，添加到LoginUser（实现UserDetails接口）
//        List<String> list = new ArrayList<>(Arrays.asList("sys:hello"));
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        //封装UserDetails对象返回
        LoginUser loginUser = new LoginUser(user,list);
        return loginUser;

    }
}
