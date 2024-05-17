package com.way.service;

import com.way.bean.User;
import com.way.utils.ResponseResult;

public interface LoginServcie {
    public ResponseResult login(User user);
}
