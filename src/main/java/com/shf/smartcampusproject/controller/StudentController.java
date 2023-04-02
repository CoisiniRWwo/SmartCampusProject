package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbStudent;
import com.shf.smartcampusproject.service.TbStudentService;
import com.shf.smartcampusproject.util.MD5;
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

@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private TbStudentService tbStudentService;

    @ApiOperation("带条件的学生信息的分页查询")
    @GetMapping("getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
                                  @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
                                  @ApiParam("分页查询的年级信息") @RequestParam(value = "clazzName", required = false, defaultValue = "") String clazzName,
                                  @ApiParam("分页查询的班级信息") @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        Page<TbStudent> tbstudentPage = new Page<>(pageNo, pageSize);
        IPage<TbStudent> page = tbStudentService.getClazzByOpr(tbstudentPage, clazzName, name);

        return Result.ok(page);
    }

    @ApiOperation("保存或修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("要保存或修改的学生JSON") @RequestBody TbStudent student) {
        Integer id = student.getId();
        if (id == null || id == 0) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        tbStudentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("删除单个或多个学生信息")
    @DeleteMapping("/delStudentById")
    public Result deleteStudentById(@ApiParam("要删除的学生编号的JSON集合") @RequestBody List<Integer> students) {
        tbStudentService.removeByIds(students);
        return Result.ok();
    }

}
