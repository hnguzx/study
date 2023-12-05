package main.java;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class HashTest {

    @Test
    public void testFile() {
        String directoryPath = "C:\\Users\\25446\\Desktop\\CJK";
        String algorithm = "SHA-256";

        try {
            // Create message digest instance
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            // Get list of files in directory
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();

            // Iterate over files and compute hash value, size, and name
            for (File file : files) {
                if (file.isFile()) {
                    // Compute hash value
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[8192];
                    int read = 0;
                    while ((read = inputStream.read(buffer)) > 0) {
                        digest.update(buffer, 0, read);
                    }
                    inputStream.close();
                    byte[] hash = digest.digest();

                    // Convert byte array to hexadecimal string
                    StringBuilder hexString = new StringBuilder();
                    for (byte b : hash) {
                        String hex = Integer.toHexString(0xff & b);
                        if (hex.length() == 1) {
                            hexString.append('0');
                        }
                        hexString.append(hex);
                    }

                    // Get file size and name
                    long size = file.length();
                    String name = file.getName();

                    // Print hash value, size, and name
                    System.out.println("File name: " + name);
                    System.out.println("Hash value: " + hexString.toString());
                    System.out.println("File size: " + size + " bytes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
