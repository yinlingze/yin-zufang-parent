package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.AdminMapper;
import org.example.dao.BaseDao;
import org.example.entity.Admin;
import org.example.service.AdminService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public BaseDao<Admin> getEntityDao() {
        return adminMapper;
    }
}
