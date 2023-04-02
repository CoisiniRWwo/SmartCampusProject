package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.TbTeacher;
import com.shf.smartcampusproject.service.TbTeacherService;
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
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TbTeacherService tbTeacherService;

    /*
     * get  sms/teacherController/getTeachers/1/3
     *      sms/teacherController/getTeachers/1/3?name=小&clazzName=一年一班
     *      请求数据
     *      响应Result  data= 分页
     * */
    @ApiOperation("分页获取教师信息,带搜索条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo ,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize ,
            @ApiParam("分页查询的教师名字") @RequestParam(value = "name", required = false, defaultValue = "") String name
    ){
        Page<TbTeacher> paraParam =new Page<>(pageNo,pageSize);

        IPage<TbTeacher> page = tbTeacherService.getTeachersByOpr(paraParam,name);

        return Result.ok(page);
    }


    /*
     * post sms/teacherController/saveOrUpdateTeacher
     *      请求数据 Teacher
     *      响应Result data= null OK
     * */
    @ApiOperation("添加或者修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("要保存或者修改的JOSN格式的Teacher") @RequestBody TbTeacher teacher
    ){

        // 如果是新增,要对密码进行加密
        if (teacher.getId() == null || teacher.getId() ==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }

        tbTeacherService.saveOrUpdate(teacher);
        return  Result.ok();
    }

    /*
     * DELETE sms/teacherController/deleteTeacher
     *   请求的数据 JSON 数组 [1,2,3]
     *   响应Result data =null  OK
     *
     * */
    @ApiOperation("删除单个或者多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("要删除的教师信息的id的JOSN集合")  @RequestBody List<Integer> ids
    ){
        tbTeacherService.removeByIds(ids);
        return Result.ok();
    }

}
