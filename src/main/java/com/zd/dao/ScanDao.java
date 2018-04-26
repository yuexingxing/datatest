package com.zd.dao;

import com.zd.object.ScanDataListEnt;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * 扫描数据头部保存
 */
public class ScanDao {

    public boolean save_data( ScanDataListEnt dataEnt, SqlSession sen ) {

        boolean saveResult = true;
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            // 查询SQL
            String SQL = "select count(1) from scan " +
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
