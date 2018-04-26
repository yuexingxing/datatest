package com.zd.service;

import com.zd.object.ResultInfo;
import com.zd.object.User;
import com.zd.util.CommandTools;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiusheng on 17/3/25.
 */
@Service("userService")
public class UserServiceImpl implements UserInterface {

    // 内部错误编码,写入到日志,用于跟踪错误的
    private static final String Inner_log_Errcode = "【UserServiceImpl-ERR】-";

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 登录检查
     * 请求 Post 数据:
     * {"username":"lmt","userpwd":"08C52FC23BA98DC1913922C97A781317"}
     *
     * @param UserName
     * @param UserPwd
     * @return
     */
    public ResultInfo login( String UserName, String UserPwd ) {
        // ObjectMapper objectMapper = new ObjectMapper();

        ResultInfo info = null;
        SqlSession sen = null;
        try {
            // SqlSession sen = MyBatisUtil.getSqlSession();
            sen = MyBatisUtil.getSqlSession(true);
            User user = new User();
            user.setUserName(UserName);
            user.setPassword(UserPwd);

            // 执行应该放到DAO中,这里为了省事直接干了
            String sql = "select top(1) a.id,a.Employee_Number, a.Is_PDA_Enabled , a.CompanyID, a.Sex, a.CellPhone , a.Enable_Status , " +
                    " a.Username , a.PDA_Passs_Word , a.Created_UserID, a.DepartmentID, a.Nickname, b.Expired_Time, b.State," +
                    " CASE b.Level_No WHEN  2 THEN b.ID  WHEN 3 THEN c.ID END platform_id" +
                    " from com_userinfo a JOIN dbo.Base_Company b ON a.CompanyID = b.ID" +
                    "      LEFT JOIN dbo.Base_Company c ON b.Parent_Comp_ID = c.ID" +
                    " where a.userName=#{userName} and a.PDA_Passs_Word=#{password}";
            // 2017-04-02 修改上面的查询SQL,用PDA的登录密码;

            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            // 传入参数,查询..
            final List<Map<String, Object>> maps = sqlMapper.selectList(sql, user);
            sqlMapper = null;
            info = new ResultInfo();

            if (null != maps & maps.size() > 0) {

                info.setSuccess(0);
                info.setCode("1001");
                info.setMessage("登录成功!");


                Map<String, Object> map = maps.get(0);
                if (null != maps & maps.size() > 0) {

                    info.setSuccess(0);
                    info.setCode("1001");
                    info.setMessage("登录成功!");

                    String Expired_Time = null;
                    if(map.containsKey("Expired_Time")){

                        Expired_Time= map.get("Expired_Time").toString();
                    }
                    int State = Integer.parseInt(map.get("State").toString());

                    if(!TextUtils.isEmpty(Expired_Time)){

                        if(Expired_Time.compareTo(CommandTools.getTime()) < 0){

                            info.setSuccess(-1);
                            info.setCode("1001");
                            info.setMessage("登录信息已过期");
                        }
                    }

                    if(State == 0){
                        info.setSuccess(-1);
                        info.setCode("1001");
                        info.setMessage("用户公司已停用");
                    }

                } else {
                    info.setSuccess(-1);
                    info.setCode("2001");
                    info.setMessage("用户名或者密码错误!");
                }
            } else {
                info.setSuccess(-1);
                info.setCode("2001");
                info.setMessage("用户名或者密码错误!");
            }

            info.setData(maps);
        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + ex.getMessage());
            info.setSuccess(-1);
            info.setCode("2001");
            info.setMessage("登录时遇到异常:" + ex.getMessage());
        } finally {
            try {
                sen.close();
                sen = null;
            } catch (Exception e) {
            }
        }

        return info;
    }


    /**
     * 更新PDA密码
     * @param UserName
     * @param UserPwd
     * @param NewUserPwd
     * @return
     */
    public ResultInfo update_password(String UserName, String UserPwd, String NewUserPwd) {

        ResultInfo info = new ResultInfo();

        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            User user = new User();
            user.setUserName(UserName);
            user.setPassword(UserPwd);

            String sql = " SELECT id FROM dbo.COM_UserInfo" +
                    " WHERE Username = #{userName}" +
                    " AND PDA_Passs_Word = #{password}";

            Map<String, Object> map = sqlMapper.selectOne(sql, user);
            if(map == null || map.size() == 0){
                info.setSuccess(1);
                info.setCode("1001");
                info.setMessage("用户或密码不正确");
                info.setData(null);
            }else{

                user.setPassword(NewUserPwd);//修改新密码

                sql = " UPDATE dbo.COM_UserInfo" +
                        " SET PDA_Passs_Word = #{password}" +
                        " WHERE Username = #{userName}";

                int count = sqlMapper.update(sql, user);
                if (count > 0) {
                    info.setSuccess(0);
                    info.setCode("1001");
                    info.setMessage("更新成功");
                    info.setData(null);

                } else {
                    info.setSuccess(-1);
                    info.setCode("2001");
                    info.setMessage("更新失败");
                    info.setData(null);
                }

                sen.commit();
                sen.close();
                sen = null;
            }
        } catch (Exception ex) {
            logger.error(Inner_log_Errcode + ex.getMessage());
            info.setSuccess(-1);
            info.setCode("2001");
            info.setMessage("更新密码异常:" + ex.getMessage());
        }

        return info;
    }
}
