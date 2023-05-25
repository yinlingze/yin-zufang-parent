package org.example.service;


import com.github.pagehelper.PageInfo;
import org.example.entity.UserFollow;
import org.example.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {

    /**
     * 用户关注房源 添加
     * @param userId
     * @param houseId
     */
    void follow(Long userId, Long houseId);

    /**
     * 用户是否关注房源
     * @param userId
     * @param houseId
     * @return
     */
    Boolean isFollowed(Long userId, Long houseId);


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);


    /**
     * 取消关注
     * @param id
     * @return
     */
    void cancelFollow(Long id);
}
