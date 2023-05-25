package org.example.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import org.example.entity.UserInfo;
import org.example.result.Result;
import org.example.result.ResultCodeEnum;
import org.example.service.UserInfoService;
import org.example.util.MD5;
import org.example.vo.LoginVo;
import org.example.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/userInfo")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;
    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    @RequestMapping ("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request){
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //验证是否有空
        if(StringUtils.isEmpty(nickName)
           || StringUtils.isEmpty(phone)
           || StringUtils.isEmpty(password)
           || StringUtils.isEmpty(code)
        ){
            //返回参数错误
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //验证验证码是否正确
        String currentCode = (String)request.getSession().getAttribute("CODE");
        if(!code.equals(currentCode)) {
            //放回验证码错误
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //验证手机号是否已经注册
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null != userInfo){
            //放回手机号已经注册错误
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //创建UserInfo对象，放入数据库中
        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.save(userInfo);
        return Result.ok();

    }

    /**
     * 获取验证码
     * @param moble
     * @param request
     * @return
     */
    @RequestMapping("/sendCode/{moble}")
    public Result sendCode(@PathVariable String moble, HttpServletRequest request) {
        //设置验证码
        String code = "1111";
        //放入session中

        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

    /**
     * 登录
     * @param loginVo
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request){
        //获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //验证为空
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getByPhone(phone);
        //检查账号是否存在
        if(null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //检查密码是否正确
        if(!MD5.encrypt(password).equals(userInfo.getPassword())){
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //检查用户是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //将用户放在session中,便于过滤器判断用户是否登录
        request.getSession().setAttribute("USER", userInfo);

        //回显用户名和电话
        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);

    }


    /**
     * 退出登录，从session删除用户信息
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.ok();
    }


}
