package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.*;
import com.example.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController extends BaseController{

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;

    private final static String LIST_ACTION = "redirect:/house";
    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SHOW = "house/show";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    //分页及带条件查询的方法
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        //获取请求数据
        Map<String,Object> filters = getFilters(request);
        //获取分页数据
        PageInfo<House> page = houseService.findPage(filters);
        //将filters放入请求域中
        model.addAttribute("filters" ,filters);
        model.addAttribute("page" ,page);
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList",dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findListByDictCode("direction"));
        model.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));

        return PAGE_INDEX;
    }

    //去修改页面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap model){
        setRequest(model);
        return PAGE_EDIT;
    }
    //更新
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    //去增加页面
    @RequestMapping("/create")
    public String create(ModelMap model){
        setRequest(model);
        return PAGE_CREATE;
    }
    //保存
    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return PAGE_SUCCESS;
    }
    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

    //发布房源和取消发布
    @GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        //调用houseService
        houseService.publish(id, status);
        return LIST_ACTION;
    }

    //查看房源详情
    @RequestMapping("/{houseId}")
    public String show(ModelMap model,@PathVariable("houseId")Long houseId) {
        //获取房源信息
        House house = houseService.getById(houseId);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取房源经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(houseId);
        //获取房源房东信息
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(houseId);
        //获取房源图片
        List<HouseImage> houseImage1List = houseImageService.findList(houseId, 1);
        //获取房产图片
        List<HouseImage> houseImage2List = houseImageService.findList(houseId, 2);
        model.addAttribute("house",house);
        model.addAttribute("community",community);
        model.addAttribute("houseBrokerList",houseBrokerList);
        model.addAttribute("houseUserList",houseUserList);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        return PAGE_SHOW;
    }





    //将所有小区以及数据字典的数据放到request中
    public void setRequest(ModelMap model){
        //获取所有小区
        List<Community> communityList = communityService.findAll();
        //获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //获取所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //获取所有建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //获取所有朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        model.addAttribute("communityList",communityList);
        model.addAttribute("houseTypeList",houseTypeList);
        model.addAttribute("floorList",floorList);
        model.addAttribute("buildStructureList",buildStructureList);
        model.addAttribute("directionList",directionList);
        model.addAttribute("decorationList",decorationList);
        model.addAttribute("houseUseList",houseUseList);
    }





}
