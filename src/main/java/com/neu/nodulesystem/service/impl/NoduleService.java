package com.neu.nodulesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.nodulesystem.entity.Nodule;
import com.neu.nodulesystem.entity.User;
import com.neu.nodulesystem.mapper.NoduleMapper;
import com.neu.nodulesystem.service.INoduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoduleService extends ServiceImpl<NoduleMapper, Nodule> implements INoduleService {


}
