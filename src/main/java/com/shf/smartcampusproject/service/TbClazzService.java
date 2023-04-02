package com.shf.smartcampusproject.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbClazz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Admin
 * @description 针对表【tb_clazz】的数据库操作Service
 * @createDate 2023-01-07 11:47:28
 */
public interface TbClazzService extends IService<TbClazz> {

    IPage<TbClazz> getClazzByOpr(Page<TbClazz> tbClazzPage, String gradeName, String name);
}
