package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.BaseDao;
import org.example.dao.HouseUserDao;
import org.example.entity.HouseUser;
import org.example.service.HouseUserService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {


    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    public BaseDao<HouseUser> getEntityDao() {

        return houseUserDao;

    }

    public List<HouseUser> findListByHouseId(Long id){
        return houseUserDao.findListByHouseId(id);
    }
}
