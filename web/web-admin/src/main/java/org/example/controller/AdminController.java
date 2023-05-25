package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.service.RoleService;
import com.example.until.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value="/admin")
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdminController extends BaseController{
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";



    /**
     * 列表
     * @param map
     * @param request
     * @return
     */
    //分页带条件查询
    @RequestMapping
    public String findPage(Map map , HttpServletRequest request){
        //获取前端分页要求数据
        Map<String, Object> filters = getFilters(request);
        //根据要求查询数据
        PageInfo<Admin> page = adminService.findPage(filters);
        //将数据反馈
        map.put("page",page);
        map.put("filters",filters);

        return PAGE_INDEX;
    }

    /**
     * 进入新增页面
     * @return
     */
    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param admin
     * @return
     */
    @PostMapping("/save")
    public String save(Admin admin){
        //加密密码
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //设置默认头像
        admin.setHeadUrl("static/img/userdefult.png");
        adminService.insert(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 进入编辑页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") Long id){
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param admin
     * @return
     */
    @PostMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/detele/{id}")
    public String delete(@PathVariable("id") Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 去上传头像页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/uploadShow/{id}")
    public String uploadShow(ModelMap model,@PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        byte[] bytes = file.getBytes();
        //通过UUID随机生成字符串作为上传到七牛云图片的名字
        String newFileName = UUID.randomUUID().toString();
        //使用七牛云工具类上传图片
        QiniuUtils.upload2Qiniu(bytes,newFileName);
        //图片在七牛云上的名字
        String url= "http://rl26xnrxk.hn-bkt.clouddn.com/"+ newFileName;
        //创建admin对象
        Admin admin= adminService.getById(id);
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        return PAGE_SUCCESS;

    }

    /**
     * 去分配角色的页面
     * @param model
     * @param adminId
     * @return
     */
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap model,@PathVariable Long adminId) {
        //获得用户已经分配和未分配的角色数据
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        //将map数据以键值对的方式放入请求域中
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId",adminId);
        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 根据用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return PAGE_SUCCESS;
    }

}
