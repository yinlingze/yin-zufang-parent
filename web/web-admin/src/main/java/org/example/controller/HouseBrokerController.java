package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.Admin;
import com.example.entity.HouseBroker;
import com.example.service.AdminService;
import com.example.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/houseBroker")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseBrokerController {
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private AdminService adminService;
    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_CREATE = "houseBroker/create";
    private final static String PAGE_EDIT = "houseBroker/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    //进入新增页面
    @RequestMapping("/create")
    public String create(ModelMap model, @RequestParam("houseId") Long houseId){
        //查出所有管理员
        List<Admin> adminList = adminService.findAll();
        //放入请求域中
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }
    //保存新增
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        //根据经纪人id获得管理人信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //根据补全信息
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return PAGE_SUCCESS;
    }

    //进入修改页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,ModelMap model){
        //根据房源id得到房源经纪人信息
        List<HouseBroker> houseBroker = houseBrokerService.findListByHouseId(id);
        //查询所有管理员（经纪人）
        List<Admin> adminList = adminService.findAll();
        model.addAttribute(houseBroker);
        model.addAttribute(adminList);
        return PAGE_EDIT;
    }

    //保存修改
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        //根据id获取经纪人全部信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return PAGE_SUCCESS;
    }

    //删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable("id") Long id){
        houseBrokerService.delete(id);
        return LIST_ACTION + houseId;
    }


}
