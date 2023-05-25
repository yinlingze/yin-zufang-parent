package org.example.service;

import org.example.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community>{
    List<Community> findAll();
}

