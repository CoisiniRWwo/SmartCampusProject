package com.shf.smartcampusproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.smartcampusproject.mapper.AdminMapper;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.tb_admin;
import com.shf.smartcampusproject.service.AdminService;
import com.shf.smartcampusproject.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:Su HangFei
 * @Date:2023-01-07 12 01
 * @Project:SmartCampusProject
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, tb_admin> implements AdminService {
    @Override
    public tb_admin login(LoginForm loginForm) {
        LambdaQueryWrapper<tb_admin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(tb_admin::getName,loginForm.getUsername());
        wrapper.eq(tb_admin::getPassword,MD5.encrypt(loginForm.getPassword()));
        tb_admin admin = getOne(wrapper);
        return admin;
    }

    @Override
    public tb_admin getAdminById(Long userId) {
       return getById(userId);
    }

    @Override
    public IPage getAdminByOpr(Page<tb_admin> adminPage, String name) {
        LambdaQueryWrapper<tb_admin> wrapper = Wrappers.lambdaQuery();

        if (!name.isEmpty()) {
            wrapper.like(tb_admin::getName, name);
        }

        wrapper.orderByDesc(tb_admin::getId);

        return page(adminPage, wrapper);
    }
}
