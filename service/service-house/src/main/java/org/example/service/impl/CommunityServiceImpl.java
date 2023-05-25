package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.dao.BaseDao;
import org.example.dao.CommunityMapper;
import org.example.dao.DictMapper;
import org.example.entity.Community;
import org.example.service.CommunityService;
import org.example.service.imp.BaseServiceImpl;
import org.example.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private DictMapper dictDao;

    @Override
    public BaseDao<Community> getEntityDao() {
        return communityMapper;
    }
    @Override
    public PageInfo findPage(Map filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);

        List<Community> page = communityMapper.findPage(filters);



        for (Community community : page) {
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);

         }

        return new PageInfo<Community>(page, 10);
    }


    @Override
    public List<Community> findAll() {

        return communityMapper.findAll();
    }
}
