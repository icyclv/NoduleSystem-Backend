package com.neu.nodulesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.nodulesystem.dto.LoginFormDTO;
import com.neu.nodulesystem.dto.RegisterDto;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.dto.UserDTO;
import com.neu.nodulesystem.entity.User;

import javax.servlet.http.HttpSession;


public interface IUserService extends IService<User> {

    Result register(RegisterDto user);
    Result login(LoginFormDTO loginForm, HttpSession session);

}
