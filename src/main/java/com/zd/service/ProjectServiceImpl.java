package com.zd.service;

import com.zd.object.ResultInfo;
import com.zd.object.UserProjectlistRequest;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 项目服务
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectInterface {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【ProjectServiceImpl-ERR】-";

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    /**
     * 登录之后,根据用户明查询项目列表,返回一个json数组即可
     *
     * @param
     * @return 测试数据:
     * and a.id_user = '80D8FD19-4358-413F-A606-E45206E231A5'
     */
    public ResultInfo getProjectRouteList(UserProjectlistRequest lrEnt) {
        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession(false);

            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            // 查询SQL
            String SQL = "select distinct b.id,  b.project_name , b.project_code  " +
                    "from com_userorganizationinfo a " +
                    "join project b on a.ID_Organization = b.ID " +
                    "     where a.gc_flag  = 0 " +
                    "       and b.gc_flag = 0 " +
                    "       and a.id_user = #{user_id}"+
                    "       and b.Platform_Comp_ID = #{platform_id}";

            // 查询项目列表:
            final List<Map<String, Object>> maps = sqlMapper.selectList(SQL, lrEnt);
            sen.close();
            sqlMapper = null;

            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到可操作项目");
                rltInfo.setData(maps);
            }

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询项目出现异常");
            rltInfo.setData(null);

            // 记录异常日志:
            logger.error(Inner_log_Errcode + "getProjectRoutList(user_id=" + lrEnt.getUser_id() + ")" + ex.getMessage());
        }

        return rltInfo;
    }
}
