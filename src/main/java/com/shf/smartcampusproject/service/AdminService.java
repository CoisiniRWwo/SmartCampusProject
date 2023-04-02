package com.shf.smartcampusproject.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.tb_admin;

public interface AdminService extends IService<tb_admin> {
    tb_admin login(LoginForm loginForm);

    tb_admin getAdminById(Long userId);

    IPage getAdminByOpr(Page<tb_admin> adminPage, String name);
}
