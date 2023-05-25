package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.BaseDao;
import org.example.dao.HouseBrokerDao;
import org.example.entity.HouseBroker;
import org.example.service.HouseBrokerService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    HouseBrokerDao houseBrokerDao;

    @Override
    public BaseDao<HouseBroker> getEntityDao() {

        return houseBrokerDao;
    }

    public List<HouseBroker> findListByHouseId(Long id){
        return houseBrokerDao.findListByHouseId(id);
    }


}
