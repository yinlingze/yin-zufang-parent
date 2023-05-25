package org.example.dao;


import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.example.entity.UserFollow;
import org.example.vo.UserFollowVo;

public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer countByUserIdAndHouserId(@Param("userId")Long userId, @Param("houseId")Long houseId);
    Page<UserFollowVo> findListPage(@Param("userId")Long userId);
}
