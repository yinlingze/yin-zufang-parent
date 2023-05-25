package org.example.service;

import org.example.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser>{

    List<HouseUser> findListByHouseId(Long id);

}
