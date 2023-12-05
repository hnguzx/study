package main.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    /**
     * base64字符串转byte[]
     *
     * @param base64Str
     * @return
     */
    public static byte[] base64String2Byte(String base64Str) {

        return Base64.decodeBase64(base64Str);
    }

    /**
     * byte[]转base64
     *
     * @param b
     * @return
     */
    public static String byte2Base64String(byte[] b) {

        return Base64.encodeBase64String(b);
    }
}
