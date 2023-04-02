package com.shf.smartcampusproject.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbGrade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Admin
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2023-01-07 11:49:37
*/
public interface TbGradeService extends IService<TbGrade> {

    IPage<TbGrade> getGradeByOpr(Page<TbGrade> tbGradePage, String gradeName);
}
