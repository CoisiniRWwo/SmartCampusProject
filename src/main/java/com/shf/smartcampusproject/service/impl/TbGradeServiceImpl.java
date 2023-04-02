package com.shf.smartcampusproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.smartcampusproject.pojo.TbGrade;
import com.shf.smartcampusproject.service.TbGradeService;
import com.shf.smartcampusproject.mapper.TbGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Admin
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2023-01-07 11:49:37
*/
@Service
@Transactional
public class TbGradeServiceImpl extends ServiceImpl<TbGradeMapper, TbGrade>
    implements TbGradeService{

    @Override
    public IPage<TbGrade> getGradeByOpr(Page<TbGrade> tbGradePage, String gradeName) {
        LambdaQueryWrapper<TbGrade> wrapper = Wrappers.lambdaQuery();

        if (!gradeName.isEmpty()){
            wrapper.like(TbGrade::getName, gradeName);
        }

        wrapper.orderByDesc(TbGrade::getId);

        return page(tbGradePage, wrapper);

    }
}




