//package main.java;
//
//import com.itextpdf.text.DocumentException;
//import lombok.extern.slf4j.Slf4j;
//import main.util.WaterMarkUtils;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Slf4j
//public class ImageAddWaterMark {
//
//    @Autowired
//    WaterMarkUtils utils = new WaterMarkUtils();
//
//    /**
//     * 添加单个水印
//     *
//     * @throws Exception
//     */
//    @Test
//    public void imageAddWater() throws Exception {
////        File file1 = new File("F:/air star/waterMark/HKID_Sample.png");
////        File file2 = new File("F:/air star/waterMark/HKID_Sample_GIF.gif");
////        File file3 = new File("F:/air star/waterMark/HKID_Sample_JP.jpg");
////        File file4 = new File("F:/air star/waterMark/HKID_Sample_JPEG.jpeg");
////        WaterMarkUtils.mark(file1, "png", Color.getHSBColor(205, 205, 205), "copy");
////        WaterMarkUtils.mark(file2, "png", Color.getHSBColor(205, 205, 205), "copy");
////        WaterMarkUtils.mark(file3, "png", Color.getHSBColor(205, 205, 205), "copy");
////        WaterMarkUtils.mark(file4, "png", Color.getHSBColor(205, 205, 205), "copy");
////        WaterMarkUtils.setPdfWatermark("F:/air star/waterMark/HKID_Sample_PDF.pdf", "copy");
//
////        File file1 = new File("F:/air star/waterMark/PASSPORT_SAMPLE.jpg");
////        WaterMarkUtils.mark(file1, "png", Color.getHSBColor(205, 205, 205), "copy");
////        WaterMarkUtils.setPdfWatermark("F:/air star/waterMark/HKID_Sample_PDF.pdf", "copy");
////        WaterMarkUtils.setPdfWatermark("F:/air star/waterMark/PASSPORT_SAMPLE.pdf", "copy");
//
////        WaterMarkUtils.setPdfWatermark("F:/air star/waterMark/test-pdf.pdf","copy");
//
////        File file = new File("F:/air star/waterMark/waterMark2.jpg");
////        File file1 = new File("F:/air star/waterMark/HKID_Sample.png");
////        File file2 = new File("F:/air star/waterMark/HKID_Sample_JPEG.jpeg");
//        File file = new File("F:/air star/waterMark/HKID_Sample_PDF.pdf");
//        FileInputStream inputStream = new FileInputStream(file);
//        File file1 = WaterMarkUtils.setPdfWatermark(inputStream, "copy");
//        log.info(file1.getAbsolutePath());
//    }
//
//    /**
//     * 添加图片水印
//     */
//    @Test
//    public void addImageWater() {
//        utils.markImageByIcon("F:/Images/mark.png", "F:/Images/t1.png", "F:/Images/t3.png");
//    }
//
//    /**
//     * pdf添加水印
//     */
//    /*@Test
//    public void pdfAddWater() throws DocumentException, IOException, InterruptedException {
////        File pdf = new File("F:/air star/waterMark/HKID_Sample_PDF.pdf");
////        FileInputStream is = new FileInputStream(pdf);
////        Long pdfSize = pdf.length();
////        String pdfName = pdf.getName();
////        log.info("原 pdf name为：{}，文件大小为：{}，inputStream是否为null：{}", pdfName, pdfSize, is == null);
////        File markPdf = WaterMarkUtils.setPdfWatermark(is, "copy");
////        log.info("新 pdf name为：{}，文件大小为：{}", markPdf.getName(), markPdf.length());
//
////        File image = new File("F:/air star/waterMark/HKID_Sample.png");
////        File image = new File("F:/air star/waterMark/2.jpeg");
//        File image = new File("F:/air star/waterMark/hasWaterMark.jpeg");
//        String imageName = image.getName();
//        Long imageSize = image.length();
//        FileInputStream inputStream = new FileInputStream(image);
//
//        log.info("原 image name为：{}，文件大小为：{}，inputStream是否为null：{}", imageName, imageSize, inputStream == null);
//        File markImage = WaterMarkUtils.mark(image, imageName, "copy");
//        log.info("新 image 文件大小为：{}", markImage.length());
//
//    }*/
//
//
//}
