package com.shf.smartcampusproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shf.smartcampusproject.pojo.LoginForm;
import com.shf.smartcampusproject.pojo.TbStudent;
import com.shf.smartcampusproject.pojo.TbTeacher;
import com.shf.smartcampusproject.pojo.tb_admin;
import com.shf.smartcampusproject.service.AdminService;
import com.shf.smartcampusproject.service.TbStudentService;
import com.shf.smartcampusproject.service.TbTeacherService;
import com.shf.smartcampusproject.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:Su HangFei
 * @Date:2023-01-07 12 22
 * @Project:SmartCampusProject
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TbStudentService tbStudentService;
    @Autowired
    private TbTeacherService tbTeacherService;


    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        request.getSession().setAttribute("verifiCode", verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String verifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(verifiCode)) {
            return Result.fail().message("验证码有误，请刷新后重试");
        }
        //从session域中移除现有验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验
        //准备一个map用户存放响应的数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    tb_admin admin = adminService.login(loginForm);
                    if (admin != null) {
                        //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    TbStudent Student = tbStudentService.login(loginForm);
                    if (Student != null) {
                        //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(Student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    TbTeacher teacher = tbTeacherService.login(loginForm);
                    if (teacher != null) {
                        //用户的类型和用户id转换成一个密文，以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    @GetMapping("/getInfo")
    public Result getinfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出 用户id 和 用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                tb_admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                TbStudent student = tbStudentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                TbTeacher teacher = tbTeacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile headerImg){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //获取原始文件名
        String originalFilename = headerImg.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String newFileName = uuid + originalFilename.substring(index);
        //保存文件
        try {
            headerImg.transferTo(new File("D:\\Study\\SHFWorkSpace\\SmartCampusProject\\target\\classes\\public\\upload\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应图片的路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    @ApiOperation("更新密码的处理器")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token")String token,@PathVariable("oldPwd") String oldPwd, @PathVariable("newPwd") String newPwd ){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.fail().message("token失效，请重新登陆后修改密码");
        }
        //获取用户ID和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                LambdaQueryWrapper<tb_admin> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(tb_admin::getPassword,oldPwd);
                tb_admin admin = adminService.getById(userId);
                if (admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                LambdaUpdateWrapper<TbStudent> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(TbStudent::getId,userId)
                        .eq(TbStudent::getPassword,oldPwd)
                        .set(TbStudent::getPassword,newPwd);
                boolean update = tbStudentService.update(updateWrapper);
                if (!update){
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                LambdaUpdateWrapper<TbTeacher> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(TbTeacher::getId,userId)
                        .eq(TbTeacher::getPassword,oldPwd)
                        .set(TbTeacher::getPassword,newPwd);
                boolean result = tbTeacherService.update(lambdaUpdateWrapper);
                if (!result){
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }
}
