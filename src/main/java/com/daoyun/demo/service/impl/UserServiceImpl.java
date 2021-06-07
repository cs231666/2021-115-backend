package com.daoyun.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.config.security.JwtTokenUtil;
import com.daoyun.demo.mapper.UserMapper;
import com.daoyun.demo.pojo.Login;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.RegisterDto;
import com.daoyun.demo.pojo.dto.UserPasswordDto;
import com.daoyun.demo.service.ISmsService;
import com.daoyun.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
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

    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public ReturnInfo login(String username, String password, HttpServletRequest request) {
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails);
        /**
         * 在注册的时候注册密码
         */
        System.out.println("123:"+passwordEncoder.matches(password,userDetails.getPassword()));
            if(userDetails==null || !passwordEncoder.matches(password,userDetails.getPassword())){
                return ReturnInfo.badrequest("用户名或密码不正确");
        }


        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        user.setLoginTime(LocalDateTime.now());
        userMapper.update(user,new QueryWrapper<User>().eq("username",username));
        String pwd = passwordEncoder.encode(password);
        this.userMapper.deleteLogin(username);
        this.userMapper.insertInLogin(username,pwd,LocalDateTime.now(),token);
        return ReturnInfo.success("登录成功",tokenMap);
    }


    @Autowired
    ISmsService iSmsService;
    /**
     * 验证码登录
     * @param username
     * @param code
     * @param request
     * @return
     */
    @Override
    public ReturnInfo loginByCode(String username, String code, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails){
            return ReturnInfo.badrequest("无此用户");
        }
        if(code != ""){
            int res=iSmsService.verifyCode(username, code);
            if(res == 1){
                return ReturnInfo.badrequest("验证码错误");
            }else if(res == 2){
                return ReturnInfo.badrequest("验证码失效");
            }else {
                //更新security登录用户对象
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());

                //生成token
                String token = jwtTokenUtil.generateToken(userDetails);
                Map<String,String> tokenMap = new HashMap<>();
                tokenMap.put("token",token);
                tokenMap.put("tokenHead",tokenHead);
                this.userMapper.deleteLogin(username);
                this.userMapper.insertInLoginByCode(username,LocalDateTime.now(),token);
                return ReturnInfo.success("登录成功",tokenMap);
            }
        }else{
            return ReturnInfo.badrequest("验证码为空");
        }
    }

    /**
     * 用户注册
     * @param username
     * @param code
     * @param request
     * @return
     */
    @Override
    public ReturnInfo userRegister(String username, String code, Integer role, HttpServletRequest request) {
        //        User user = this.userDao.getUserByUsername(username);
        User isuser = this.userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        int res=iSmsService.verifyCode(username, code);
        if (isuser != null) {
            return ReturnInfo.created("用户已存在");
        } else {
            if(res == 1){
                return ReturnInfo.badrequest("验证码错误");

            }else if(res == 2){
                return ReturnInfo.badrequest("验证码失效");
            }else{
                User user = new User();
                user.setUsername(username);
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
        User isuser = this.userMapper.selectOne(new QueryWrapper<User>().eq("username",registerDto.getUsername()));
        int res=iSmsService.verifyCode(registerDto.getUsername(), registerDto.getCode());
        if (isuser != null) {
            return ReturnInfo.created("用户已存在");
        } else {
            if(res == 1){
                return ReturnInfo.badrequest("验证码错误");
            }else if(res == 2){
                return ReturnInfo.badrequest("验证码失效");
            }else{
                User user = new User();
                user.setUsername(registerDto.getUsername());
                user.setRole(registerDto.getRole());
                String pwd = passwordEncoder.encode(registerDto.getPassword());
                user.setPassword(pwd);
                user.setCreationDate(LocalDateTime.now());
                user.setCreator(registerDto.getUsername());
                this.userMapper.insert(user);
                return ReturnInfo.success("注册成功");
            }
        }
    }

    /**
     * 前端管理员账号直接添加用户
     * @param registerDto
     * @return
     */
    @Override
    public ReturnInfo addUser(RegisterDto registerDto) {
        if(this.userMapper.selectOne(new QueryWrapper<User>().eq("username",registerDto.getUsername())) != null){
            return ReturnInfo.created("用户存在");
        }else {
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

//        System.out.println("oldpsw:"+ userPasswordDto.getOldpassword());
//        System.out.println("newpsw:"+ userPasswordDto.getOldpassword());
//        System.out.println(passwordEncoder.matches(userPasswordDto.getOldpassword(),userDetails.getPassword()));
        if (!passwordEncoder.matches(userPasswordDto.getOldpassword(),userDetails.getPassword())) {

        return ReturnInfo.badrequest("原密码错误,修改失败");
        }else {
            String nowusername = userDetails.getUsername();
            User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username", nowusername));
            String pwd = passwordEncoder.encode(userPasswordDto.getNewpassword());
            user.setPassword(pwd);
            user.setModificationDate(LocalDateTime.now());
            user.setModifier(userPasswordDto.getUsername());
            this.userMapper.update(user,new QueryWrapper<User>().eq("username",nowusername));
            return ReturnInfo.success("修改成功");
        }
    }

    @Override
    public ReturnInfo getUserById(Integer id) {
        User user = this.userMapper.selectById(id);
        return  ReturnInfo.success("获取成功", user);
    }

    @Override
    public ReturnInfo deleteUser(String username) {
        this.userMapper.delete(new QueryWrapper<User>().eq("username",username));
        return ReturnInfo.success("删除成功");
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        return  users;
    }
}
