package com.zd.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 工具类
 */
public class MyBatisUtil {
    //创建SqlSessionFactory对象
    private static SqlSessionFactory factory;
    private static Logger logger = LoggerFactory.getLogger(MyBatisUtil.class);

    static {
        try {
            //获取配置文件资源
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            //获取SqlSessionFactory对象
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取SqlSession对象
     */
    public static SqlSession getSqlSession() {
        return factory.openSession();
    }

    /**
     * 获取SqlSession对象- 控制事务提交方式
     */
    public static SqlSession getSqlSession( boolean autoCommit ) {
        return factory.openSession(autoCommit);
    }

    /**
     * 关闭SqlSession对象
     */
    public static void closeSqlSession( SqlSession session ) {
        if (null != session) {
            //关闭Sqlsession对象
            session.close();
        }
        session = null;
    }

}