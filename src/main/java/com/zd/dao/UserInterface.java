package com.zd.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by zhangxiusheng on 17/3/25.
 */
@Repository
public interface UserInterface {
    public String getUserInfo(String userName,String userPwd);
}
