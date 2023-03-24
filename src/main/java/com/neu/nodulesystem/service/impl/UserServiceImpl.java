package com.neu.nodulesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.nodulesystem.dto.LoginFormDTO;
import com.neu.nodulesystem.dto.RegisterDto;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.dto.UserDTO;
import com.neu.nodulesystem.entity.User;
import com.neu.nodulesystem.entity.UserInfo;
import com.neu.nodulesystem.mapper.UserMapper;
import com.neu.nodulesystem.service.IUserInfoService;
import com.neu.nodulesystem.service.IUserService;
import com.neu.nodulesystem.utils.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;



@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    @Transactional
    public Result register(RegisterDto registerDto) {
        if (registerDto == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StrUtil.isBlank(registerDto.getUserName())) {
            return Result.fail("账号不能为空");
        }
        if (StrUtil.isBlank(registerDto.getPassword())) {
            return Result.fail("密码不能为空");
        }
        if (StrUtil.isBlank(registerDto.getEmail())) {
            return Result.fail("邮箱不能为空");
        }
        if(StrUtil.isBlank(registerDto.getPhone())){
            return Result.fail("手机号不能为空");
        }
        if(StrUtil.isBlank(registerDto.getName() )){
            return Result.fail("姓名不能为空");
        }
        if(StrUtil.isBlank(registerDto.getDepartment())){
            return Result.fail("部门不能为空");
        }
        if(StrUtil.isBlank(registerDto.getPosition())){
            return Result.fail("职位不能为空");
        }
        // 验证账号是否已存在
        User u =query().eq("user_name", registerDto.getUserName()).one();
        if (u != null) {
           return Result.fail("该账号已被注册");
        }
        User user = BeanUtil.copyProperties(registerDto, User.class);
        user.setType(0);
        user.setStatus(1);
        // 随机生成盐
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        // 加密密码
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        save(user);
        // 获取注册用户的id
        Long userId = user.getId();
        UserInfo userInfo = BeanUtil.copyProperties(registerDto, UserInfo.class);
        userInfo.setUserId(userId);
        userInfoService.save(userInfo);

        return Result.ok();
    }
    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        // 空值处理
        if (StringUtils.isBlank(username)) {
            return Result.fail("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return Result.fail("密码不能为空");
        }


        // 验证账号
        User user =query().eq("user_name", username).one();
        if (user == null) {

            return Result.fail("该账号不存在");
        }

        // 验证状态
        if (user.getStatus() == 0) {
            // 账号未激活

            return Result.fail("该账号未激活");
        }

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {

            return Result.fail("账号或密码不正确");
        }



        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // 存入用户
        session.setAttribute("user", userDTO);


        return Result.ok();
    }



}
