package com.shf.smartcampusproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.smartcampusproject.pojo.TbClazz;
import com.shf.smartcampusproject.pojo.TbGrade;
import com.shf.smartcampusproject.service.TbClazzService;
import com.shf.smartcampusproject.mapper.TbClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 * @description 针对表【tb_clazz】的数据库操作Service实现
 * @createDate 2023-01-07 11:47:28
 */
@Service
@Transactional
public class TbClazzServiceImpl extends ServiceImpl<TbClazzMapper, TbClazz> implements TbClazzService {

    @Override
    public IPage<TbClazz> getClazzByOpr(Page<TbClazz> tbClazzPage, String gradeName, String name) {
        LambdaQueryWrapper<TbClazz> wrapper = Wrappers.lambdaQuery();

        if (!gradeName.isEmpty()) {
            wrapper.like(TbClazz::getGradeName, gradeName);
        }

        if (!name.isEmpty()) {
            wrapper.like(TbClazz::getName, name);
        }

        wrapper.orderByDesc(TbClazz::getId);

        return page(tbClazzPage, wrapper);
    }
}




