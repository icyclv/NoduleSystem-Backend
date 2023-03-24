package com.neu.nodulesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.nodulesystem.dto.LoginFormDTO;
import com.neu.nodulesystem.dto.RegisterDto;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.User;
import com.neu.nodulesystem.entity.UserInfo;

import javax.servlet.http.HttpSession;

public interface IUserInfoService extends IService<UserInfo> {

}
