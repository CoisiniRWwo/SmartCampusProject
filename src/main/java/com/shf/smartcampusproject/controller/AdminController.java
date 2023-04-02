package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.smartcampusproject.pojo.tb_admin;
import com.shf.smartcampusproject.service.AdminService;
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
 * @Date:2023-01-07 12 09
 * @Project:SmartCampusProject
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("分页待条件查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
                              @ApiParam("分页查询的管理员名字") @RequestParam(value = "name", required = false, defaultValue = "") String name){
        Page<tb_admin> adminPage = new Page<>(pageNo, pageSize);
        IPage page = adminService.getAdminByOpr(adminPage,name);
        return Result.ok(page);
    }

    @ApiOperation("增加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("要保存或修改的admin的JSON") @RequestBody tb_admin tbAdmin){
        Integer id = tbAdmin.getId();
        if (id == null || id == 0) {
            tbAdmin.setPassword(MD5.encrypt(tbAdmin.getPassword()));
        }
        adminService.saveOrUpdate(tbAdmin);
        return Result.ok();
    }

    @ApiOperation("删除单个或多个管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("要删除的多个管理员编号的JSON集合") @RequestBody List<Integer> students){
        adminService.removeByIds(students);
        return Result.ok();
    }

}
