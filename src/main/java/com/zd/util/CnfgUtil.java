package com.zd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.zd.common.SeparatorUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.extra.spath.Path;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class CnfgUtil {

	private static Logger logger = Logger.getLogger(CnfgUtil.class);

	/**
	 * 读取配置文件
	 * String picfile_basepath = CnfgUtil.readCnfg("cnfg.xml", "picfile_basepath");
	 * */
	public static String readCnfg(String cnfgFile, String key) {

		String keyvalue = "";
		String workPath = Get_workPath();
		Path cnfgPath = (Path) Paths.get(workPath, cnfgFile);

		String cnfg_xml_path = cnfgPath.toString();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(cnfg_xml_path));
			NodeList serverslist = doc.getElementsByTagName("server");
			if (serverslist != null) {
				for (int i = 0; i < serverslist.getLength(); i++) {
					//serverslist.item(0)
					//					String tmp = doc.getElementsByTagName(key).toString();
					//					logger.debug("tmp=" + tmp);
					keyvalue = doc.getElementsByTagName(key).item(i).getFirstChild().getNodeValue();
				}
			}

		} catch (Exception e) {
			logger.error("读取配置文件异常:" + e.getMessage());
		}



		return keyvalue;
	}

	/**
	 * 获取当前程序目录;
	 */
	public static String Get_workPath() {
		String inf_dirPath = "";
		File directory = new File("");//设定为当前文件夹
		try {
			inf_dirPath = directory.getCanonicalPath();
		} catch (Exception e) {
		}

		String osname = SeparatorUtils.getOSname().toUpperCase();
		if (osname.contains("WINDOWS")) {
			// windows ...
			inf_dirPath = inf_dirPath.replace("classes\\", "").replace("/", "\\");
			if (inf_dirPath.startsWith("\\")) {
				inf_dirPath = inf_dirPath.substring(1, inf_dirPath.length());
				inf_dirPath = inf_dirPath.replace("classes\\", "");
			}
		} else {
			// Linux 或者 mac 系统:
			inf_dirPath = inf_dirPath.replace("classes/", "");
		}
		return inf_dirPath;
	}


}
