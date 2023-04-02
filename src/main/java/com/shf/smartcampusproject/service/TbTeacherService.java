package com.shf.smartcampusproject.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.TbTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Admin
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2023-01-07 11:49:50
*/
public interface TbTeacherService extends IService<TbTeacher> {

    TbTeacher login(LoginForm loginForm);

    TbTeacher getTeacherById(Long userId);

    IPage<TbTeacher> getTeachersByOpr(Page<TbTeacher> paraParam, String name);
}
