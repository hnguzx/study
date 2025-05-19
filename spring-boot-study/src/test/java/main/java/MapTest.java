//package main.java;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.*;
//import org.junit.jupiter.api.Test;
//import ws.schild.jave.Encoder;
//import ws.schild.jave.EncoderException;
//import ws.schild.jave.MultimediaObject;
//import ws.schild.jave.encode.AudioAttributes;
//import ws.schild.jave.encode.EncodingAttributes;
//import ws.schild.jave.encode.VideoAttributes;
//import ws.schild.jave.filtergraphs.FilterAndWatermark;
//import ws.schild.jave.filtergraphs.TrimFadeAndWatermark;
//import ws.schild.jave.filters.*;
//
//import javax.imageio.ImageIO;
//import javax.sound.sampled.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.*;
//import java.util.List;
//
//public class MapTest {
//
//    @Test
//    void testHashMapAndTreeMap() {
//        Map<String, String> map = new HashMap<>();
//        map.put("c", "carrot");
//        map.put("b", "banana");
//        map.put("a", "apple");
//        map.put("f", "fish");
//        map.put("d", "date");
//        map.put("e", "egg");
//
//
//        /*Set<String> keySet = map.keySet();
//        for (String key : keySet) {
//            System.out.println("key: " + key + " value: " + map.get(key));
//        }*/
//        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> next = iterator.next();
//            System.out.println("key: " + next.getKey() + " value: " + next.getValue());
//        }
//
//
//        Map<String, String> treeMap = new TreeMap<>();
//        treeMap.put("c", "carrot");
//        treeMap.put("b", "banana");
//        treeMap.put("a", "apple");
//        treeMap.put("f", "fish");
//        treeMap.put("d", "date");
//        treeMap.put("e", "egg");
//
//        /*Set<String> stringSet = treeMap.keySet();
//        for (String key : stringSet) {
//            System.out.println("key: " + key + " value: " + treeMap.get(key));
//        }*/
//
//        Iterator<Map.Entry<String, String>> iterator1 = treeMap.entrySet().iterator();
//        while (iterator1.hasNext()) {
//            Map.Entry<String, String> next = iterator1.next();
//            System.out.println("key: " + next.getKey() + " value: " + next.getValue());
//        }
//    }
//
//    /**
//     * 给PDF文件添加文字水印
//     *
//     * @throws IOException
//     * @throws DocumentException
//     */
//    @Test
//    void testPDF() throws IOException, DocumentException {
//        // 需要添加水印的文件
//        PdfReader reader = new PdfReader("D:\\file\\input.pdf");
//        // 修改文件
//        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:\\file\\output.pdf"));
//        // 水印文本
//        String watermarkText = "CONFIDENTIAL";
//        // 水印字体
//        BaseFont font = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
//        PdfContentByte content;
//        PdfGState gs = new PdfGState();
//        // 水印透明度
//        gs.setFillOpacity(0.5f);
//        gs.setStrokeOpacity(0.5f);
//        // pdf尺寸
//        Rectangle pageSize = reader.getPageSize(0);
//        float height = pageSize.getHeight();
//        float width = pageSize.getWidth();
//        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
////            content = stamper.getOverContent(i);
//            content = stamper.getUnderContent(i);
//            content.beginText();
//            content.setFontAndSize(font, 50);
//            content.setGState(gs);
//            content.showTextAligned(Element.ALIGN_CENTER, watermarkText, 100, 100, 45);
//            content.endText();
//        }
//        stamper.close();
//        reader.close();
//    }
//
//    @Test
//    void testImage() throws IOException {
//        // Load the original image
//        BufferedImage image = ImageIO.read(new File("D:\\file\\image\\huitailang.jpg"));
//
//        // Create a graphics object to draw the watermark
//        Graphics2D g2d = image.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//
//        // Set the font and color of the watermark text
//        Font font = new Font("Arial", Font.BOLD, 36);
//        g2d.setFont(font);
//        g2d.setColor(Color.WHITE);
//
//        // Set the position of the watermark text
//        String watermark = "Watermark";
//        int x = image.getWidth() - g2d.getFontMetrics().stringWidth(watermark) - 10;
//        int y = image.getHeight() - 10;
//
//        // Draw the watermark text
//        g2d.drawString(watermark, x, y);
//
//        // Save the watermarked image
//        ImageIO.write(image, "png", new File("D:\\file\\image\\result.jpg"));
//    }
//
//    @Test
//    void testMusic() {
//        String inputFilePath = "D:\\KuGou\\李荣浩 - 贝贝.mp3";
//        String outputFilePath = "D:\\KuGou\\result.mp3";
//        String watermarkFilePath = "D:\\KuGou\\watermark.mp3";
//
//        try {
//            // Load input and watermark files
//            File input = new File(inputFilePath);
//            File watermark = new File(watermarkFilePath);
//
//            // Create output file
//            File output = new File(outputFilePath);
//
//            // Create audio attributes
//            AudioAttributes audioAttributes = new AudioAttributes();
//            audioAttributes.setCodec("libmp3lame");
//            audioAttributes.setBitRate(new Integer(128000));
//            audioAttributes.setChannels(new Integer(2));
//            audioAttributes.setSamplingRate(new Integer(44100));
//
//            // Create encoding attributes
//            EncodingAttributes encodingAttributes = new EncodingAttributes();
//            encodingAttributes.setOutputFormat("mp3");
//            encodingAttributes.setAudioAttributes(audioAttributes);
//
//            // Create audio filter graph
//            FilterGraph filterGraph = new FilterGraph();
//            FilterChain chain = new FilterChain();
//            filterGraph.addChain(chain);
////            ConcatFilter concatFilter = new ConcatFilter();
//
////            filterGraph.addFile(input);
////            filterGraph.addFile(watermark);
////            filterGraph.setAudioPad(true);
////            filterGraph.setAudioMix(true);
//
//            // Create encoder
////            Encoder encoder = new Encoder(new MyFFmpegLocator());
////            encoder.encode(filterGraph, output, encodingAttributes);
//
//            System.out.println("Audio watermarking complete.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
