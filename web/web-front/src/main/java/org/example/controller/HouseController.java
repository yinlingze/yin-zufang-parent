package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.example.entity.*;
import org.example.result.Result;
import org.example.service.*;
import org.example.vo.HouseQueryVo;
import org.example.vo.HouseVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private UserInfoService userInfoService;
    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNum , @PathVariable("pageSize") Integer pageSize, @RequestBody HouseQueryVo houseQueryVo){
        //调用HouseService中前端分页及带条件查询的方法
//        PageInfo<HouseVo> pageInfo = houseService.findPage(pageNum,pageSize,houseQueryVo);

        return Result.ok();
    }
    @GetMapping("info/{id}")
    public Result info(@PathVariable Long id,HttpServletRequest request) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Boolean isFollow = false;
        if(userInfo != null){
            Long userId = userInfo.getId();
            isFollow =userFollowService.isFollowed(userId,id);
        }
        //关注业务后续补充，当前默认返回未关注
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }



}
