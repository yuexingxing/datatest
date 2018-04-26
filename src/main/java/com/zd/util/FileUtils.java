package com.zd.util;

import com.zd.common.Constant;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017-05-03.
 */
public class FileUtils {

    public static final String PIC_PATH = "D:/file";

    /**
     * base64转换为图片并保存
     * 先判断是否存在当前日期的文件夹，如果不存在则创建，否则直接保存照片
     * 文件的写操作
     * */
    public void savePic(String strBase64, String name){

        try {
            if(strBase64!=null){

                byte[] b= Base64Coder.decodeLines(strBase64);
                File file=new File(PIC_PATH + "_" + CommandTools.getTimeName());
                if(!file.exists()) {
                    file.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(name + ".png");
                System.out.println(file.getPath());
                fos.write(b);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
