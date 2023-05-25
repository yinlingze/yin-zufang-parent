package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.dao.BaseDao;
import org.example.dao.DictMapper;
import org.example.dao.HouseMapper;
import org.example.entity.House;
import org.example.service.BaseService;
import org.example.service.DictService;
import org.example.service.HouseService;
import org.example.service.imp.BaseServiceImpl;
import org.example.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;

@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private DictMapper dictDao;

    @Override
    public BaseDao<House> getEntityDao() {
        return houseMapper;
    }

    @Override
    public House getById(Serializable id) {
//        System.out.println("=====");
        House house = houseMapper.findById(id);
        if(null == house) return null;
        //户型：
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //建筑结构：
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //朝向：
        String directionName = dictDao.getNameById(house.getDirectionId());
        //装修情况：
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //房屋用途：
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);
        return house;
    }


}
