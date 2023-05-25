package org.example.service;


import org.example.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    //根据手机号查询用户
    UserInfo getByPhone(String phone);
}
