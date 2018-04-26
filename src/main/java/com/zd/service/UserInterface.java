package com.zd.service;

import com.zd.object.ResultInfo;

/**
 * Created by zhangxiusheng on 17/3/25.
 */
public interface UserInterface {

    public ResultInfo login( String UserName, String UserPwd );

    public ResultInfo update_password(String UserName, String UserPwd, String NewUserPwd);
}
