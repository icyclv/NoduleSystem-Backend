package com.neu.nodulesystem.contorller;


import com.neu.nodulesystem.dto.LoginFormDTO;
import com.neu.nodulesystem.dto.RegisterDto;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.dto.UserDTO;
import com.neu.nodulesystem.service.IUserService;
import com.neu.nodulesystem.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){

        return userService.login(loginForm, session);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto){

        return userService.register(registerDto);
    }

}
