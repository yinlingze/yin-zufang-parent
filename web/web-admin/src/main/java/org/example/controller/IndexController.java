package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.Admin;
import com.example.entity.Permission;
import com.example.service.AdminService;
import com.example.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;
    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";

    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        //后续替换为当前登录用户id
//        Long adminId = 1L;
//        Admin admin = adminService.getById(adminId);
        //通过SpringSecurity获取user对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        //调用AdminService中的方法获取admin对象
        Admin admin = adminService.getByUsername(user.getUsername());

        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {

        return PAGE_MAIN;
    }
    @RequestMapping("/login")
    public String login(){
        return "frame/login.html";
    }


    /**
     * 获取当前登录信息
     * @return
     */
    @GetMapping("getInfo")
    @ResponseBody
    public Object getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }





}
