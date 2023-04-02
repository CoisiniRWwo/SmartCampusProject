package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbClazz;
import com.shf.smartcampusproject.pojo.TbGrade;
import com.shf.smartcampusproject.service.TbClazzService;
import com.shf.smartcampusproject.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:Su HangFei
 * @Date:2023-01-07 12 09
 * @Project:SmartCampusProject
 */

@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private TbClazzService tbClazzService;

    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
                                 @ApiParam("分页查询的年级信息") @RequestParam(value = "gradeName", required = false, defaultValue = "") String gradeName,
                                 @ApiParam("分页查询的班级信息") @RequestParam(value = "name", required = false, defaultValue = "") String name) {

        Page<TbClazz> tbClazzPage = new Page<>(pageNo, pageSize);
        IPage<TbClazz> page = tbClazzService.getClazzByOpr(tbClazzPage, gradeName, name);

        return Result.ok(page);
    }

    @ApiOperation("新增或修改clazz，有id属性是修改，没有则是增加")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("Json的Clazz对象") @RequestBody TbClazz tbClazz) {
        tbClazzService.saveOrUpdate(tbClazz);
        return Result.ok();
    }

    @ApiOperation("删除单个或多个班级信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("要删除的所有的class的id的json集合") @RequestBody List<Integer> ids){
        tbClazzService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<TbClazz> list = tbClazzService.list();
        return Result.ok(list);
    }
}
