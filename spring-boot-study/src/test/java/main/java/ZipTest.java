package main.java;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import main.util.Base64Util;
//import net.lingala.zip4j.ZipFile;
//import net.lingala.zip4j.exception.ZipException;
//import net.lingala.zip4j.model.FileHeader;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.io.FileUtils.deleteDirectory;

@Slf4j
public class ZipTest {

    @Test
    public void testUnZip() throws IOException {
        String zipFilePath = "C:\\Users\\25446\\Desktop\\eep check\\E230413894226_出入境记录查询结果（电子文件）.zip";
        String destDirectory = "C:\\Users\\25446\\Desktop\\eep check\\unzip\\";
        String password = "970712";
        unzip(zipFilePath, destDirectory, password);
    }

    @Test
    public void testUnzip2() throws IOException {
        FileEntity fileEntity = new FileEntity();
        String sourceFile = "C:\\Users\\25446\\Desktop\\eep check\\E230413894226_出入境记录查询结果（电子文件）.zip";
        Path path = Paths.get(sourceFile);
        byte[] fileBytes = Files.readAllBytes(path);

        fileEntity.setContent(Base64Util.byte2Base64String(fileBytes));
        String userId = "guzx222";
        String password = "970712";
        unzip(fileEntity, userId, password);
//        System.out.println("fileEntity:{}" + fileEntity.toString());
    }


    public static void unzip(String zipFilePath, String destDirectory, String password) throws IOException {
        /*try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }
            destDirectory += UUID.randomUUID().toString().substring(0, 3) + new Date().getTime();
            zipFile.extractAll(destDirectory);

            // 获取解压后的文件
            File unzippedFile = new File(destDirectory);
            File[] files = unzippedFile.listFiles();
            for (File file : files) {
                System.out.println("File name: " + file.getName());
                System.out.println("File size: " + file.length() + " bytes");
                InputStream is = new FileInputStream(file);
                byte[] bytes = is.readAllBytes();
                System.out.println(bytes.length);
                is.close();
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }*/
    }

    private void unzip(FileEntity fileEntity, String userId, String password) throws IOException {
        /*String destDirectory = "/home/work/tmp/zip_file/" + userId + "/";
        Path destDir = Path.of(destDirectory);
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        String zipFileDirectory = destDirectory + new Date().getTime() + ".zip";
        Path zipDir = Path.of(zipFileDirectory);
        if (!Files.exists(zipDir)) {
            Files.createFile(zipDir);
        }

        try (FileOutputStream fos = new FileOutputStream(zipFileDirectory);
             ZipFile zipFile = new ZipFile(zipFileDirectory)) {
            fos.write(Base64Util.base64String2Byte(fileEntity.getContent()));
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }
            zipFile.extractAll(destDirectory);

            // 获取解压后的文件
            File unzippedFile = new File(destDirectory);
            File[] files = unzippedFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        Path path = Paths.get(destDirectory + file.getName());
                        byte[] bytes = Files.readAllBytes(path);
                        fileEntity.setContent(Base64Util.byte2Base64String(bytes));
                        fileEntity.setOriginalFileName(file.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            File unzipDirectory = new File(destDirectory);
            if (unzipDirectory.exists()) {
                File[] files = unzipDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteDirectory(file);
                        } else {
                            file.delete();
                        }
                    }
                }
                unzipDirectory.delete();
            }
        }*/
    }

    @Data
    class FileEntity {
        private String name;
        private String originalFileName;
        private String contentType;
        private long size;
        private String md5Sum;
        private String content;
    }

}
