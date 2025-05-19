package main.java;

//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;

public class PDFTest {
    public static void main(String[] args) {
        String sourceFilePath = "C:\\Users\\25446\\Downloads\\15d_Time deposit application_amendment form_202309_v2.pdf";
//        String sourceFilePath = "C:\\Users\\25446\\Downloads\\Change of Customer Contact form.pdf";
//        String sourceFilePath = "C:\\Users\\25446\\Desktop\\fsdownload\\1667274013376007.pdf";
//        String sourceFilePath = "C:\\Users\\25446\\Desktop\\fsdownload\\2new.pdf";
        String destinationFilePath = "C:\\Users\\25446\\Downloads\\destination.pdf";

        try {
            // 创建PdfReader对象以读取输入PDF文件
//            String password = "";
//            PdfReader reader = new PdfReader(sourceFilePath,new ReaderProperties().setPassword(password.getBytes()));
            PdfReader reader = new PdfReader(sourceFilePath);
            PdfDocument pdfDocument = new PdfDocument(reader);
            PdfVersion pdfVersion = pdfDocument.getPdfVersion();
            // 创建PdfWriter对象以写入输出PDF文件
            PdfWriter writer = new PdfWriter(destinationFilePath);

            // 创建PdfDocument对象，将PdfReader和PdfWriter传递给它
//            PdfDocument pdfDocument = new PdfDocument(reader, writer);
            pdfDocument.getPdfVersion();
            Document document = new Document(pdfDocument);

            // 在这里可以执行对PDF文件的读取和修改操作
            // 例如，可以获取页面数量、提取文本、添加水印等

            // 关闭PdfDocument对象，确保输出文件被保存
            document.close();

            System.out.println("PDF文件读取和重新写入成功！");
        } catch (Exception e) {
            System.out.println("发生错误：" + e);
        }
    }

}