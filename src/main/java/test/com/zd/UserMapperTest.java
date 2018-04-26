package test.com.zd;

import com.zd.object.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import com.zd.util.MyBatisUtil;


/**
 * 基于注解的单表映射
 */
public class UserMapperTest {

    @Before
    public void before() {
        System.out.println("UserMapperTest.before()");
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            User user = new User();
            user.setGroup(2);
            user.setEmail("AA12@126.com");
            user.setPassword("123321");
            user.setPhone("13190980072");
            user.setSex("0");
            user.setTrueName("真张三");
            user.setUserName("张三");
            sqlSession.insert("com.zd.mapper.UserMapperI.insertUser", user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            User user = new User();
            user.setId(1);//主键
            user.setGroup(2);
            user.setEmail("AA12@126.com");
            user.setPassword("123321");
            user.setPhone("13190980072");
            user.setSex("0");
            user.setTrueName("真张三");
            user.setUserName("张三");
            sqlSession.update("com.zd.mapper.UserMapperI.updateUser", user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFind() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            User user = sqlSession.selectOne("com.zd.mapper.UserMapperI.findById", 1);
            System.out.println(user.getId() + "--" + user.getUserName() + "--" + user.getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDelele() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            sqlSession.delete("com.zd.mapper.UserMapperI.deleteUser", 3);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
