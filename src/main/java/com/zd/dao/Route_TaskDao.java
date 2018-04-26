package com.zd.dao;

import com.zd.object.ScanDataListEnt;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * 数据保存到 Route_Task 表的存储代码;
 * 逻辑写在这里,启用外部事务;
 */
public class Route_TaskDao {
    public boolean save_data( ScanDataListEnt dataEnt, SqlSession sen ) {
        boolean saveResult = true;
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            // 查询SQL
            String SQL = "select count(1) from Route_Task " +
                    "where Task_Code = #{Task_Code}";
            // 查询项目列表:
            Map<String, Object> rlt_one = sqlMapper.selectOne(SQL);

            // 检查有无,没有则插入一条,有则update.


        } catch (Exception ex) {

            saveResult = false;
        }
        return saveResult;
    }
}
