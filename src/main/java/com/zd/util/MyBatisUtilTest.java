package com.zd.util;
import com.zd.object.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.util.List;

/**
 *
 */
public class MyBatisUtilTest {
    @Test
    public void testQueryUsers() throws Exception {

        // 获取SqlSession对象
        SqlSession session = MyBatisUtil.getSqlSession();
        // 查询全部对象
        List<User> list = session.selectList("com.zd.mapper.queryUsers");
        System.out.println(list.size());
        //关闭SqlSession对象
        MyBatisUtil.closeSqlSession(session);

    }


}