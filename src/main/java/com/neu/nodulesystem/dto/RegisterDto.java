package com.neu.nodulesystem.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterDto {
    /**
     * 账号名称
     */
    private String userName;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 性别，0：男，1：女
     */
    private Boolean gender;

    /**
     * 部门
     */
    private String department;

    /**
     * 职位
     */
    private String position;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate birthday;

}
