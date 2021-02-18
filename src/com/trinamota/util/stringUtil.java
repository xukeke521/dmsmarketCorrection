package com.trinamota.util;

import java.text.DecimalFormat;
import java.util.Random;

public class stringUtil {
	 /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if ((str != null) && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param str
     * @return
     */
    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        } else {
            return null;
        }
    }
    
    /**
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        return String.valueOf(obj);
    }
    
    /**
     * @param obj
     * @return
     */
    public static String intToString(Integer i) {
        if( i== null || i==0)
        {
        	return null;
        }
        else
        {
        	return String.valueOf(i);
        }
    }
    
    
    /**
     * @param Ëæ»ú×Ö·û´®³¤¶È
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    
    
    public static String DoubleToString(Object i) {
    	Double value = Double.valueOf(String.valueOf( i));
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }
}
