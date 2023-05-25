package org.example.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.dao.BaseDao;
import org.example.dao.UserFollowDao;
import org.example.entity.UserFollow;
import org.example.service.DictService;
import org.example.service.UserFollowService;
import org.example.service.imp.BaseServiceImpl;
import org.example.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Reference
    private DictService dictService;
    @Autowired
    private UserFollowDao userFollowDao;

    @Override
    public BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    /**
     * 添加关注房源
     * @param userId
     * @param houseId
     */
    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.save(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouserId(userId, houseId);
        if(count.intValue() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);
        List<UserFollowVo> list = page.getResult();
        //System.out.println("dictService"+dictService);
        for(UserFollowVo userFollowVo : list) {
            String name = dictService.getById(userFollowVo.getHouseTypeId()).getName();
            String name1 = dictService.getById(userFollowVo.getFloorId()).getName();
            String name2 = dictService.getById(userFollowVo.getDirectionId()).getName();
            //户型：
            String houseTypeName = name;
            //楼层
            String floorName = name1;
            //朝向：
            String directionName = name2;
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page, 5);
    }

    /**
     * 删除我的关注
     * @param id
     */
    @Override
    public void cancelFollow(Long id) {

        userFollowDao.delectById(id);
    }
}
