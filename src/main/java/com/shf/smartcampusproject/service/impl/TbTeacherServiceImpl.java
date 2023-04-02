package com.shf.smartcampusproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.TbTeacher;
import com.shf.smartcampusproject.service.TbTeacherService;
import com.shf.smartcampusproject.mapper.TbTeacherMapper;
import com.shf.smartcampusproject.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Admin
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2023-01-07 11:49:50
*/
@Service
@Transactional
public class TbTeacherServiceImpl extends ServiceImpl<TbTeacherMapper, TbTeacher>
    implements TbTeacherService{

    @Override
    public TbTeacher login(LoginForm loginForm) {
        LambdaQueryWrapper<TbTeacher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TbTeacher::getName,loginForm.getUsername());
        wrapper.eq(TbTeacher::getPassword, MD5.encrypt(loginForm.getPassword()));
        TbTeacher teacher = getOne(wrapper);
        return teacher;
    }

    @Override
    public TbTeacher getTeacherById(Long userId) {
        return getById(userId);
    }

    @Override
    public IPage<TbTeacher> getTeachersByOpr(Page<TbTeacher> paraParam, String name) {
        LambdaQueryWrapper<TbTeacher> wrapper = Wrappers.lambdaQuery();

        if (!name.isEmpty()) {
            wrapper.like(TbTeacher::getName, name);
        }

        wrapper.orderByDesc(TbTeacher::getId);

        return page(paraParam, wrapper);
    }
}




