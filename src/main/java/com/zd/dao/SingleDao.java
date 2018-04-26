package com.zd.dao;

import com.zd.object.ActionRequest;
import com.zd.util.Constant;
import com.zd.util.SqlMapper;

import java.util.Map;

/**
 * Created by Administrator on 2017-05-25.
 */
public class SingleDao {

    /**
     * 判断总箱单、分箱单中是否存在该条码
     * @param request
     * @param sqlMapper
     * @return
     */
    public Map<String, Object> checkBarcode(ActionRequest request, SqlMapper sqlMapper){

        String strReq = "select top(1) id, pack_no as pack_no from Main_ContainerGoods where Pack_BarCode = #{pack_barcode} and project=#{project_id}";
        if(request.getType().equals(Constant.SCAN_TYPE_INSTALL) || request.getType().equals(Constant.SCAN_TYPE_OFFLINE)){
            strReq = "select top(1) id, goods_no as pack_no from Part_ContainerGoods where Goods_BarCode = #{pack_barcode} and project=#{project_id}";
        }
//        else if(request.getType().equals(Constant.SCAN_TYPE_PACK)){
//            strReq = " SELECT  a.Pack_BarCode AS pack_barcode," +
//                    "        a.Pack_No AS pack_no," +
//                    "        b.Goods_BarCode AS goods_barcode," +
//                    "        b.Goods_No AS goods_no " +
//                    " FROM   dbo.Main_ContainerGoods a " +
//                    "        JOIN dbo.Part_ContainerGoods b ON a.Pack_No = b.Pack_BNo" +
//                    "                                          AND b.Goods_BarCode = #{pack_barcode}" +
//                    "                                          AND b.Project = #{project_id}";
//        }

        return sqlMapper.selectOne(strReq, request);
    }

    /**
     * 单件录入表中已经存在的包装号码，已经审核过的（check_status<>0），禁止单件录入
     * @param pack_no
     * @param sqlMapper
     * @return
     */
    public Map<String, Object> getCheckStatus(String pack_no,  SqlMapper sqlMapper){

        String strReq2 = "select top(1) Check_Status as check_status from Scan_Input_Goods where Pack_No = #{pack_no}";
        return sqlMapper.selectOne(strReq2, pack_no);
    }

    public Map<String, Object> checkIfRightLink(ActionRequest request, SqlMapper sqlMapper){

        String strReq = "select sc.id" +
                "    FROM scan sc" +
                "    JOIN scan_detail sdt" +
                "    ON sc.id = sdt.Scan_ID" +
                "    AND sc.Route_Points_ID = #{node_id}" +
                "    AND sc.Link_No= #{link_num}" +
                "    AND sc.gc_flag = 0" +
                "    AND sdt.gc_flag = 0" +
                "    AND sdt.Goods_Barcode = #{pack_barcode}";

        return sqlMapper.selectOne(strReq, request);
    }

}
