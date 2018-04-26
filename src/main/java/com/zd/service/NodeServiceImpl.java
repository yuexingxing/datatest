package com.zd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zd.common.JsonUtils;
import com.zd.object.ActionRequest;
import com.zd.object.ResultInfo;
import com.zd.object.UserNodeRequest;
import com.zd.util.CommandTools;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.Encoder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-04-06.
 */
@Service("nodeService")
public class NodeServiceImpl implements NodeInterface{

    public ResultInfo getNodeList(UserNodeRequest userNodeRequest ){

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            String user_id = userNodeRequest.getUser_id();
            String route_id = userNodeRequest.getRoute_id();

            // 查询SQL    order by case when e.text='路径' then 1 else 2 end, b.sort_no
            String SQL = "select d.points_type, a.ID_1, b.route_id, row_number() over (order by b.sort_no) rn, " +
                    "b.id," +
                    "b.points_name," +
                    "c.route_name," +
                    //"d.points_type," +
                    "d.id," +
                    "a.Link_1," +
                    "a.Link_2," +
                    "a.Link_3 " +
                    "from route_user_points a join route_points b " +
                    "on a.ID_1 = #{user_id} " +//12D0DD57-C8AF-48B4-AA1B-8D1280A87A8D
                    "and a.gc_flag=0 " +
                    "and b.gc_flag=0 " +
                    "and a.connect_type = 1 and a.ID_2 = b.id " +
                    "and b.route_id = #{route_id} " +//84AC6AAD-0B17-423F-BCDB-DDF79E3005B6
                    "join route c on b.route_id  = c.id " +
                    "join Base_points d on b.points_id = d.id " +
                    "left join com_DataDictionaryInfo e on d.Form = e.id";

            // 查询节点列表:
            final List<Map<String, Object>> maps = sqlMapper.selectList(SQL, userNodeRequest);

            sen.close();
            sen = null;
            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(maps);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询节点SQL异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 判断是否第一个节点
     */
    public ResultInfo checkFirstNode(UserNodeRequest userNodeRequest ){

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            // 查询SQL
            String SQL = "select count(1) as node_num from (select id, row_number() over (order by sort_no) rn"
                    + " from route_points where  route_id = #{route_id}  AND gc_flag = 0) t "
                    + " where rn = 1 and id = #{node_id}";

            // 查询节点列表:
            final Map<String, Object> map = sqlMapper.selectOne(SQL, userNodeRequest);

            if (null != map & map.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");

                //int node_num = Integer.parseInt(map.get(0).get("node_num").toString());
                rltInfo.setData(map);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("当前操作不是第一个节点");
            }

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 任务状态汇总
     * @param actionRequest
     * @return
     */
    public ResultInfo getTaskStatusData(ActionRequest actionRequest){

        Map<String, Object> mapData = null;
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);

        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            JSONArray jsonArrayTask = new JSONArray();
            resultInfo = new ActionServiceImpl().getFlag(actionRequest);
            if(resultInfo.getSuccess() == 0){

                JSONObject jsonObject = JSONObject.fromObject(resultInfo.getData().toString());
                String flag = jsonObject.optString("flag");
                actionRequest.setFlag(Integer.parseInt(flag));

                List<Map<String, Object>> list = new ActionServiceImpl().getTaskStatusList(actionRequest);
                if(list == null || list.size() == 0){
                    resultInfo.setSuccess(1);
                    resultInfo.setMessage("未查到数据");
                    return resultInfo;
                }

                String last_task_id = null;
                int len = list.size();
                for(int i=0; i<len; i++){

                    String task_name = list.get(i).get("Task_Name").toString();
                    String task_id = list.get(i).get("ID").toString();
                    actionRequest.setTask_id(task_id);

                    if (!TextUtils.isEmpty(last_task_id) && last_task_id.equals(task_id)){
                        continue;
                    }

                    String strReq = "exec dbo.app_action_get_task_status #{task_id},#{node_id},#{route_id},#{flag}";

                    List<Map<String, Object>> maps = sqlMapper.selectList(strReq, actionRequest);
                    if(maps != null && maps.size() > 0){

                        for(int k=0; k<maps.size(); k++){

                            Map<String, Object> map = maps.get(k);
                            if(!map.containsKey("Upload_Time")){//时间返回为空情况
                                map.put("Upload_Time", "");
                            }

                            JSONObject jsonObject1 = JSONObject.fromObject(map.get("Upload_Time"));
                            if(jsonObject1 != null && jsonObject1.size() > 0){
                                map.put("Upload_Time", CommandTools.getLong2Time(jsonObject1.getLong("time")));
                            }

                        }

                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("Task_Name", task_name);
                        jsonObject2.put("link_data", maps);

                        jsonArrayTask.add(jsonObject2);
                    }

                    last_task_id = task_id;
                }
            }

            resultInfo.setData(jsonArrayTask);


        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("查询任务状态信息异常");
            resultInfo.setData(null);
        }

        if(resultInfo.getSuccess() == 0){
            resultInfo.setSuccess(0);
            resultInfo.setMessage("查询成功");
//            resultInfo.setData(mapData);
        }

        return resultInfo;
    }
}
