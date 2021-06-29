package com.daoyun.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daoyun.demo.componet.AsyncStopSignIn;
import com.daoyun.demo.mapper.*;
import com.daoyun.demo.pojo.*;
import com.daoyun.demo.pojo.dto.*;
import com.daoyun.demo.service.ISignInService;
import com.daoyun.demo.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service(value = "SignInServiceImpl")
public class SignInServiceImpl implements ISignInService {

    @Autowired(required = false)
    private SignInMapper signInMapper;

    @Autowired(required = false)
    private SignLogMapper signLogMapper;

    @Autowired(required = false)
    private CourseMapper courseMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private AsyncStopSignIn asyncStopSignIn;

    @Autowired(required = false)
    private SysParamMapper sysParamMapper;

    @Autowired(required = false)
    private ParticipateInCourseMapper participateInCourseMapper;


    @Override
    public ReturnInfo startSignIn(SignInDTO signInDTO) {
        SignIn signIn;
        signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", signInDTO.getCourseCode()).eq("status", 1));
        if (signIn != null){
            return ReturnInfo.error("当前课程还有正在进行的签到，不能发起新的签到");
        }
        signIn = new SignIn();
        signIn.setStatus(1);
        signIn.setCourseCode(signInDTO.getCourseCode());
        signIn.setLatitude(signInDTO.getLatitude());
        signIn.setLongitude(signInDTO.getLongitude());
        signIn.setGesture(signInDTO.getGesture());
        signIn.setCreateTime(LocalDateTime.now());
        int second = signInDTO.getMinutes() * 60 + signInDTO.getSecond();
        signIn.setEndTime(LocalDateTime.now().plusSeconds(second));
        int ret = signInMapper.insert(signIn);
        if (ret != 0){
            asyncStopSignIn.stopSignIn(signIn, second);
            return ReturnInfo.success("创建限时签到成功", signIn);
        }
        return ReturnInfo.error("创建限时签到失败");
    }

    @Override
    public ReturnInfo startClickSignIn(SignInDTO signInDTO) {
        SignIn signIn;
        signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", signInDTO.getCourseCode()).eq("status", 1));
        if (signIn != null){
            return ReturnInfo.error("当前课程还有正在进行的签到，不能发起新的签到");
        }
        signIn = new SignIn();
        signIn.setStatus(1);
        signIn.setCourseCode(signInDTO.getCourseCode());
        signIn.setLatitude(signInDTO.getLatitude());
        signIn.setLongitude(signInDTO.getLongitude());
        signIn.setGesture(signInDTO.getGesture());
        signIn.setCreateTime(LocalDateTime.now());
        int ret = signInMapper.insert(signIn);
        if (ret != 0){
            return ReturnInfo.success("创建一键签到成功", signIn);
        }
        return ReturnInfo.error("创建一键签到失败");
    }

    @Override
    public ReturnInfo stopClickSignIn(String course_code) {
        SignIn signIn;
        signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", course_code).eq("status", 1));
        if (signIn == null){
            return ReturnInfo.error("当前课程还有正在进行的签到，不能停止签到");
        }
        signIn.setStatus(0);
        signIn.setEndTime(LocalDateTime.now());
        int ret = signInMapper.updateById(signIn);
        if (ret != 0){
            return ReturnInfo.success("停止签到成功");
        }
        return ReturnInfo.error("停止签到失败");
    }

    @Transactional
    @Override
    public ReturnInfo getSignInInfoBySignInId(Integer sign_id) {
        SignIn signIn = signInMapper.selectById(sign_id);
        List<SignInfoDTO> inList = userMapper.getUserSignInCourse(signIn.getCourseCode(), sign_id);
        List<SignInfoDTO> outList = userMapper.getUserNotSignInCourse(signIn.getCourseCode(), sign_id);
        Map<String, List<SignInfoDTO>> res = new HashMap<>();
        res.put("signed", inList);
        res.put("unsigned", outList);
        return ReturnInfo.success("成功获取指定签到id的签到信息", res);
    }

    @Override
    public ReturnInfo resign(Integer sign_id, Integer student_id) {
        SignIn signin = signInMapper.selectById(sign_id);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("student_id", student_id));
        SysParam sysParam = sysParamMapper.selectOne(new QueryWrapper<SysParam>().eq("param_key", "score"));
        ParticipateInCourse participateInCourse =
                participateInCourseMapper.selectOne(
                        new QueryWrapper<ParticipateInCourse>().eq("user_id", user.getId())
                                .eq("course_code", signin.getCourseCode()));
        participateInCourse.setScore(participateInCourse.getScore() + Integer.parseInt(sysParam.getParamValue()));
        participateInCourseMapper.updateById(participateInCourse);
        SignLog signLog = new SignLog();
        signLog.setStudentId(student_id);
        signLog.setSignId(sign_id);
        signLog.setSignTime(new Date());
        signLog.setDistance(.0);
        signLogMapper.insert(signLog);
        return ReturnInfo.success("补签成功", signLog);
    }


    @Override
    public ReturnInfo getStudentSignInfoByCourseCode(String course_code, Integer student_id) {
        List<SignIn> signInList = signInMapper.getStudentSignInfoByCourseCode(course_code, student_id);
        List<SignIn> unSignInList = signInMapper.getStudentUnSignInfoByCourseCode(course_code, student_id);
        List<SignInHelper> res = new ArrayList<>();
        for (SignIn s : signInList){
            SignInHelper signInHelper = new SignInHelper();
            BeanUtil.copyProperties(s, signInHelper);
            signInHelper.setSigned("已签到");
            res.add(signInHelper);
        }
        for (SignIn s : unSignInList){
            SignInHelper signInHelper = new SignInHelper();
            BeanUtil.copyProperties(s, signInHelper);
            signInHelper.setSigned("未签到");
            res.add(signInHelper);
        }
        return ReturnInfo.success("成功获取学生指定班课的所有签到信息", res);
    }

    @Transactional
    @Override
    public ReturnInfo signIn(SignLogDTO signLogDTO) {
        SignIn signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", signLogDTO.getCourse_code())
                .eq("status", 1));
        if (signIn == null){
            return ReturnInfo.error("当前课程没有签到活动");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("student_id", signLogDTO.getStudent_id()));
        SignLog signLog;
        signLog = signLogMapper.selectOne(new QueryWrapper<SignLog>().eq("sign_id", signIn.getId()).eq("student_id", signLogDTO.getStudent_id()));
        if (signLog != null){
            return ReturnInfo.error("已签到");
        }
        SysParam sysParam = sysParamMapper.selectOne(new QueryWrapper<SysParam>().eq("param_key", "score"));
        ParticipateInCourse participateInCourse =
                participateInCourseMapper.selectOne(
                        new QueryWrapper<ParticipateInCourse>().eq("user_id", user.getId())
                            .eq("course_code", signLogDTO.getCourse_code()));
        participateInCourse.setScore(participateInCourse.getScore() + Integer.parseInt(sysParam.getParamValue()));
        participateInCourseMapper.updateById(participateInCourse);
        signLog = new SignLog();
        Double distance = DistanceUtil.GetDistance(signIn.getLatitude(), signIn.getLongitude(), signLogDTO.getLatitude(), signLogDTO.getLongitude());
        signLog.setStudentId(signLogDTO.getStudent_id());
        signLog.setSignId(signIn.getId());
        signLog.setLongitude(signLogDTO.getLongitude());
        signLog.setLatitude(signLogDTO.getLatitude());
        signLog.setSignTime(new Date());
        signLog.setDistance(distance);
        int ret = signLogMapper.insert(signLog);
        if (ret != 0){
            return ReturnInfo.success("签到成功");
        }
        return ReturnInfo.error("签到失败");
    }

    @Override
    public ReturnInfo getSignInfoForCourse(String course_code) {
        SignIn signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", course_code).eq("status", 1));
        if (signIn == null){
            return ReturnInfo.error("没有正在进行的签到");
        }
        List<SignInfoDTO> inList = userMapper.getUserSignInCourse(course_code, signIn.getId());
        List<SignInfoDTO> outList = userMapper.getUserNotSignInCourse(course_code, signIn.getId());
        Map<String, Object> res = new HashMap<>();
        res.put("sign_id", signIn.getId());
        res.put("signed", inList);
        res.put("unsigned", outList);
        return ReturnInfo.success("成功获取签到学生和未签到学生信息", res);
    }

    @Override
    public ReturnInfo hasSignIn(String course_code) {
        SignIn signIn = signInMapper.selectOne(new QueryWrapper<SignIn>().eq("course_code", course_code).eq("status", 1));
//        List<Integer> id = signInMapper.getCourseId(course_id);
        if (signIn == null){
            return ReturnInfo.error("没有正在进行的签到");
        }
        return ReturnInfo.success("有正在进行的签到", signIn);
    }

    @Override
    public ReturnInfo allSignIn(String course_code) {
        List<SignIn> signIns = signInMapper.getAllByCourseCode(course_code);
        List<SignVo> signVos = new ArrayList<>();
        int number = participateInCourseMapper.getCourseNumber(course_code);
        for (SignIn signIn : signIns){
            SignVo temp = new SignVo();
            BeanUtil.copyProperties(signIn, temp);
            temp.setSignCount(signLogMapper.getSignCountBySignId(temp.getId()));
            temp.setCourseNumber(number);
            signVos.add(temp);
        }
        return ReturnInfo.success(course_code+ ":的所有签到记录", signVos);
    }


}
