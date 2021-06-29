package com.daoyun.demo.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.config.security.JwtTokenUtil;
import com.daoyun.demo.mapper.CourseMapper;
import com.daoyun.demo.mapper.ParticipateInCourseMapper;
import com.daoyun.demo.mapper.UserMapper;
import com.daoyun.demo.pojo.*;
import com.daoyun.demo.pojo.dto.RegisterDto;
import com.daoyun.demo.pojo.dto.UserInfoDTO;
import com.daoyun.demo.pojo.dto.UserPasswordDto;
import com.daoyun.demo.service.ISmsService;
import com.daoyun.demo.service.IUserService;
import com.daoyun.demo.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-06
 */
@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired(required = false)
    private CourseMapper courseMapper;

    @Autowired(required = false)
    private ParticipateInCourseMapper participateInCourseMapper;

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public ReturnInfo login(String username, String password, HttpServletRequest request) {
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        /**
         * 在注册的时候注册密码
         */
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return ReturnInfo.badrequest("用户名或密码不正确");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("SecurityContextHolder.getContext().getAuthentication():"+SecurityContextHolder.getContext().getAuthentication());
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        user.setLoginTime(LocalDateTime.now());
        userMapper.update(user, new QueryWrapper<User>().eq("username", username));
        String pwd = passwordEncoder.encode(password);
        this.userMapper.deleteLogin(username);
        this.userMapper.insertInLogin(username, pwd, LocalDateTime.now(), token);
        return ReturnInfo.success("登录成功", tokenMap);
    }


    @Autowired
    ISmsService iSmsService;

    /**
     * 验证码登录
     *
     * @param username
     * @param code
     * @param request
     * @return
     */
    @Override
    public ReturnInfo loginByCode(String username, String code, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails) {
            return ReturnInfo.badrequest("无此用户");
        }
        if (code != "") {
            int res = iSmsService.verifyCode(username, code);
            if (res == 1) {
                return ReturnInfo.badrequest("验证码错误");
            } else if (res == 2) {
                return ReturnInfo.badrequest("验证码失效");
            } else {
                //更新security登录用户对象
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());

                //生成token
                String token = jwtTokenUtil.generateToken(userDetails);
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                tokenMap.put("tokenHead", tokenHead);
                this.userMapper.deleteLogin(username);
                this.userMapper.insertInLoginByCode(username, LocalDateTime.now(), token);
                return ReturnInfo.success("登录成功", tokenMap);
            }
        } else {
            return ReturnInfo.badrequest("验证码为空");
        }
    }

    /**
     * 用户注册
     *
     * @param username
     * @param code
     * @param request
     * @return
     */
    @Override
    public ReturnInfo userRegister(String username, String code, Integer role, HttpServletRequest request) {
        //        User user = this.userDao.getUserByUsername(username);
        User isuser = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        int res = iSmsService.verifyCode(username, code);
        if (isuser != null) {
            return ReturnInfo.created("用户已存在");
        } else {
            if (res == 1) {
                return ReturnInfo.badrequest("验证码错误");
            } else if (res == 2) {
                return ReturnInfo.badrequest("验证码失效");
            } else {
                User user = new User();
                user.setUsername(username);
                if (LoginUtil.check(username).equals("phone")){
                    user.setPhone(username);
                }
                user.setRole(role);
                String pwd = passwordEncoder.encode("123456");
                user.setPassword(pwd);
                user.setCreationDate(LocalDateTime.now());
                user.setCreator(username);
                this.userMapper.insert(user);
                return ReturnInfo.success("注册成功");
            }
        }
    }

    @Override
    public ReturnInfo userRegisterByPsw(RegisterDto registerDto, HttpServletRequest request) {
        User isuser = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
//        int res = iSmsService.verifyCode(registerDto.getUsername(), registerDto.getCode());
        if (isuser != null) {
            return ReturnInfo.created("用户已存在");
        } else {
//            if (res == 1) {
//                return ReturnInfo.badrequest("验证码错误");
//            } else if (res == 2) {
//                return ReturnInfo.badrequest("验证码失效");
//            } else {
                User user = new User();
                user.setUsername(registerDto.getUsername());
                if (LoginUtil.check(registerDto.getUsername()).equals("phone")){
                    user.setPhone(registerDto.getUsername());
                }
                user.setRole(registerDto.getRole());
                String pwd = passwordEncoder.encode(registerDto.getPassword());
                user.setPassword(pwd);
                user.setCreationDate(LocalDateTime.now());
                user.setCreator(registerDto.getUsername());
                this.userMapper.insert(user);
                return ReturnInfo.success("注册成功");
//            }
        }
    }

    /**
     * 前端管理员账号直接添加用户
     *
     * @param registerDto
     * @return
     */
    @Override
    public ReturnInfo addUser(RegisterDto registerDto) {
        if (this.userMapper.selectOne(new QueryWrapper<User>().eq("username", registerDto.getUsername())) != null) {
            return ReturnInfo.created("用户存在");
        } else {
            User user = new User();
            user.setUsername(registerDto.getUsername());
            String pwd = passwordEncoder.encode("123456");
            user.setPassword(pwd);
            user.setRole(registerDto.getRole());
            user.setCreationDate(LocalDateTime.now());
            user.setCreator("rool");
            this.userMapper.insert(user);
            return ReturnInfo.success("添加成功");
        }
    }

    @Override
    public ReturnInfo changePassword(UserPasswordDto userPasswordDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPasswordDto.getUsername());


        if (!passwordEncoder.matches(userPasswordDto.getOldpassword(), userDetails.getPassword())) {

            return ReturnInfo.badrequest("原密码错误,修改失败");
        } else {
            String nowusername = userDetails.getUsername();
            User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", nowusername));
            String pwd = passwordEncoder.encode(userPasswordDto.getNewpassword());
            user.setPassword(pwd);
            user.setModificationDate(LocalDateTime.now());
            user.setModifier(userPasswordDto.getUsername());
            this.userMapper.update(user, new QueryWrapper<User>().eq("username", nowusername));
            return ReturnInfo.success("修改成功");
        }
    }

    @Override
    public ReturnInfo forgotPassword(String username, String code) {
        int res = iSmsService.verifyCode(username, code);
        if (res == 1) {
            return ReturnInfo.badrequest("验证码错误");
        } else if (res == 2) {
            return ReturnInfo.badrequest("验证码失效");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        user.setPassword(passwordEncoder.encode("123456"));
        int ret = userMapper.updateById(user);
        if (ret != 0){
            return ReturnInfo.success("密码重置为：123456, 请重新登录");
        }
        return ReturnInfo.error("密码重置失败，请重试");

    }

    @Override
    public ReturnInfo getUsersByRoleId(Integer role_id) {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("role", role_id));
        return ReturnInfo.success("成功获取指定角色用户", userList);
    }

    @Override
    public ReturnInfo getUserById(Integer id) {
        User user = this.userMapper.selectById(id);
        return ReturnInfo.success("获取成功", user);
    }

    @Override
    public ReturnInfo deleteUser(String username) {
        this.userMapper.delete(new QueryWrapper<User>().eq("username", username));
        return ReturnInfo.success("删除成功");
    }

    @Override
    public ReturnInfo getUserInfoByToken(HttpServletRequest request) {
        User user = this.userMapper.getUserInfoByToken(request.getHeader("Authorization"));
        user.setPassword("");
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtil.copyProperties(user, userInfoDTO);
        List<Course> courses = courseMapper.selectList(new QueryWrapper<Course>().eq("creator",user.getId()));
        List<ParticipateInCourse> participateInCourses =
                participateInCourseMapper.selectList(
                        new QueryWrapper<ParticipateInCourse>().eq("user_id", user.getId()));
        userInfoDTO.setCreate(courses.size());
        userInfoDTO.setJoin(participateInCourses.size());
        return ReturnInfo.success("用户信息获取成功", userInfoDTO);
    }

    @Override
    public ReturnInfo resetUserInfoByToken(UserInfo userInfo, HttpServletRequest request) {
        User user = this.userMapper.getUserInfoByToken(request.getHeader("Authorization"));
        System.out.println("user:" + user);

        if(null != userInfo.getSex()){
            user.setSex(userInfo.getSex());
        }
        if(null != userInfo.getStudentId()){
            user.setStudentId(userInfo.getStudentId());
        }
        if(null != userInfo.getRealname()){
            user.setRealname(userInfo.getRealname());
        }
//        if(null != userInfo.getPhone()){
//            user.setPhone(userInfo.getPhone());
//        }
        user.setRole(userInfo.getRole());

        if(null != userInfo.getOrg()){
            user.setOrg(userInfo.getOrg());
        }

        user.setBirthday(userInfo.getBirthday());
        this.userMapper.updateById(user);
        return ReturnInfo.success("更新成功",user);
    }



    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        return users;
    }
}
