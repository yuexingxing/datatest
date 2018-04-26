/**
 * @Title: AESII.java
 * @Package com.zhiduan.axp.web.tools.sign
 * @Project: axp-tools
 * @Description: (用一句话描述该文件做什么)
 * @author wdx
 * @date 2016年3月31日 下午8:06:29
 * @version V1.0
 * ──────────────────────────────────
 * Copyright (c) 2016, 指端科技.
 */

package com.zd.common.sign;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: AES
 * @Description: AES解密工具
 * @author wdx
 * @date 2016年3月31日 下午8:06:29
 *
 */

public class AES {

    public static Logger logger = LoggerFactory.getLogger(AES.class);

    //私钥AES
    //private static final String privatekey = "L+\\~f4,Ir)b$=pkf";

    //私钥MD5
    private static final String privatekey = "L+\\~f4,Ir)b$=pkf";

    public static void setAesFlag( boolean aesFlag ) {
        AES.aesFlag = aesFlag;
    }

    public static boolean isAesFlag() {
        return aesFlag;
    }

    //true 禁用
    private static boolean aesFlag = true;

//	public static void main(String[] args) throws Exception {
//		//产生6位字符串
//		String con=RandomCode.getRandomCode();
//		String publicKey="20160409195443165";
//		//AES加密
//		String username=encrypt("1826\34/05_43+4", publicKey, "TyLnJD");
//		String pwd=encrypt("123456", publicKey, "ztxl55");
//		//AES解密
//		String des=desEncrypt("fPiBL3pBiYYOOSZtWsvajg==TyLnJD", "20160620014926");
//
//		System.out.println("加密字符串:zhanglei");
//		System.out.println("Phone加密后密文："+username);
//		System.out.println("acountPwd加密后密文："+pwd);
//		System.out.println("timestamp公钥："+publicKey);
//		System.out.println("解密后明文"+des);
//		System.out.println(PhoneUtils.isMobile("18263405434"));
//		Integer ll=188;
//		Integer bb=188;
//		System.out.println(ll.equals(bb));
//	}

    /**
     * @Method: desEncrypt
     * @Description: 解密工具
     * @param @param ciphertext 密文（最后6位是混淆码，不需要base64解码）
     * @param @param publickey(endtime规则: 以 100 纳秒为单位表示的当前的日期和时间长整型值的字符串)
     * @param @return
     * @param @throws Exception    参数
     * @return String    返回类型
     * @throws
     * @author wdx
     * @date 2016年3月31日 下午11:45:18 
     **/

    public static String desEncrypt( String ciphertext, String publickey ) throws Exception {
        if (!aesFlag) {
            return ciphertext;
        }
        try {
            int length = ciphertext.length();
            //获取混淆码
            String confusioncode = StringUtils.substring(ciphertext, length - 6, length);
            //截取预解密的 密文
            String cryptText = StringUtils.substring(ciphertext, 0, length - 6);
            //生成公钥，并MD5加密
            String key = publickey + confusioncode + publickey;
            key = MD5II(Base64.encodeBase64String(key.getBytes()));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(privatekey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            //BASE64解码后，解密文
            byte[] original = cipher.doFinal(Base64.decodeBase64(cryptText));
            return new String(original).trim();
        } catch (Exception e) {
            logger.error("AES解密错误", e);
            return null;
        }
    }

    /**
     * @Method: encrypt
     * @Description: 加密算法
     * @param @param plaintext 明文
     * @param @param publickey 公钥
     * @param @param confusioncode 混淆码（6位随机数）
     * @param @return
     * @param @throws Exception    参数
     * @return String    返回类型
     * @throws
     * @author wdx
     * @date 2016年4月1日 上午11:09:36 
     **/

    public static String encrypt( String plaintext, String publickey, String confusioncode ) throws Exception {

        if (!aesFlag) {
            return plaintext;
        }

        try {

            //生成公钥，并MD5加密
            String key = publickey + confusioncode + publickey;
            key = MD5II(Base64.encodeBase64String(key.getBytes()));

            //加密链
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = plaintext.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintextBytes = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintextBytes, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(privatekey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            //加密
            byte[] encrypted = cipher.doFinal(plaintextBytes);


            //BASE64 编码
            return Base64.encodeBase64String(encrypted) + confusioncode;

        } catch (Exception e) {
            logger.error("AES加密错误", e);
            return null;
        }
    }

    /**
     * @Method: MD5
     * @Description: md5加密工具(明文)
     * @param @param plain
     * @param @return    参数
     * @return String    返回类型
     * @throws
     * @author wdx
     * @date 2016年3月31日 下午11:44:55
     **/

//	private static String MD5(String plain) {
//		String re_md5 = new String();
//		MessageDigest md;
//		try {
//			md = MessageDigest.getInstance("md5");
//			md.update(plain.getBytes());
//			byte b[] = md.digest();
//			int i;
//			StringBuffer buf = new StringBuffer("");
//			for (int offset = 0; offset < b.length; offset++) {
//				i = b[offset];
//				if (i < 0)
//					i += 256;
//				if (i < 16)
//					buf.append("0");
//				buf.append(Integer.toHexString(i));
//			}
//
//			re_md5 = buf.toString();
//			
//			System.out.println(re_md5);
//			
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return re_md5;
//	}

    /**
     * @Method: MD5II
     * @Description: md5加密工具(明文)
     * @param @param plain
     * @param @return    参数
     * @return String    返回类型
     * @throws
     * @author wdx
     * @date 2016年3月31日 下午11:44:55
     **/

    public static String MD5II( String plain ) {
        return Hashing.md5().newHasher().putString(plain, Charsets.UTF_8).hash().toString();
    }

//    private static String getRandom(){
//    	return RandomStringUtils.randomAlphanumeric(6);
//    } 

//    public static void main(String[] args) throws Exception {
//    	
//    	String mSign = "c66b1588-f6ab-49d4-b917-6376956b4247";
//    	
//    	String sign="gJaRJc2JEZZ7Ma3avKsfwg==abcdef";
//    	String sendtime="635916883470454300";
//    	
//    	System.out.println(RandomStringUtils.randomAlphanumeric(6));
//
////    	System.out.println(Base64.encodeToString(key.getBytes(), Base64.NO_WRAP));
//    	
//    	
//    	System.out.println(encrypt("11223344", "635916883470454300","J2yRu8"));
//    	
//    	
//    	System.out.println(desEncrypt("VfV21A0fK82PwZ3mrqDMWg==abcdef", sendtime));
//    	
//    }
//
//
//	public static byte[] hexToBytes(String str) {	public static String bytesToHex(byte[] data) {
//		if (data == null) {
//			return null;
//		}
//
//		int len = data.length;
//		String str = "";
//		for (int i = 0; i < len; i++) {
//			if ((data[i] & 0xFF) < 16)
//				str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
//			else
//				str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
//		}
//		return str;
//	}
//		if (str == null) {
//			return null;
//		} else if (str.length() < 2) {
//			return null;
//		} else {
//			int len = str.length() / 2;
//			byte[] buffer = new byte[len];
//			for (int i = 0; i < len; i++) {
//				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
//			}
//			return buffer;
//		}
//	}

    public static String alwaysDesEncrypt( String ciphertext, String publickey ) throws Exception {
        try {
            int length = ciphertext.length();
            //获取混淆码
            String confusioncode = StringUtils.substring(ciphertext, length - 6, length);
            //截取预解密的 密文
            String cryptText = StringUtils.substring(ciphertext, 0, length - 6);
            //生成公钥，并MD5加密
            String key = publickey + confusioncode + publickey;
            key = MD5II(Base64.encodeBase64String(key.getBytes()));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(privatekey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            //BASE64解码后，解密文
            byte[] original = cipher.doFinal(Base64.decodeBase64(cryptText));
            return new String(original).trim();
        } catch (Exception e) {
            logger.error("AES解密错误", e);
            return null;
        }
    }


    public static String alwaysEncrypt( String plaintext, String publickey, String confusioncode ) throws Exception {
        try {
            //生成公钥，并MD5加密
            String key = publickey + confusioncode + publickey;
            key = MD5II(Base64.encodeBase64String(key.getBytes()));

            //加密链
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = plaintext.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintextBytes = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintextBytes, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(privatekey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            //加密
            byte[] encrypted = cipher.doFinal(plaintextBytes);


            //BASE64 编码
            return Base64.encodeBase64String(encrypted) + confusioncode;

        } catch (Exception e) {
            logger.error("AES加密错误", e);
            return null;
        }
    }
}
