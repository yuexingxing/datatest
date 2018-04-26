package com.zd.common;

import java.util.Properties;

public class SeparatorUtils {

	/* 行分隔符
	 * system properties to get separators */
	static final Properties PROPERTIES = new Properties(System.getProperties());

	/**
	 * get line separator on current platform
	 * @return line separator
	 */
	public static String getLineSeparator() {
		return PROPERTIES.getProperty("line.separator");
	}

	/**
	 * 路径分隔符
	 * get path separator on current platform
	 * @return path separator
	 */
	public static String getPathSeparator() {
		return PROPERTIES.getProperty("path.separator");
	}

	/**
	 * 操作系统路径:
	 * @return
	 */
	public static String getOSname() {
		return PROPERTIES.getProperty("os.name");
	}

}
