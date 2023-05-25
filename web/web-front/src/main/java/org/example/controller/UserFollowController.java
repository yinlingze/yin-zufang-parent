package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.entity.UserInfo;
import org.example.result.Result;
import org.example.service.UserFollowService;
import org.example.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/userFollow")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    /**
     * 关注房源,添加到我的关注
     * @param houseId
     * @param request
     * @return
     */
    @GetMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request){
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        userFollowService.follow(userId, houseId);
        return Result.ok();
    }

    /**
     * 展示我的关注
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping(value = "/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpServletRequest request) {
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
        return Result.ok(pageInfo);
    }

    /**
     * 删除我的关注
     * @param id
     * @param request
     * @return
     */
    @GetMapping("auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id, HttpServletRequest request){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }

}
