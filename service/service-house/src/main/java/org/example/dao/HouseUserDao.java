package org.example.dao;

import org.example.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {
    //根据房源id查询房东
    List<HouseUser> findListByHouseId(Long houseId);


}
