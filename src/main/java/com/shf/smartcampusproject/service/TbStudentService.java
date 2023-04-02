package com.shf.smartcampusproject.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.TbStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Admin
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2023-01-07 11:49:46
*/
public interface TbStudentService extends IService<TbStudent> {
    TbStudent login(LoginForm loginForm);

    TbStudent getStudentById(Long userId);

    IPage<TbStudent> getClazzByOpr(Page<TbStudent> tbStudentPage, String clazzName, String name);
}
