package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.entity.HouseUser;
import org.example.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/houseUser")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseUserController extends BaseController{


    @Reference
    private HouseUserService houseUserService;

    private final static String LIST_ACTION = "redirect:/house/";

    private final static String PAGE_CREATE = "houseUser/create";
    private final static String PAGE_EDIT = "houseUser/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    //去修改页面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,ModelMap model){
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    //保存修改
    @PostMapping(value="/update")
    public String update(HouseUser houseUser){
        houseUserService.reSet(houseUser);
        return PAGE_SUCCESS;
    }

    //去增加页面
    @GetMapping("/create")
    public String create(ModelMap model,@RequestParam("houseId") Long houseId) {
        model.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }

    //保存增加
    @PostMapping("/save")
    public String save(HouseUser houseUser){
        houseUserService.save(houseUser);
        return PAGE_SUCCESS;
    }

    //删除
    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseUserService.delectById(id);
        return LIST_ACTION+ houseId;
    }
}
