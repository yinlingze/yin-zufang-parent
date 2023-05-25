package org.example.config;

import com.alibaba.dubbo.config.annotation.Reference;

import org.example.entity.Admin;
import org.example.service.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    //登录时，SpringService会自动调用该方法，并将用户名传入改方法中
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用AdminService中的方法查询Admin
        Admin admin = adminService.getByUsername(username);
        if(null == admin) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //获取用户权限列表
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        //创建授权的集合
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //遍历得到每一个权限嘛
        for (String permissionCode : codeList) {
            if(StringUtils.isEmpty(permissionCode)) continue;
            //创建GrantedAuthority对象
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionCode);
            //将SimpleGrantedAuthority对象放入集合中
            authorities.add(authority);
        }
        /*
        给用户授权
        权限有两种表示方式
        1、通过角色的方式表示，例如：ROLE_ADMIN
        2、直接设置权限，例如：Delete、Query、Update
         */
        return new User(username,admin.getPassword(), authorities);
    }

}
