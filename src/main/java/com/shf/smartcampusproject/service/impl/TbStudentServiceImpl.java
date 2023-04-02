package com.shf.smartcampusproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.TbStudent;
import com.shf.smartcampusproject.service.TbStudentService;
import com.shf.smartcampusproject.mapper.TbStudentMapper;
import com.shf.smartcampusproject.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Admin
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2023-01-07 11:49:46
*/
@Service
@Transactional
public class TbStudentServiceImpl extends ServiceImpl<TbStudentMapper, TbStudent>
    implements TbStudentService{

    @Override
    public TbStudent login(LoginForm loginForm) {
        LambdaQueryWrapper<TbStudent> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TbStudent::getName,loginForm.getUsername());
        wrapper.eq(TbStudent::getPassword,MD5.encrypt(loginForm.getPassword()));
        TbStudent student = getOne(wrapper);
        return student;
    }

    @Override
    public TbStudent getStudentById(Long userId) {
        return getById(userId);
    }

    @Override
    public IPage<TbStudent> getClazzByOpr(Page<TbStudent> tbStudentPage, String clazzName, String name) {
        LambdaQueryWrapper<TbStudent> wrapper = Wrappers.lambdaQuery();

        if (!clazzName.isEmpty()) {
            wrapper.like(TbStudent::getClazzName, clazzName);
        }

        if (!name.isEmpty()) {
            wrapper.like(TbStudent::getName, name);
        }

        wrapper.orderByDesc(TbStudent::getId);

        return page(tbStudentPage, wrapper);
    }
}




