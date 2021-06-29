package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.SignInDTO;
import com.daoyun.demo.pojo.dto.SignLogDTO;
import com.daoyun.demo.service.ISignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "SignInController")
@RestController
@Slf4j
@RequestMapping("/sign")
public class SignInController {

    @Autowired(required = false)
    private ISignInService signInService;

    @ApiOperation(value = "教师发起签到")
    @PostMapping("/startSignIn")
    public ReturnInfo startSignIn(@RequestBody SignInDTO signInDTO){
        return signInService.startSignIn(signInDTO);
    }
    @ApiOperation(value = "教师发起一键签到")
    @PostMapping("/startClickSignIn")
    public ReturnInfo startClickSignIn(@RequestBody SignInDTO signInDTO){
        return signInService.startClickSignIn(signInDTO);
    }

    @ApiOperation(value = "教师停止签到")
    @PostMapping("/stopSignIn")
    public ReturnInfo stopClickSignIn(@RequestParam String course_code){
        return signInService.stopClickSignIn(course_code);
    }

    @ApiOperation(value = "查询是否有签到进行")
    @GetMapping("/hasSignIn")
    public ReturnInfo hasSignIn(@RequestParam String course_code){
        return signInService.hasSignIn(course_code);
    }

    @ApiOperation(value = "学生签到")
    @PostMapping("/signIn")
    public ReturnInfo signIn(@RequestBody SignLogDTO signLogDTO){
        return signInService.signIn(signLogDTO);
    }

    @ApiOperation(value = "签到情况")
    @GetMapping("/infoForCourse/{course_code}")
    public ReturnInfo infoForCourse(@PathVariable(name = "course_code") String course_code){
        return signInService.getSignInfoForCourse(course_code);
    }

    @ApiOperation(value = "所有签到记录")
    @GetMapping("/allSignIn")
    public ReturnInfo allSignIn(@RequestParam String course_code){
        return signInService.allSignIn(course_code);
    }


    @ApiOperation(value = "获取指定班课id的签到信息")
    @GetMapping("/getSignInInfoBYSignInId")
    public ReturnInfo getSignInInfoBySignInId(@RequestParam Integer sign_id){
        return signInService.getSignInInfoBySignInId(sign_id);
    }


    @ApiOperation(value = "学生补签")
    @PostMapping("/resign/{sign_id}/{student_id}")
    public ReturnInfo resign(@PathVariable("sign_id") Integer sign_id, @PathVariable("student_id") Integer student_id){
        return signInService.resign(sign_id, student_id);
    }

    @ApiOperation("获取学生指定班课的所有签到信息")
    @SneakyThrows
    @GetMapping("/getStudentSignInfoByCourseCode/{course_code}/{student_id}")
    public ReturnInfo getStudentSignInfoByCourseCode(@PathVariable("course_code") String course_code, @PathVariable("student_id") Integer student_id){
        return signInService.getStudentSignInfoByCourseCode(course_code, student_id);
    }
}
