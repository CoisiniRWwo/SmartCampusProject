package com.shf.smartcampusproject.pojo;

import lombok.Data;

/**
 * @Author:Su HangFei
 * @Date:2023-01-07 11 54
 * @Project:SmartCampusProject
 * @description : 用户登陆表单信息
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
