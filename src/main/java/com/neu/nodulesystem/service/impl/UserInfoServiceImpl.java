package com.neu.nodulesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.nodulesystem.entity.UserInfo;
import com.neu.nodulesystem.mapper.UserInfoMapper;
import com.neu.nodulesystem.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
