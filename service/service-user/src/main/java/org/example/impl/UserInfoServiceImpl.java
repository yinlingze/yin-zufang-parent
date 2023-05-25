package org.example.impl;

import com.alibaba.dubbo.config.annotation.Service;

import org.example.dao.BaseDao;
import org.example.dao.UserInfoDao;
import org.example.entity.UserInfo;
import org.example.service.UserInfoService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = UserInfoService.class)
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    //根据手机号查询用户
    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoDao.getByPhone(phone);
    }
}
