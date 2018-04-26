package com.zd.dao;

import com.zd.common.ConvertDataSchemaUtils;
import com.zd.object.Header;
import com.zd.object.ResultInfo;
import com.zd.object.ScanDataListEnt;
import com.zd.pojo.AttachmentInfo;
import com.zd.pojo.ExceptionData;
import com.zd.pojo.ScanData;
import com.zd.util.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017-05-26.
 */
public class PictureDao {

    private static final Logger logger = LoggerFactory.getLogger(PictureDao.class);

    public ResultInfo save(ScanData scanData, AttachmentInfo attachmentInfo, SqlMapper sqlMapper){

        ResultInfo resultInfo = new ResultInfo();

        try {
            attachmentInfo = ConvertDataSchemaUtils.Conver2Scan_Attachment(scanData, attachmentInfo);

            String SQL = " insert into Attachment(" +
                    " ID,Upload_Time,Attachment_Type,Main_ID," +
                    " Attachment_Url,Attachment_Path,Sort_No," +
                    " Created_UserID,Created_User,Created_Time,GC_Flag," +
                    " Updated_User,Updated_Time) " +
                    " values(" +
                    "#{id},#{upload_time},#{attachment_type},#{main_id}," +
                    "#{attachment_url},#{attachment_path},#{sort_no}," +
                    "#{created_userid},#{created_user},#{created_time},#{gc_flag}," +
                    "#{created_user},#{created_time})";

            int insertCnt = sqlMapper.insert(SQL, attachmentInfo);
            logger.debug("save_data.insertCnt(插入记录数量)=" + insertCnt);
            logger.debug("图片保存成功" + scanData.getReturnedCargoFile());
        }catch(Exception e){
            e.printStackTrace();
            resultInfo.setSuccess(-1);
            resultInfo.setMessage("PictureDao.save()图片保存异常");
        }

        return  resultInfo;
    }
}
