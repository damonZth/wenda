package com.nowcoder.util;

import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.MessageDigest;

/**
 * Created by Damon on 2017/6/25.
 * md5加密
 */

public class WendaUtil {
    private static final Logger logger = LoggerFactory.getLogger(WendaUtil.class);

    public static String MD5(String key){
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try{
            byte[] btInput = key.getBytes();
            //获取md5摘要算法的MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换为十六进制的自付出形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i = 0; i < j; i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e){
            logger.error("生成MD5失败", e);
            return null;
        }
    }
}
