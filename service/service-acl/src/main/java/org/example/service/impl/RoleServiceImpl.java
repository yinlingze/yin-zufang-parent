package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.BaseDao;
import org.example.dao.RoleMapper;
import org.example.entity.Role;
import org.example.service.RoleService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

   @Autowired
   private RoleMapper roleMapper;
   @Override
   public BaseDao<Role> getEntityDao() {
      return  roleMapper;
   }
}