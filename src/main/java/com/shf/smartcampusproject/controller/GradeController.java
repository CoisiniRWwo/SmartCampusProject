package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbGrade;
import com.shf.smartcampusproject.service.TbGradeService;
import com.shf.smartcampusproject.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:Su HangFei
 * @Date:2023-01-07 12 10
 * @Project:SmartCampusProject
 */

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private TbGradeService tbGradeService;

    @ApiOperation("根据年级名称模糊查询，带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo,
                            @ApiParam("分页查询的页大小")@PathVariable("pageSize") Integer pageSize,
                            @ApiParam("分页查询模糊匹配的名称")@RequestParam(value = "gradeName", required = false, defaultValue = "") String gradeName) {
        //分页 带条件查询
        Page<TbGrade> tbGradePage = new Page<>(pageNo, pageSize);
        //通过服务层查询
        IPage<TbGrade> page = tbGradeService.getGradeByOpr(tbGradePage, gradeName);
        //封装Result对象并返回
        return Result.ok(page);
    }

    @ApiOperation("新增或修改geade，有id属性是修改，没有则是增加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("Json的Grade对象") @RequestBody TbGrade tbGrade){
        //接收参数
        //调用服务层方法完成增减或修改
        tbGradeService.saveOrUpdate(tbGrade);
        return Result.ok();
    }

    @ApiOperation("删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的所有的grade的id的json集合") @RequestBody List<Integer> ids){
        tbGradeService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<TbGrade> gradeList = tbGradeService.list();
        return Result.ok(gradeList);
    }

}
