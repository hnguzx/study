package main.java;

import com.google.common.base.Strings;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA256Util {
    private final static String SECRET_KEY = "r9bcvu7q7CIpMxB65LlEHhP_UDMC8RAPn_S76eodn9X9qtqlZwjun5L8dW7mLgCnPO0y7ADSG4uSHb_QrxA8N9DpeRvQtjHE0oCkbjB_D68ViGFbTzzQE0CNJRKuuzI3bS8vHTQnoGaZkc4b2P9kdqWg-pas6DDr_MNOvzb4HzY";

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {

        StringBuilder hs = new StringBuilder();
        String temp;
        for (int n = 0; b != null && n < b.length; n++) {
            temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs.append('0');
            }
            hs.append(temp);
        }
        return hs.toString().toLowerCase();

    }

    /**
     * sha256_HMAC加密
     *
     * @param content
     * @param secret
     * @return
     */
    public static String sha256_HMAC(String content, String secret) {

        String hash = "";
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] bytes = mac.doFinal(content.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.err.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * @param content
     * @param secret
     * @param encryptData
     * @return
     */
    public static boolean verifySha256_HMAC(String content, String secret, String encryptData) {

        boolean verifyResult = false;

        if (Strings.isNullOrEmpty(encryptData)) {
            return verifyResult;
        }
        if (encryptData.equals(HMACSHA256Util.sha256_HMAC(content, secret))) {
            verifyResult = true;
        }
        return verifyResult;
    }

    public static void main(String[] args) {

        String content = "accountType=0&advertisingId=63c6535d-7701-463a-8654-91e3cf200621&crop=true&deviceDetails=jspXEfLjGfesPbfyUauv0w==;A736BXXU2BVK2&deviceIsPhysical=true&deviceModel=SM-A736B&deviceName=samsungSM-N22000&deviceOs=android&deviceType=android&deviceVersion=13.0&encDeviceId=ybzXue11/9Zaxe+Q/wcY/r2/gv9bzcSrzOR7v2ZheuA=&idAngle2Image=8f0f480ca1b50d756f17f1df58113ee2&idAngle2ImageBg=bd74702914830ea0bfece32facc82e21&idAngle3Image=cae91fef3eb1c257a8c76f1ebeaefc44&idAngle3ImageBg=0a8ea15120cadee5b13617e1a7e01694&idFlashImage=2bb9017cb9cce49b76a3e4d302a29696&idFlashImageBg=d5e7d2fec3886f720fa50db016202b51&idHeadImage=29f6df40a663b6c2536227df3e8923f7&idImage=4e5716cb55e6b0c9ab9be96b180e58b7&idImageBg=5e4516c9b27defb6a39f1c35dab4fad1&idVersion=2018&lang=zh_CN&phone=78113235&pkgVersionCode=2023030520&pkgVersionName=5.5.0&processStartTime=&registerToken=fORyC6xkRDyuuJ7cZUYREr:APA91bG5nAbiw-mmQj_PdMsga1gxOKT6Ixg6cWj1SUVbUkZ_hMaKpCbz0a6kWor_09vekOohX9iE3BHT5y7K9-iDmF9WTW53f-pyFPjDNiKDUQxTiSFEFn8lF23FymbFDhmuAAP9kyqk&scanIdCardTime=&screenHeight=805.3333333333334&screenWidth=384.42857142857144&serviceId=app&skip=true&theme=dark&timestamp=1688550567144&timezone=8:00:00.000000";
        String encryptData = HMACSHA256Util.sha256_HMAC(content, SECRET_KEY);
        System.out.println(encryptData);

        boolean verifyResult = HMACSHA256Util.verifySha256_HMAC(content, SECRET_KEY, encryptData);
        System.out.println("verifyResult = " + verifyResult);
    }
}
