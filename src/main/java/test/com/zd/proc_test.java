package test.com.zd;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import com.zd.util.MyBatisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class proc_test {
    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【proc_test-ERR】-";

    //添加一个日志器
    private static final Logger log = LoggerFactory.getLogger(proc_test.class);

    @Before
    public void before() {
        System.out.println("proc_test.before()");
    }

    /*
        存储过程调用测试:
     */
    @Test
    public void test_proc_call() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
//            //创建sqlMapper
//            SqlMapper sqlMapper = new SqlMapper(sqlSession);

            // user_id,node_id,out_success,out_code,out_message
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("user_id", "F3584E1A-F008-4C61-BAAE-3EA2CA16A77D");
            paramMap.put("node_id", "997D8CF9-F3C8-431C-8B35-CAC7327ADC86");
            paramMap.put("out_success", -1);
            paramMap.put("out_code", -1);
            paramMap.put("out_message", " ");

            final List<Map<String, Object>> maps = sqlSession.selectList("app_getloadcar_tastlist", paramMap);
            String out_success = paramMap.get("out_success").toString();
            String out_code = paramMap.get("out_code").toString();
            String out_message = paramMap.get("out_message").toString();

            System.out.println("out_success=" + out_success);
            System.out.println("out_code=" + out_code);
            System.out.println("out_message=" + out_message);
            System.out.println(maps.toString());
            /**
             * 输出结果:
             * out_success=-1
             out_code=-1
             out_message=
             [{typestr=binidpoint, task_name=111, task_code=SHTFea170201-001002T.YARD01001, id=BB8E9434-606A-4B46-AC5B-37C8844B9587, route_points_id=, id_1=},
             {typestr=binidpoint, task_name=1, task_code=xl01170102-001asd1112302002, id=328A2610-71C7-42DC-A61B-DA48E1C0F016, route_points_id=, id_1=}]
             */

        } catch (Exception ex) {
//            log.error(ex.getMessage());
            System.out.println("Exception=" + ex.getMessage());
        } finally {

            sqlSession.close();
        }
    }


}
