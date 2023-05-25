package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.Role;
import com.example.service.PermissionService;
import com.example.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;
    private final static String PAGE_CREATE = "role/create";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_ASSIGN_SHOW = "role/assignShow";

    private final static String PAGE_AUTH     = "frame/auth";


//    @RequestMapping
//    public String index(Map map){
//        //调用RoleService中获取所有角色的方法
//        List<Role> all = roleService.findAll();
//        map.put("list",all);
//        return "role/index";
//    }
    //只有查出来的用户权限中有role.show这个编码的用户才能调用此方法
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping
    public String index(ModelMap model , HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //获取分页带条件查询的方法
        PageInfo<Role> page = roleService.findPage(filters);

        model.addAttribute("page", page);
        //将filters放到request域中
        model.addAttribute("filters", filters);
        return "role/index";
    }


    /**
     * 去新增页面
     * @return
     */
    @PreAuthorize("hasAuthority('role.create')")
    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param role
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public String save(Role role, HttpServletRequest request){
        roleService.insert(role);
        //重定向到查询所有角色的方法
        //return "redirect:/role";
        //去成功页面
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAuthority('role.delete')")
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        //调用删除方法
        roleService.delete(roleId);
        //重定向
        return "redirect:/role";
    }

    /**
     * 去修改页面
     * @param roleId
     * @param map
     * @return
     */
    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable("roleId") Long roleId , Map map){
        //调用RoleService中根据id查询方法
        Role role = roleService.getById(roleId);
        //将查询到的ROle放到请求域中
        map.put("role",role);
        //去修改页面
        return "role/edit";
    }

    /**
     * 保存更新
     * @param role
     * @return
     */
    @RequestMapping("/update")
    public String update(Role role) {
        roleService.update(role);
        return PAGE_SUCCESS;
    }

    /**
     * 进入分配权限页面
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAuthority('role.assgin')")
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId") Long roleId,Map map){
        //获取角色权限
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        map.put("zNodes",zNodes);
        map.put("roleId",roleId);

        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 保存角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PreAuthorize("hasAuthority('role.assgin')")
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,@RequestParam("permissionIds") Long[] permissionIds){
        permissionService.saveRolePermissionRealtionShip(roleId,permissionIds);
        return PAGE_SUCCESS;
    }

    /**
     * 去自定义的权限不够页面
     * @return
     */
    @GetMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }





}
