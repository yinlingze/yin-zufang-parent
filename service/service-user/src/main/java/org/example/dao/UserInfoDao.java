package org.example.dao;


import org.example.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo> {
    //根据手机号查询用户
    UserInfo getByPhone(String phone);

}
