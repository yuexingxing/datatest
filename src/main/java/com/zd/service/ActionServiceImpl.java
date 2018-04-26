package com.zd.service;

import com.zd.dao.QueryDao;
import com.zd.dao.ServiceDataDao;
import com.zd.dao.SingleDao;
import com.zd.object.ActionRequest;
import com.zd.object.Header;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.pojo.QueryData;
import com.zd.pojo.ScanData;
import com.zd.util.Constant;
import com.zd.util.MyBatisUtil;
import com.zd.util.SqlMapper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 装货
 * Created by Administrator on 2017-04-05.
 */
@Service("actionService")
public class ActionServiceImpl implements ActionInterface {

    /**
     * 获取flag
     */
    public ResultInfo getFlag(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("user_id", request.getUser_id());
            paramMap.put("node_id", request.getNode_id());
            paramMap.put("flag", -1);

            final List<Map<String, Object>> maps = sen.selectList("app_action_get_flag", paramMap);

            Map<String, Object> mapsFlag = new HashMap<String, Object>();
            int flag = Integer.parseInt(paramMap.get("flag").toString());

            if (flag != -1) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1000");
                rltInfo.setMessage("查询成功");

                mapsFlag.put("flag", flag);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("flag获取失败");
            }

            rltInfo.setData(mapsFlag);

            sen.close();
            sen = null;
            sqlMapper = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("flag获取异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }


    /**
     * 查询
     */
    public ResultInfo query(QueryData data) {
        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq = "exec dbo.app_action_query #{pack_no}, #{pack_barcode}, #{type}";
            final List<Map<String, Object>> maps = sqlMapper.selectList(strReq, data);

            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到数据");
                rltInfo.setData(null);
            }

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 货物任务列表
     * 查到任务列表后，任务task_id获取任务名称组成部分字段，
     * 陆运-车牌号+车次
     */
    public ResultInfo getTaskList(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq = "exec dbo.app_gettaskname_list #{user_id},#{route_id},#{node_id},#{type},#{node_num},#{link_num},#{flag}";
            final List<Map<String, Object>> maps = sqlMapper.selectList(strReq, request);

            for (int i = 0; i < maps.size(); i++) {

                Map<String, Object> map = maps.get(i);
                String task_id = map.get("ID").toString();
                String task_name = map.get("Task_Name").toString();

                request.setBs_type("2");//默认是BS计划任务
                request.setTask_id(task_id);
                strReq = "exec dbo.app_gettaskname_by_taskid #{task_id},#{type},#{bs_type}";
                Map<String, Object> map2 = sqlMapper.selectOne(strReq, request);

                if (map2 != null && map2.size() > 0) {

                    map.put("car_plate", map2.get("car_plate"));
                    map.put("car_number", map2.get("car_number"));

                    //海运多个字段，单独判断
                    if (map2.containsKey("shipping_space")) {
                        map.put("shipping_space", map2.get("shipping_space"));
                    }
                } else {//如果没有查到，则认为是PDA创建任务

                    request.setBs_type("1");
                    strReq = "exec dbo.app_gettaskname_by_taskid #{task_id},#{type},#{bs_type}";
                    Map<String, Object> map3 = sqlMapper.selectOne(strReq, request);

                    if (map3 != null && map3.size() > 0) {

                        map.put("car_plate", map3.get("car_plate"));
                        map.put("car_number", map3.get("car_number"));

                        //海运多个字段，单独判断
                        if (map3.containsKey("shipping_space")) {
                            map.put("shipping_space", map3.get("shipping_space"));
                        }
                    }
                }
            }

            if (maps != null && maps.size() > 0) {

                getLinkMan_Phone(request, sqlMapper, maps);

                rltInfo.setSuccess(0);
                rltInfo.setCode("1000");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(null);
            }

            sen.close();
            sen = null;
            sqlMapper = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("任务列表查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }


    /**
     * 任务状态汇总
     * @param request
     * @return
     */
    public List<Map<String, Object>> getTaskStatusList(ActionRequest request) {

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq = "exec dbo.app_gettaskname_list_taskstatus #{user_id},#{node_id},#{flag}";
            listMap = sqlMapper.selectList(strReq, request);

            sen.close();
            sen = null;
            sqlMapper = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listMap;
    }


    /**
     * 获取商品明细
     */
    public ResultInfo getGoodsDetail(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            if(TextUtils.isEmpty(request.getTask_id())){
                request.setTask_id("");
            }

            String strReq = "exec dbo.app_getgoodsdetail_list #{user_id}, #{node_id}, #{task_id}, #{node_num}, #{link_num}, #{flag}";
            final List<Map<String, Object>> listData = sqlMapper.selectList(strReq, request);

            if (listData != null && listData.size() > 0) {

                getGoodsDetailData(request, sqlMapper, listData);
                if(request.getType().equals(Constant.SCAN_TYPE_STORAGE)){
                    getStorageGoodsDetailData(request, sqlMapper, listData);
                }else if(request.getType().equals(Constant.SCAN_TYPE_SCALE)){
                    getScaleGoodsDetailData(request, sqlMapper, listData);
                }else if(request.getType().equals(Constant.SCAN_TYPE_PACK)){
//                    getPackGoodsDetailData(request, sqlMapper, listData);
                }else if(request.getType().equals(Constant.SCAN_TYPE_INSTALL)){

                }else if(request.getType().equals(Constant.SCAN_TYPE_LAND)){
                    getLandGoodsDetailData(request, sqlMapper, listData);
                }else if(request.getType().equals(Constant.SCAN_TYPE_STRAP)){
                    getStrapGoodsDetailData(request, sqlMapper, listData);
                }else if(request.getType().equals(Constant.SCAN_TYPE_STICK)){
                    getStickGoodsDetailData(request, sqlMapper, listData);
                }

                rltInfo.setSuccess(0);
                rltInfo.setCode("1000");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(listData);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(null);
            }

            sen.close();
            sen = null;
            sqlMapper = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("商品详情查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 获取抵达相关信息
     */
    public ResultInfo getArriveData(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq = "exec dbo.[app_action_arrive] #{task_id}, #{type}";
            final List<Map<String, Object>> maps = sqlMapper.selectList(strReq, request);

            if (maps != null && maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1000");
                rltInfo.setMessage("查询成功");

                rltInfo.setData(maps.get(0));
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到有效数据");
            }

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("抵达信息查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 获取装卸公司
     */
    public ResultInfo getCompanyList(String platform_id) {

        ResultInfo rltInfo = new ResultInfo();

        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            // 查询SQL
            String SQL = "SELECT  a.ID AS company_id ," +
                    "        a.Company_Name_Cn AS company_name ," +
                    "        b.Text AS company_type ," +
                    "        c.Nickname AS link_man ," +
                    "        c.CellPhone AS phone " +
                    " FROM    Base_Company a" +
                    "        JOIN COM_DataDictionaryInfo b ON a.Company_Type_ID = b.ID" +
                    "        JOIN dbo.COM_UserInfo c ON a.ID = c.CompanyID" +
                    "                                   AND b.Text = '服务公司' " +
                    " AND a.Parent_Comp_ID = #{platform_id}";

            final List<Map<String, Object>> maps = sqlMapper.selectList(SQL, platform_id);

            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到装卸公司");
                rltInfo.setData(maps);
            }

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询失败!");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 根据条码获取相关信息
     */
    public ResultInfo getInfoByBarcode(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq = "exec dbo.[app_exception_query_by_barcode] #{pack_barcode}, #{link_num}, #{type}";
            final List<Map<String, Object>> maps = sqlMapper.selectList(strReq, request);

            if (null != maps & maps.size() > 0) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到装卸公司");
                rltInfo.setData(maps);
            }

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询失败!");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 查询载入数相关信息
     */
    public ResultInfo getLoadNum(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            if(TextUtils.isEmpty(request.getTask_id())){
                request.setTask_id(null);
            }

//            String strReq1 = "exec dbo.[app_get_must_load_num] #{user_id}, #{node_id}, #{task_id}, #{flag}";
            String strReq1 = "exec dbo.[app_get_must_load_num2] #{user_id}, #{node_id}, #{task_id}, #{link_num}, #{flag}";
            Map<String, Object> listMap1 = sqlMapper.selectOne(strReq1, request);

            String strReq2 = "exec dbo.[app_get_goods_num] #{user_id}, #{node_id}, #{task_id}, #{node_num}, #{link_num}, #{flag}";
            Map<String, Object> listMap2 = sqlMapper.selectOne(strReq2, request);

            String strReq3 = "exec dbo.[app_get_must_scan_num] #{user_id}, #{node_id}";
            Map<String, Object> listMap3 = sqlMapper.selectOne(strReq3, request);

            int Must_Load_Number = 0;
            int Real_Load_Number = 0;
            int Must_Scan_Number = 0;

            if (listMap1 != null && listMap1.size() > 0) {
                Must_Load_Number = Integer.parseInt(listMap1.get("Must_Load_Number").toString());
            }

            if (listMap2 != null && listMap2.size() > 0) {
                Real_Load_Number = Integer.parseInt(listMap2.get("Real_Load_Number").toString());
            }

            if (listMap3 != null && listMap3.size() > 0) {
                Must_Scan_Number = Integer.parseInt(listMap3.get("Must_Scan_Number").toString());
            }

            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("must_load_number", Must_Load_Number);
            map1.put("real_load_number", Real_Load_Number);
            map1.put("must_scan_number", Must_Scan_Number);

            sen.close();
            sen = null;
            sqlMapper = null;

            if (null != map1) {
                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(map1);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("未查询到载入数量");
                rltInfo.setData(null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询失败" + ex.getMessage());
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 判断条码是否存在
     */
    public ResultInfo checkBarcode(String barcode) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            String strReq2 = "select top(1) goods_id as goods_id from scan_detail where goods_barcode = #{barcode}";
            Map<String, Object> maps = sqlMapper.selectOne(strReq2, barcode);

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

            if (maps != null && maps.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("条码不存在");
                rltInfo.setData(null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 判断条码是否存在
     */
    public ResultInfo backCheckBarcode(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            Map<String, Object> map = new SingleDao().checkIfRightLink(request, sqlMapper);
            if (map != null && map.size() > 0) {

            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("请先在操作环节完成扫描");
                rltInfo.setData(null);

                sen.close();

                sen = null;
                sqlMapper = null;
                return rltInfo;
            }

            Map<String, Object> maps = new SingleDao().checkBarcode(request, sqlMapper);

            if (maps != null && maps.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("货物不存在");
                rltInfo.setData(null);
            }

            sen.close();

            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 单件录入--判断条码是否可操作
     */
    public ResultInfo singleCheckBarcode(ActionRequest request) {

        SingleDao singleDao = new SingleDao();

        ResultInfo rltInfo = new ResultInfo();
        rltInfo.setSuccess(0);
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            Map<String, Object> maps1 = singleDao.checkBarcode(request, sqlMapper);

            if (maps1 != null && maps1.size() > 0) {

                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("总箱单中存在该包装号码，禁止做单件录入");
                rltInfo.setData(maps1);
                return rltInfo;
            }

            Map<String, Object> maps2 = singleDao.getCheckStatus(request.getPack_no(), sqlMapper);
            if (maps2 != null && maps2.size() > 0) {

                int check_status = Integer.parseInt(maps2.get("check_status").toString());
                if (check_status != 0) {
                    rltInfo.setSuccess(-1);
                    rltInfo.setCode("1001");
                    rltInfo.setMessage("该货物已做过单件录入，且审核通过");
                    rltInfo.setData(maps1);
                    return rltInfo;
                }
            } else {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps1);
            }

            sen.close();
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 查询--获取可操作环节
     */
    public ResultInfo queryGetLink(String node_id) {

        ResultInfo rltInfo = new ResultInfo();
        try {

            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            List<Map<String, Object>> listMap = new QueryDao().getLink(node_id, sqlMapper);

            sen.close();
            sen = null;
            sqlMapper = null;
            if (listMap != null && listMap.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(listMap);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到可操作环节");
                rltInfo.setData(null);
                return rltInfo;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("查询可操作环节异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 查询---获取任务列表
     * 返回值增加一个全部任务
     *
     * @param request
     * @return
     */
    public ResultInfo queryGetTaskName(ActionRequest request) {

        ResultInfo rltInfo = new ResultInfo();
        try {

            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            List<Map<String, Object>> listMap = new QueryDao().getTaskName(request, sqlMapper);

            if (listMap != null && listMap.size() > 0) {

                Map<String, Object> map = new HashedMap();
                map.put("Task_Name", "全部");
                map.put("Task_Code", "");
                map.put("Task_ID", "");
                listMap.add(0, map);

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");

            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到任务列表");
            }

            rltInfo.setData(listMap);

            sen.commit();
            sen.close();
            sen = null;
            sqlMapper = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 查询---获取货物明细及相关数量
     *
     * @param request
     * @return
     */
    public ResultInfo queryGetGoodsDetail(ActionRequest request) {

        QueryDao queryDao = new QueryDao();
        ResultInfo rltInfo = new ResultInfo();
        try {

            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            List<Map<String, Object>> listMap1 = queryDao.getBackExceptionNum(request, sqlMapper);

            Map<String, Object> map = queryDao.getScanGoodsNum(request, sqlMapper);

            int Withdraw_Num = Integer.parseInt(listMap1.get(0).get("Withdraw_Num").toString());
            int Scan_Goods_Num = Integer.parseInt(map.get("Scan_Goods_Num").toString());

            Map<String, Object> mapNumber = new HashedMap();
            mapNumber.put("withdraw_num", Withdraw_Num);
            mapNumber.put("exception_num", listMap1.get(0).get("Exception_Num"));
            mapNumber.put("scan_goods_num", Scan_Goods_Num - Withdraw_Num);//总数 - 退运数

            final List<Map<String, Object>> listMaps2 = queryDao.getGoodsDetail(request, sqlMapper);

            mapNumber.put("detail", listMaps2);

            sen.close();
            sen = null;
            sqlMapper = null;

            if (listMaps2 != null && listMaps2.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(mapNumber);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(null);
                return rltInfo;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 查询---获取异常原因，解决方案
     *
     * @param id
     * @return
     */
    public ResultInfo queryGetEcpSolution(String id) {

        ResultInfo rltInfo = new ResultInfo();
        try {

            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            Map<String, Object> map = new QueryDao().getExceptDesc(id, sqlMapper);

            if (map != null && map.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(map);
            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("未查询到有效数据");
                rltInfo.setData(null);
                return rltInfo;
            }

            sen.close();
            ;
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("异常原因查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 获取货物信息
     */
    public ResultInfo getByBarcode(ActionRequest request) {

        SingleDao singleDao = new SingleDao();

        ResultInfo rltInfo = new ResultInfo();
        rltInfo.setSuccess(0);
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);

            Map<String, Object> map = new SingleDao().checkIfRightLink(request, sqlMapper);
            if (map != null && map.size() > 0) {

            } else {
                rltInfo.setSuccess(-1);
                rltInfo.setCode("2001");
                rltInfo.setMessage("请先在操作环节完成扫描");
                rltInfo.setData(null);

                sen.close();

                sen = null;
                sqlMapper = null;
                return rltInfo;
            }

            Map<String, Object> maps = singleDao.checkBarcode(request, sqlMapper);

            if (maps != null && maps.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {

                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("货物不存在");
                rltInfo.setData(maps);
            }

            sen.close();
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 获取货物信息
     */
    public ResultInfo getByBarcode_takephoto(ActionRequest request) {

        SingleDao singleDao = new SingleDao();

        ResultInfo rltInfo = new ResultInfo();
        rltInfo.setSuccess(0);
        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            //创建sqlMapper
            SqlMapper sqlMapper = new SqlMapper(sen);
            Map<String, Object> maps = singleDao.checkBarcode(request, sqlMapper);

            if (maps != null && maps.size() > 0) {

                rltInfo.setSuccess(0);
                rltInfo.setCode("1001");
                rltInfo.setMessage("查询成功");
                rltInfo.setData(maps);
            } else {

                rltInfo.setSuccess(-1);
                rltInfo.setCode("1001");
                rltInfo.setMessage("货物不存在");
                rltInfo.setData(maps);
            }

            sen.close();
            sen = null;
            sqlMapper = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            rltInfo.setSuccess(-1);
            rltInfo.setCode("2001");
            rltInfo.setMessage("条码查询异常");
            rltInfo.setData(null);
        }

        return rltInfo;
    }

    /**
     * 根据node_id,task_id获取节点负责人、联系电话
     *
     * @param actionRequest
     * @param sqlMapper
     */
    public void getLinkMan_Phone(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            String SQL = " SELECT TOP (1)" +
                    "        d.Nickname AS link_man ," +
                    "        a.Phone AS phone " +
                    " FROM    dbo.Route_Points a" +
                    "        JOIN Route_Task b ON a.ID = b.route_points_id" +
                    "        JOIN Route_User_Points c ON a.ID = c.ID_2" +
                    "                                    AND b.route_points_id = #{node_id}" +
                    "                                    AND b.id = #{task_id}" +
                    "        LEFT JOIN dbo.COM_UserInfo d ON a.User_ID = d.ID";

            Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);
            if (mapData != null && mapData.size() > 0) {

                for (int i = 0; i < list.size(); i++) {

                    Map<String, Object> map = list.get(i);

                    if (mapData.containsKey("link_man")) {
                        map.put("link_man", mapData.get("link_man").toString());
                    }else{
                        map.put("link_man", "");
                    }

                    if (mapData.containsKey("phone")) {
                        map.put("phone", mapData.get("phone").toString());
                    }else{
                        map.put("phone", "");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    /**
     * 货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);
                actionRequest.setPack_barcode(map.get("Pack_BarCode").toString());

                String SQL = " SELECT  Parts_Name AS goods_name ," +
                        "        Length AS length," +
                        "        Width AS width," +
                        "        Height AS height," +
                        "        Pack_BNo as pack_bno," +
                        "        Pack_Require AS pack_require," +
                        "        install AS install_info," +
                        "        OffLine_Remark AS offline_info" +
                        " FROM    dbo.Part_ContainerGoods" +
                        " WHERE   Goods_BarCode = #{pack_barcode}" +
                        "        AND Project = #{project_id}";

                Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);
                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("goods_name")) {
                        map.put("goods_name", mapData.get("goods_name").toString());
                    }else{
                        map.put("goods_name", "");
                    }

                    if (mapData.containsKey("length")) {
                        map.put("length", mapData.get("length").toString());
                    }else{
                        map.put("length", "");
                    }

                    if (mapData.containsKey("width")) {
                        map.put("width", mapData.get("width").toString());
                    }else{
                        map.put("width", "");
                    }

                    if (mapData.containsKey("pack_bno")) {
                        map.put("pack_bno", mapData.get("pack_bno").toString());
                    }else{
                        map.put("pack_bno", "");
                    }

                    if (mapData.containsKey("pack_require")) {
                        map.put("pack_require", mapData.get("pack_require").toString());
                    }else{
                        map.put("pack_require", "");
                    }

                    if (mapData.containsKey("pack_require")) {
                        map.put("pack_require", mapData.get("pack_require").toString());
                    }else{
                        map.put("pack_require", "");
                    }

                    if (mapData.containsKey("install_info")) {
                        map.put("install_info", mapData.get("install_info").toString());
                    }else{
                        map.put("install_info", "");
                    }

                    if (mapData.containsKey("offline_info")) {
                        map.put("offline_info", mapData.get("offline_info").toString());
                    }else{
                        map.put("offline_info", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 货场---货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getStorageGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            String SQL = " SELECT top(1) Remark AS memo ," +
                    "        Storeman AS man" +
                    " FROM    dbo.Route_Storage" +
                    " WHERE Route_Points_ID = #{node_id}";

            Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);
                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("memo")) {
                        map.put("memo", mapData.get("memo").toString());
                    }else{
                        map.put("memo", "");
                    }

                    if (mapData.containsKey("man")) {
                        map.put("man", mapData.get("man").toString());
                    }else{
                        map.put("man", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 陆运---货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getLandGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);

                actionRequest.setId(map.get("ID").toString());

                String SQL = " SELECT top(1) " +
                        " c.Main_Goods_ID," +
                        " c.Driver_Phone AS phone," +
                        " c.Remark AS memo" +
                        " FROM dbo.Scan a" +
                        " JOIN dbo.Scan_Detail b" +
                        " ON a.id = b.Scan_ID" +
                        " JOIN dbo.Scan_Detail_Car c" +
                        " ON a.id = c.Scan_ID" +
                        " AND c.Main_Goods_ID = #{id}" +
                        " AND a.Link_No = '1'" +
                        " AND a.Route_Points_ID = #{node_id}";

                Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);

                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("phone")) {
                        map.put("phone", mapData.get("phone").toString());
                    }else{
                        map.put("phone", "");
                    }

                    if (mapData.containsKey("memo")) {
                        map.put("memo", mapData.get("memo").toString());
                    }else{
                        map.put("memo", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑扎---货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getStrapGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);

                actionRequest.setId(map.get("ID").toString());

                String SQL = " SELECT top(1) Logistics_Require as memo" +
                        " FROM dbo.Main_ContainerGoods" +
                        " WHERE ID = #{id}";

                Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);

                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("memo")) {
                        map.put("memo", mapData.get("memo").toString());
                    }else{
                        map.put("memo", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打尺---货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getScaleGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);
                actionRequest.setPack_barcode(map.get("Pack_BarCode").toString());

                Map<String, Object> mapData = new ServiceDataDao().getScaleData(actionRequest, sqlMapper);
                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("length")) {
                        map.put("length", mapData.get("length").toString());
                    }else{
                        map.put("length", "");
                    }

                    if (mapData.containsKey("width")) {
                        map.put("width", mapData.get("width").toString());
                    }else{
                        map.put("width", "");
                    }

                    if (mapData.containsKey("height")) {
                        map.put("height", mapData.get("height").toString());
                    }else{
                        map.put("height", "");
                    }

                    if (mapData.containsKey("v3")) {
                        map.put("v3", mapData.get("v3").toString());
                    }else{
                        map.put("v3", "");
                    }

                    if (mapData.containsKey("weight")) {
                        map.put("weight", mapData.get("weight").toString());
                    }else{
                        map.put("weight", "");
                    }

                    if (mapData.containsKey("charge_ton")) {
                        map.put("charge_ton", mapData.get("charge_ton").toString());
                    }else{
                        map.put("charge_ton", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 包装---货物明细---获取货物包装信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getPackGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);

                actionRequest.setPack_barcode(map.get("Pack_BarCode").toString());

                String SQL = " SELECT top(1) Pack_BNo AS pack_no " +
                        " FROM dbo.Part_ContainerGoods " +
                        " WHERE Goods_BarCode = #{pack_barcode} " +
                        " AND Project = #{project_id} ";

                Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);

                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("pack_no")) {
                        map.put("Real_PackNo", mapData.get("pack_no").toString());
                    }else{
                        map.put("Real_PackNo", "");
                    }
                }else{
                    map.put("Real_PackNo", "");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 贴唛---货物明细---获取货物的详细信息
     *
     * @param actionRequest
     * @param sqlMapper
     * @param list
     */
    public void getStickGoodsDetailData(ActionRequest actionRequest, SqlMapper sqlMapper, List<Map<String, Object>> list) {

        try {

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = list.get(i);

                actionRequest.setId(map.get("ID").toString());

                String SQL = " SELECT top(1) Product_Name as goods_name" +
                        " FROM dbo.Main_ContainerGoods" +
                        " WHERE ID = #{id}";

                Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);

                if (mapData != null && mapData.size() > 0) {

                    if (mapData.containsKey("goods_name")) {
                        map.put("goods_name", mapData.get("goods_name").toString());
                    }else{
                        map.put("goods_name", "");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取总箱单信息,
     * 需要判断每个货物是否对应总箱单号，如果不在一个总箱单则退出
     */
    public ResultInfo getSumPackageInfo(ScanDataListEnt scandataPackage) {

        Map<String, Object> mapData = null;
        ActionRequest actionRequest = new ActionRequest();
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);

        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            Header header = scandataPackage.getHeader();
            actionRequest.setProject_id(header.getProject_id());
            actionRequest.setNode_id(header.getNode_id());

            String pack_barcode = null;
            List<ScanData> list = scandataPackage.getDetail();
            int len = list.size();
            for (int i = 0; i < len; i++) {

                ScanData scanData = list.get(i);
                actionRequest.setPack_barcode(scanData.getPackNumber());
                actionRequest.setPack_no(scanData.getPackNumber());

                String SQL = "SELECT top(1) a.Pack_BarCode AS pack_barcode ," +
                        "        a.Pack_No AS pack_no ," +
                        "        a.Product_Name AS parts_name ," +
                        "        c.Pack_Company_Name AS company_name ," +
                        "        c.Pack_Company_ID AS company_id ," +
                        "        a.Packing AS pack_mode ," +
                        "        a.Length AS length ," +
                        "        a.Width AS width ," +
                        "        a.Height AS height ," +
                        "        a.Gross_Weight AS gross_weight, " +
                        "        a.Logistics_Require AS remark, " +
                        "        a.Volume AS v3 " +
                        " FROM   dbo.Main_ContainerGoods a" +
                        "        JOIN dbo.Part_ContainerGoods b ON a.Pack_No = b.Pack_BNo" +
                        "                                          AND a.Project = b.Project" +
                        "                                          AND b.Project = #{project_id}" +
                        "                                          AND b.Goods_No = #{pack_no}" +
                        "        LEFT JOIN dbo.Route_Pack c ON c.Route_Points_ID = #{node_id}";

                mapData = sqlMapper.selectOne(SQL, actionRequest);
                if (mapData == null || mapData.size() == 0) {

                    if(i == 0){
                        resultInfo.setSuccess(1);
                        resultInfo.setMessage("请完整填写相关包装信息");
                    }else{
                        resultInfo.setSuccess(1);
                        resultInfo.setMessage("商品不属于同一包装号码");
                    }

                }else{

                    if(i == 0){
                        pack_barcode = mapData.get("pack_barcode").toString();
                    }else {
                        if(TextUtils.isEmpty(pack_barcode) || !pack_barcode.equals(mapData.get("pack_barcode").toString())){
                            resultInfo.setSuccess(1);
                            resultInfo.setCode("100");
                            resultInfo.setMessage("商品不属于同一包装号码");
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("查询总箱单信息异常");
        }

        if(resultInfo.getSuccess() == 0){
            resultInfo.setSuccess(0);
            resultInfo.setMessage("查询成功");
            resultInfo.setData(mapData);
        }

        return resultInfo;
    }

    /**
     * 获取总箱单信息,
     * 根据条码号
     */
    public ResultInfo getSumPackageInfoByBarcode(ActionRequest actionRequest) {

        Map<String, Object> mapData = null;
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);
        resultInfo.setMessage("查询成功");

        try {
            SqlSession sen = MyBatisUtil.getSqlSession();
            SqlMapper sqlMapper = new SqlMapper(sen);

            String SQL = " SELECT top(1) Pack_BarCode AS pack_barcode," +
                    "        Pack_No AS pack_no," +
                    "        Product_Name AS goods_name," +
                    "        Length AS length," +
                    "        Width AS width," +
                    "        Height AS height," +
                    "        Gross_Weight AS weight," +
                    "        Packing AS pack_type," +
                    "        Logistics_Require AS remark" +
                    " FROM   dbo.Main_ContainerGoods" +
                    " WHERE  Pack_BarCode = #{pack_barcode}";

            mapData = sqlMapper.selectOne(SQL, actionRequest);
            if (mapData == null || mapData.size() == 0) {
                resultInfo.setSuccess(1);
                resultInfo.setMessage("未查到对应包装信息");
            }else{
                resultInfo.setData(mapData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("查询总箱单信息异常");
        }

        return resultInfo;
    }


    /**
     * 获取RFID绑定的货物信息
     *
     */
    public ResultInfo getInfoByRFID(ActionRequest actionRequest) {

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);

        SqlSession sen = MyBatisUtil.getSqlSession();
        SqlMapper sqlMapper = new SqlMapper(sen);
        try {

            String SQL = " SELECT top(1) a.Pack_BarCode AS pack_barcode," +
                    " a.Pack_No AS pack_no," +
                    " b.Main_Goods_ID AS goods_id" +
                    " FROM dbo.Main_ContainerGoods a" +
                    "        JOIN dbo.Main_Goods_RFID b ON a.ID = b.Main_Goods_ID" +
                    "                                      AND b.RFID = #{rfid}" +
                    "                                      AND a.Project = #{project_id}";

            Map<String, Object> mapData = sqlMapper.selectOne(SQL, actionRequest);
            if (mapData != null && mapData.size() > 0) {

                mapData.put("rfid", actionRequest.getRfid());

                resultInfo.setSuccess(0);
                resultInfo.setMessage("查询成功");
            }else{

                resultInfo.setSuccess(0);
                resultInfo.setMessage("未查询到数据");
            }

            resultInfo.setData(mapData);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setMessage("获取RFID信息异常");
        }finally {
            sen.close();;
            sen = null;
        }

        return resultInfo;
    }

    /**
     * 微信扫码后获取打尺详细数据
     * 分多个SQL写的，然后拼装在一起
     *
     * @param node_id
     * @return
     */
    public ResultInfo getWXInfo(String node_id){

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(0);
        resultInfo.setCode("1000");
        resultInfo.setMessage("获取成功");

        JSONObject jsonObject = new JSONObject();

        SqlSession sen = MyBatisUtil.getSqlSession();
        SqlMapper sqlMapper = new SqlMapper(sen);
        String project_name = "";
        try {

            //获取项目名称
            String SQL = " SELECT top(1) rt.Project_ID ," +
                    "        pro.Project_Name AS project_name" +
                    " FROM   Route rt" +
                    "        JOIN Route_Points rpt ON rt.ID = rpt.Route_ID" +
                    "        JOIN dbo.Project pro ON pro.ID = rt.Project_ID" +
                    " WHERE  rpt.ID = #{node_id}";//'68D33FAD-D256-4CD3-8A0A-A0D73D1F41E6'
            Map<String, Object> mapData = sqlMapper.selectOne(SQL, node_id);
            if (mapData != null && mapData.size() > 0) {
                project_name = mapData.get("project_name").toString();
            }

            //获取打尺总体积和计费吨
            SQL = " SELECT DISTINCT" +
                    "        c.Pack_Barcode ," +
                    "        c.Pack_No ," +
                    "        c.Scale_Company_Name ," +
                    "        c.Goods_Len ," +
                    "        c.Goods_Width ," +
                    "        c.Goods_Height ," +
                    "        c.Gross_Weight ," +
                    "        c.Charge_Ton ," +
                    "        c.Created_User ," +
                    "        c.Scale_User_Name ," +
                    "        c.Created_Time" +
                    " FROM   dbo.Scan a" +
                    "        JOIN dbo.Scan_Detail b ON a.ID = b.Scan_ID" +
                    "        JOIN dbo.Scan_Detail_Scale c ON b.Scan_ID = c.Scan_ID" +
                    "                                        AND a.Route_Points_ID = #{node_id}" +
                    "                                        ORDER BY c.Created_Time DESC";//'68D33FAD-D256-4CD3-8A0A-A0D73D1F41E6'
            List<Map<String, Object>> listMapData = sqlMapper.selectList(SQL, node_id);

            String commit_time = "";
            String monitor = "";
            String ruler_company = "";
            String ruler_user = "";
            double sumLen=0, sumWidth=0, sumHeight=0;
            double sumChargeTon=0, sumWeight = 0.0;
            double rulerV3 = 0.0;
            double boxV3 = 0.0;

            if (listMapData != null && listMapData.size() > 0) {

                int len = listMapData.size();

                for(int i=0; i<len; i++){

                    mapData = listMapData.get(i);
                    if(i == 0){
                        commit_time = mapData.get("Created_Time").toString();
                        ruler_company = mapData.get("Scale_Company_Name").toString();
                        ruler_user = mapData.get("Created_User").toString();
                        monitor = mapData.get("Scale_User_Name").toString();
                    }

                    double length = Integer.parseInt(mapData.get("Goods_Len").toString()) / 1000.00;
                    double width = Integer.parseInt(mapData.get("Goods_Width").toString()) / 1000.00;
                    double height = Integer.parseInt(mapData.get("Goods_Height").toString()) / 1000.00;

                    rulerV3 = rulerV3 + length * width * height;

                    sumWeight = sumWeight + Double.parseDouble(mapData.get("Gross_Weight").toString());

                    sumChargeTon = sumChargeTon + Double.parseDouble(mapData.get("Charge_Ton").toString());

                    ActionRequest actionRequest = new ActionRequest();
                    actionRequest.setPack_barcode(mapData.get("Pack_Barcode").toString());
                    actionRequest.setPack_no(mapData.get("Pack_No").toString());

                    //从主表中获取体积
                    SQL = " SELECT  top(1) Volume" +
                            " FROM    dbo.Main_ContainerGoods" +
                            " WHERE   Pack_BarCode = #{pack_barcode}" +
                            "         AND Pack_No = #{pack_no}";
                    mapData = sqlMapper.selectOne(SQL, actionRequest);
                    if(mapData != null && mapData.size() > 0){
                        boxV3 = boxV3 + Double.parseDouble(mapData.get("Volume").toString());
                    }
                }

            }

            DecimalFormat df = new DecimalFormat("#.00");

            jsonObject.put("project_name", project_name);
            jsonObject.put("ruler_company", ruler_company);//打尺公司
            jsonObject.put("ruler_user", ruler_user);//打尺员
            jsonObject.put("monitor", monitor);//监督员
            jsonObject.put("commit_time", commit_time);//提交时间

            jsonObject.put("ruler_v3", Double.parseDouble(df.format(rulerV3)));//打尺总体积
            jsonObject.put("box_v3", Double.parseDouble(df.format(boxV3)));//箱单总体积
            jsonObject.put("sum_weight", sumWeight);//总毛重
            jsonObject.put("charge_ton", Double.parseDouble(df.format(sumChargeTon)));//计费吨

            resultInfo.setData(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setSuccess(1);
            resultInfo.setCode("1001");
            resultInfo.setMessage("微信扫码异常-" + e.getLocalizedMessage());
        }finally {
            sen.close();;
            sen = null;
        }

        return resultInfo;
    }
}
