package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.BaseDao;
import org.example.dao.HouseImageDao;
import org.example.entity.HouseImage;
import org.example.service.HouseImageService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageDao houseImageDao;
    @Override
    public BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    //根据房源id和房源类型查询房产图片
    @Override
    public List<HouseImage> findList(Long houseId, Integer type) {
        return houseImageDao.findList(houseId,type);
    }
}
