package org.example.dao;

import org.example.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageDao extends BaseDao<HouseImage>{
    //根据房源id和房源类型查询房产图片
    List<HouseImage> findList(@Param("houseId") Long houseId,@Param("type") Integer type);

}
