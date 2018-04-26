package com.zd.service;

import com.zd.object.ResultInfo;
import com.zd.object.UserRouteRequest;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 路径列表，条件：用户ID、项目ID
 */
@Service("routeService")
public class RouteServiceImpl implements RouteInterface {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【ProjectServiceImpl-ERR】-";

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    /**
     * 获取route列表..
     * -- 路由列表:
     * select a.id , -- 路径主键ID
     * a.route_name -- 路径名称
     * from route a join route_user b
     * on a.id = b.route_id and b.user_id = 'B37D2876-4821-4E22-AB95-E37E918FE0B0'
     * and a.gc_flag=0 and b.gc_flag = 0 and a.project_id = '8119B0DD-89D0-4F56-BC26-2300CFB742E6';
     */
    public ResultInfo getRouteList( UserRouteRequest userrouteReq ) {
        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            UserRouteRequest user = new UserRouteRequest();
            user.setUser_id(userrouteReq.getUser_id());
            user.setProject_id(userrouteReq.getProject_id());

            // 查询SQL(用户ID、项目ID)
            String SQL = " select distinct " +
                    " a.id as route_id , " + // -- 路径主键ID -- 路由ID
                    " a.route_name," + //  -- 路径名称
                    " a.route_type " + // -- 路由类型
                    " from route a join route_user b  " +
                    " on a.id = b.route_id and b.user_id = #{user_id} " +
                    " and a.gc_flag=0 and b.gc_flag = 0 and a.project_id = #{project_id} ";

            // 查询项目列表:
            final List<Map<String, Object>> maps = sqlMapper.selectList(SQL, user);

            sen.close();
            sen = null;
            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("2001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("4001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(maps);
            }

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("4002");
            rltInfo.setMessage("查询失败!");
            rltInfo.setData(null);

            // 记录异常日志:
            logger.error(Inner_log_Errcode + "getRouteList(user_id=" + userrouteReq.getUser_id() + ",project_id=" + userrouteReq.getProject_id() + ")" + ex.getMessage());
        }

        return rltInfo;
    }

}
