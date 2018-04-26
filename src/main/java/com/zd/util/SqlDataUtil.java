package com.zd.util;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;


/**
 * Created by zhangxiusheng on 17/3/24.
 * 执行SQL,返回数据集 -- json或者xml格式的;
 * <p/>
 * MyBatis直接执行SQL的工具SqlMapper
 * https://my.oschina.net/flags/blog/385126
 */
public class SqlDataUtil {

    private static Logger logger = LoggerFactory.getLogger(SqlDataUtil.class);

    @Test
    public void get_sql_result() {
        ObjectMapper objectMapper = new ObjectMapper();
        SqlSession sen = MyBatisUtil.getSqlSession();
        String rltJson = "";
        try {
            // 项目路径选择 SQL
            String sql = "select b.id, b.project_name , b.project_code " +
                    "from com_userorganizationinfo a join project b on a.ID_Organization = b.ID;";
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);
            final List<Map<String, Object>> maps = sqlMapper.selectList(sql);

            rltJson = objectMapper.writeValueAsString(maps);
            logger.info(rltJson);
            System.out.println(rltJson);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }

    }
}
