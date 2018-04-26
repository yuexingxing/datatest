package com.zd.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zd.pojo.*;

import java.io.*;

/*

 */
public class FileUtils {
    public static List<flinfo> listFileBySuffix( String filePath, String suffix ) {
        File directory = new File(filePath);
        List<flinfo> aim_list = new ArrayList<flinfo>();
        File[] lists = directory.listFiles(new FileNameSelector(suffix));
        if (lists != null) {
            for (int i = 0; i < lists.length; i++) {
                File tmp_file = lists[i];
                flinfo ent = new flinfo();
                ent.set_flName(tmp_file.toString());
                aim_list.add(ent);
            }
        }
        return aim_list;
    }

    /**
     * 创建指定的目录
     *
     * @param destDirName
     * @return
     */
    public static boolean createDir( String destDirName ) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
            dir = new File(destDirName);
        }

        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    public static String readFile( String flName ) {
        String FileContent = "";
        try {
            FileInputStream fis = new FileInputStream(flName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                FileContent += line;
                FileContent += "\r\n"; // 补上换行符
            }
        } catch (Exception ex) {
            FileContent = ex.getMessage();
        }
        return FileContent;
    }

}
