package org.example.service;

import org.example.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findList(Long houseId, Integer type);
}
