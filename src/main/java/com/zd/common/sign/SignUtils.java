package com.zd.common.sign;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author wdx
 * @ClassName: SignUtils
 * @Description: 签名工具类
 * @date 2016年4月2日 下午7:20:06
 */

public class SignUtils {

    /**
     * @param @param  parms 请求参数
     * @param @param  appkey
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Method: getMd5Sign
     * @Description: 获得md5签名
     * @author wdx
     * @date 2016年4月2日 下午7:19:27
     **/

    public static String getMd5Sign( Map<String, String> parms, String appkey ) {

        StringBuilder sb = new StringBuilder();
        if (parms == null || parms.size() < 1) {
            return "";
        }
        List<String> keys = new ArrayList<String>(parms.keySet());

        //参数排序 升序
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (parms.get(key) != null) {
                sb.append(key + parms.get(key));
            }
        }
        ;
        return getMd5Sign(sb.toString(), appkey);
    }

    //md5加密
    public static String getMd5Sign( String source, String appkey ) {
        return DigestUtils.md5Hex(source + appkey);
    }

//	public static String getMd5Sign(Map<String,String> parms,String appid,String confusioncode) {
//		StringBuilder sb = new StringBuilder();
//		if (parms == null || parms.size() < 1) {
//			return "";
//		}
//		List<String> keys = new ArrayList<String>(parms.keySet());
//		Collections.sort(keys);
//		for (int i = 0; i < keys.size(); i++) {
//			String key = keys.get(i);
//			if ( parms.get(key)!= null ) {
//				sb.append(key + parms.get(key));
//			}
//		};
//		return getMd5Sign(sb.toString(),appid,confusioncode);
//	}

    /**
     * @Method: getMd5Sign
     * @Description: MD5 sign
     * @param @param source
     * @param @param appid 约定号的appid
     * @param @param confusioncode 6位随机混淆码
     * @param @return    参数
     * @return String    返回类型
     * @throws
     * @author wdx
     * @date 2016年3月31日 下午3:14:25
     **/
//	private static final String privateKey = "9eec69f570eb91f%#$&()sdfsdx.1sfs317444073b3b1f936";
//
//	public static String getMd5Sign(String source,String appid,String confusioncode) {
//		String md5sign=Hashing.md5().newHasher().putString(source, Charsets.UTF_8).putString(confusioncode, Charsets.UTF_8).putString(appid, Charsets.UTF_8).putString(privateKey, Charsets.UTF_8).hash().toString();
//		return new sun.misc.BASE64Encoder().encode(md5sign.getBytes());
//	}
}
