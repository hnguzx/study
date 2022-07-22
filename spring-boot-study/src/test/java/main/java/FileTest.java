//package main.java;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import main.controller.DemoController;
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.util.ResourceUtils;
//
//import java.io.*;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(DemoController.class)
//public class FileTest {
//
//    @Value("${response.path}")
//    private String messagePath;
//
//    @Test
//    public void fileTest() throws Exception {
//        String entityType = "partnership";//local_limited,sole_proprietorship
//        String response = null;
//        String methodName = "createTicket";//getTicketInfo
//        String ticketId = "85700288-20f6-464e-95e0-qwe123qwe12";
//        if ("createTicket".equals(methodName)) {
//            // 发起查册
//            int v = (int) (Math.ceil(Math.random() * 3.0));
//            String fileName = entityType + "_" + v;
//            log.info("发起查册,获取挡板文件：{}", fileName);
//            response = FileTest.readLocalFile(messagePath, fileName);
//        } else {
//            // 获取查册结果
//            log.info("获取查册结果文件：{}", ticketId);
//            response = FileTest.readLocalFile(messagePath, ticketId);
//        }
//
//        log.info("response:{}", response);
//    }
//
//    @Test
//    public void random() {
//        for (int i = 0; i < 100; i++) {
//            int v = (int) (Math.ceil(Math.random() * 3.0));
//            log.info("随机数：{}", v);
//        }
//    }
//
//    /**
//     * 读取虚拟报文
//     *
//     * @return String
//     */
//    public static String readLocalFile(String virtualPkgPath, String hostCode) throws Exception {
//        InputStream is = null;
//        try {
//            File file = ResourceUtils.getFile(virtualPkgPath + hostCode + ".txt");
//            is = new FileInputStream(file);
//            return IOUtils.toString(is);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != is) {
//                    is.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return virtualPkgPath;
//    }
//
//    /**
//     * 获取指定目录下所有文件的文件名
//     */
//    @Test
//    public void getFileNameList() throws IOException {
//
////        File eportalRootDir = new File("F:\\air star\\workspace\\eportal");
////        File eportalJobRootDir = new File("F:\\air star\\workspace\\eportal-job");
////        File innerRootDir = new File("F:\\air star\\workspace\\inner");
//        File eportalWebRootDir = new File("F:\\air star\\web\\eportal-web");
//        File innerWebRootDir = new File("F:\\air star\\web\\inner-web");
//        File targetFile = new File("F:\\air star\\file\\FileList.txt");
////        getFile(eportalRootDir);
////        getFile(eportalJobRootDir);
////        getFile(innerRootDir);
//        getFile(eportalWebRootDir, targetFile);
//        getFile(innerWebRootDir, targetFile);
//    }
//
//    public void getFile(File file, File targetFile) throws IOException {
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//            if (file1.isDirectory()) {
//                getFile(file1, targetFile);
//            } else {
//                String path = file1.getAbsolutePath().substring(16);
//                log.info("AbsolutePath:{}", path);
//                if (!excludeFiles(path)) {
//                    FileOutputStream outputStream = new FileOutputStream(targetFile, true);
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//                    BufferedWriter writer = new BufferedWriter(outputStreamWriter);
//                    writer.write(path + "\r\n");
//                    writer.close();
//                }
//            }
//        }
//    }
//
//    public boolean excludeFiles(String fileName) {
//        boolean result = false;
//        if (fileName.contains("log") || fileName.contains("git") || fileName.contains("libraries") || fileName.endsWith(".class")
//                || fileName.contains("target") || fileName.contains("dist") || fileName.contains("node_modules") || fileName.contains("mocks")
//                || fileName.contains("tests")) {
//            result = true;
//        }
//        return result;
//    }
//
//    /**
//     * 将文件转为二进制文件
//     */
//    @Test
//    public void binaryFile() throws IOException {
//        File file = new File("F:\\air star\\waterMark\\2.jpeg");
//        String s = fileToBinStr(file);
//        log.info("file binary:{}", s);
//
//
//    }
//
//    /**
//     * 文件转二进制数组
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    public static byte[] fileToBytes(File file) throws IOException {
//        InputStream inputStream = new FileInputStream(file);
//        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
//        return bytes;
//    }
//
//    /**
//     * 文件转二进制字符串
//     *
//     * @param file
//     * @return
//     */
//    public static String fileToBinStr(File file) throws IOException {
//        InputStream inputStream = new FileInputStream(file);
//        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
//        return new String(bytes, "ISO-8859-1");
//    }
//}
