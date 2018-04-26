package com.zd.dao;

import com.zd.object.ActionRequest;
import com.zd.util.SqlMapper;
import org.apache.http.util.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-24.
 */
public class QueryDao {

    /**
     * 获取任务列表
     * @param request
     * @param sqlMapper
     * @return
     */
    public List<Map<String, Object>> getTaskName(ActionRequest request, SqlMapper sqlMapper){

        String SQL = "select distinct (rtk.Task_Name), rtk.ID Task_ID, rtk.Task_Code" +
                " from " +
                "(" +
                " select sc.Route_Task_ID" +
                " from Scan sc" +
                " where sc.Route_Points_ID = #{node_id}" +
                " AND sc.Link_No = #{link_num}" +
                " and sc.GC_Flag  = 0" +
                ") sc join Route_Task rtk on sc.Route_Task_ID = rtk.ID" +
                " and rtk.GC_Flag = 0";

        return sqlMapper.selectList(SQL, request);
    }

    /**
     * 获取异常描述/退运原因
     * @param id
     * @param sqlMapper
     * @return
     */
    public Map<String, Object> getExceptDesc(String id, SqlMapper sqlMapper){

        String SQL = "select top(1) Except_Desc," + //异常描述/退运原因
                " Solution" +    //解决方案
                " from Scan_Exception sx" +
                " where sx.ID = #{id}";

        Map<String, Object> map = sqlMapper.selectOne(SQL, id);

        return map;
    }

    /**
     * 获取退运数、异常数
     * @param request
     * @param sqlMapper
     * @return
     */
    public List<Map<String, Object>> getBackExceptionNum(ActionRequest request, SqlMapper sqlMapper){

        String strReq = "select count(distinct case when sx.Exception_Type = 1 then sx.Main_Goods_ID end) Withdraw_Num , " +//-- 退运数
                " count(distinct case when sx.Exception_Type = 2 then sx.Main_Goods_ID end) Exception_Num  " + // 异常数
                " from Scan_Exception sx" +
                " where sx.Route_Points_ID = #{node_id}" +
                " and sx.Link_No = #{link_num}" +
                " and sx.GC_Flag = 0";

        return sqlMapper.selectList(strReq, request);
    }

    /**
     * 获取载入数
     * @param request
     * @param sqlMapper
     * @return
     */
    public Map<String, Object> getScanGoodsNum(ActionRequest request, SqlMapper sqlMapper){

        String sqlPlus = " where sc.Route_Points_ID = #{node_id}" +
                " and sc.Route_Task_Id = #{task_id}";
        if(TextUtils.isEmpty(request.getTask_id())){
            sqlPlus = " where sc.Route_Points_ID = #{node_id} ";
        }

        String strReq2 = "select top(1) count(distinct sdt.Goods_ID) Scan_Goods_Num" +
                " from Scan sc join Scan_Detail sdt on sdt.Scan_ID = sc.ID" +
                " and sc.GC_Flag = 0 and sdt.GC_Flag = 0" +
                sqlPlus +
                " and sc.Link_No = #{link_num}";

        return sqlMapper.selectOne(strReq2, request);
    }

    /**
     * 获取可操作环节
     * @param node_id
     * @param sqlMapper
     * @return
     */
    public List<Map<String, Object>> getLink(String node_id, SqlMapper sqlMapper){

        String strReq = " SELECT  B.Link_Caption AS link_name, link_no" +
                " FROM    ( SELECT    CASE WHEN rpt.Link_1 = 1 THEN bpt.Link_1" +
                "                    END [1] ," +
                "                    CASE WHEN rpt.Link_2 = 1 THEN bpt.Link_2" +
                "                    END [2] ," +
                "                    CASE WHEN rpt.Link_3 = 1 THEN bpt.Link_3" +
                "                    END [3]" +
                "          FROM      Route_Points rpt" +
                "                    JOIN Base_Points bpt ON bpt.ID " +
                " = rpt.Points_ID" +
                "                                            AND bpt.GC_Flag = 0" +
                "          WHERE     rpt.ID = #{node_id}" +
                "        ) A UNPIVOT ( Link_Caption FOR Link_No IN ( [1], [2], [3] ) ) B" +
                " WHERE   B.Link_Caption IS NOT NULL;";

        return sqlMapper.selectList(strReq, node_id);
    }

    /**
     * 获取货物明细，status为货物状态，NULL、退运、异常，NULL为正常状态
     * 如果taskId为空，则查询所有的
     * @param request
     * @param sqlMapper
     * @return
     */
    public List<Map<String, Object>> getGoodsDetail(ActionRequest request, SqlMapper sqlMapper){

        String sqlPlus = " where sc.Route_Points_ID = #{node_id}" +
                " AND sc.Route_Task_ID = #{task_id}";

        if(TextUtils.isEmpty(request.getTask_id())){
            sqlPlus = " where sc.Route_Points_ID = #{node_id} ";
        }

        String SQL = " SELECT * " +
                " FROM    ( SELECT   sdt.Scan_ID ," +
                "                    sdt.Goods_ID ," +
                "                    sdt.Goods_Code AS Pack_No ," +
                "                    sdt.Goods_Barcode AS Pack_Barcode ," +
                "                    sx.Except_Desc ," +
                "                    sx.Solution ," +
                "                    main.Pack_No AS Goods_No ," +
                "                    main.Pack_BarCode AS Goods_Barcode ," +
                "                    CASE sx.Exception_Type" +
                "                      WHEN 1 THEN '退运'" +
                "                      WHEN 2 THEN '异常'" +
                "                      ELSE '正常'" +
                "                    END status ," +
                "                    usr.Nickname Scan_User_Name ," +
                "                    sdt.Scan_Time ," +
                "   row_number() over (partition by sdt.Goods_ID order by sdt.Scan_Time desc, " +
                "   sx.Scan_Time desc) rn" +
                "  from scan sc join scan_detail sdt on sc.ID = sdt.Scan_ID" +
                " left join scan_exception sx on sc.Route_Points_ID = sx.Route_Points_ID" +
                " and sc.Link_No = sx.Link_No and sx.Exception_Type in (1,2)" +
                " and sdt.Goods_ID = sx.Main_Goods_ID" +
                " and sx.GC_Flag = 0" +
                "       left join Com_UserInfo usr on sdt.Created_UserID = usr.ID" +
                "       LEFT JOIN dbo.Part_ContainerGoods part ON sdt.Goods_Barcode = part.Goods_BarCode" +
                "       LEFT JOIN dbo.Main_ContainerGoods main ON main.Pack_BarCode = part.Pack_BNo" +
                "       LEFT JOIN dbo.Scan_Detail_Pack pack ON sdt.Scan_ID = pack.Scan_ID " +
                sqlPlus +
                "   and sc.Link_no = #{link_num}" +
                "   and sc.GC_Flag = 0" +
                "   and sdt.GC_Flag = 0" +
                ") a where rn = 1";

        return sqlMapper.selectList(SQL, request);
    }

    /**
     * 获取包装信息
     * */
    public List<Map<String, Object>> getPackData(String scan_id, SqlMapper sqlMapper){

        String strReq = "   SELECT Goods_BarCode," +
                "            Part_Goods_ID," +
                "            Goods_No" +
                "    FROM    dbo.Scan_Detail_Pack" +
                "    WHERE   Scan_ID = #{scan_id}";

        return sqlMapper.selectList(strReq, scan_id);
    }

    public Map<String, Object> checkBarCode(String barcode, SqlMapper sqlMapper){

        String strReq = " select top(1) b.goods_id as goods_id, b.Goods_Code as pack_no" +
                " FROM dbo.Scan a" +
                " JOIN dbo.Scan_Detail b" +
                " ON a.id = b.Scan_ID" +
                " AND b.Goods_Barcode = #{barcode}";

        return sqlMapper.selectOne(strReq, barcode);
    }

    public Map<String, Object> getPackInfo(String barcode, SqlMapper sqlMapper){

        String strReq = " SELECT TOP(1) a.Pack_No ," +
                "        a.Pack_BarCode ," +
                "        b.Goods_No ," +
                "        b.Goods_BarCode " +
                " FROM    dbo.Main_ContainerGoods a" +
                "        JOIN dbo.Part_ContainerGoods b ON a.Pack_BarCode = b.Pack_BNo " +
                "                                          AND b.Goods_BarCode = #{barcode}";

        return sqlMapper.selectOne(strReq, barcode);
    }
}
