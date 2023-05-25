package org.example.service;

import org.example.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker>{
    List<HouseBroker> findListByHouseId(Long id);
}
