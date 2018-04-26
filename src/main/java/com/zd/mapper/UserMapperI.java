package com.zd.mapper;

/**
 * Created by zhangxiusheng on 17/3/23.
 * 参考文档:
 * http://elim.iteye.com/blog/1841033
 */

import com.zd.object.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 接口加注解的方式定义映射
 */
public interface UserMapperI {

    @Insert("insert into User(userName, password, trueName, email, phone, sex, group_id) " +
            "values(#{userName}, #{password}, #{trueName}, #{email}, #{phone}, #{sex}, #{group_id})")
    public void insertUser( User user );

    @Update("update User set userName = #{userName}, password = #{password}, trueName = #{trueName}, email = #{email}, phone = #{phone}, sex = #{sex}, group_id = #{group_id}" +
            " where id=#{id}")
    public void updateUser( User user );

    @Select(" SELECT id, userName, password, trueName, email, phone, sex, group_id FROM User where id=#{id}")
    public User findById( int id );

    @Delete("delete from User where id=#{id}")
    public void deleteUser( int id );

}