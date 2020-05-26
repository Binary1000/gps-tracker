package com.cnqisoft.gps;

/**
 * @author Binary on 2020/5/5
 */
public class Utils {

    public static String byteToHexString(byte b) {
        return Integer.toHexString(b | 0xffffff00).substring(6).toUpperCase();
    }

}
