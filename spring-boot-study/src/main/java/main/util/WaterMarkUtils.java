package main.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import io.github.biezhi.webp.WebpIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WaterMarkUtils {

    /**
     * 图片添加水印 单个水印
     *
     * @param oldFile          水印文字的颜色
     * @param waterMarkContent 水印的文字
     * @throws IOException
     */
    public static File mark(File oldFile, String fileName, String waterMarkContent) throws IOException {
        File file = null;
        Color markContentColor = Color.getHSBColor(205, 205, 205);
        String tempFileName = UUID.randomUUID().toString().substring(0, 8).replace("-", "") + ".";
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        String outImgPath = tempFileName + extName;
//        ImageIO.setUseCache(false);

        String fileType = ImgeMimeTypeUtil.getMimeType(new FileInputStream(oldFile), oldFile.length());
        int srcImgWidth = 0, srcImgHeight = 0;
        Image srcImg = null;
        try {
            if (fileType != null && "image/webp".equals(fileType)) {
                byte[] bytes = new byte[64];
                new FileInputStream(oldFile).read(bytes, 0, bytes.length);
                srcImgWidth = ((int) bytes[27] & 0xff) << 8 | ((int) bytes[26] & 0xff);
                srcImgHeight = ((int) bytes[29] & 0xff) << 8 | ((int) bytes[28] & 0xff);
                file = new File(outImgPath);
                file.createNewFile();
                WebpIO webpIO = new WebpIO();
                webpIO.toNormalImage(oldFile, file);
                srcImg = ImageIO.read(file);
            } else {
                BufferedImage image = ImageIO.read(new FileInputStream(oldFile));
                srcImgWidth = image.getWidth(null);
                srcImgHeight = image.getHeight(null);
                srcImg = ImageIO.read(oldFile);
            }

            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
            // Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("微软正黑体", Font.PLAIN, 20);
            // 根据图片的背景设置水印颜色
            g.setColor(markContentColor);

            g.setFont(font);
            g.shear(0.1, -0.5);
            int x = -srcImgWidth, y = -srcImgHeight;
            while (y < srcImgHeight * 2) {
                while (x < srcImgWidth) {
                    g.drawString(waterMarkContent, x, y);
                    x += 80;
                }
                x = -srcImgWidth;
                y += 80;
            }
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, extName, outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (FileNotFoundException e) {
            log.info("图片文件添加水印失败，文件没找到");
            e.printStackTrace();
            throw e;
        }
        if (fileType != null && "image/webp".equals(fileType)) {
            return file;
        } else {
            return new File(outImgPath);
        }
    }

    public static int convertWebp(File originFile, File filePath) {
        String dwebp = "dwebp";
        List<String> commands = new ArrayList<String>();
        commands.add(dwebp);
        commands.add(originFile.getPath());
        commands.add("-o");
        commands.add(filePath.getPath());
        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            Process start = builder.start();
            int i = start.waitFor();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void convertPngToJpg(FileInputStream fileInputStream, File oldFile, int width, int height) {
        try {
            BufferedImage bufferedImage = ImageIO.read(fileInputStream);
            BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
            ImageIO.write(newBufferedImage, "jpg", oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将{@link BufferedImage}生成formatName指定格式的图像数据
     *
     * @param source
     * @param formatName 图像格式名，图像格式名错误则抛出异常
     * @return
     */
    public static byte[] wirteBytes(BufferedImage source, String formatName) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Graphics2D g = null;
        try {
            for (BufferedImage s = source; !ImageIO.write(s, formatName, output); ) {
                if (null != g)
                    throw new IllegalArgumentException(String.format("not found writer for '%s'", formatName));
                s = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
                g = s.createGraphics();
                g.drawImage(source, 0, 0, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != g)
                g.dispose();
        }
        return output.toByteArray();
    }

    /**
     * 获取水印文字总长度
     *
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /**
     * 给图片添加水印
     * 图片水印
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath, Integer degree) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0,
                    0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();

            float alpha = 0.5f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 表示水印图片的位置
            g.drawImage(img, 150, 300, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加Icon印章。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * pdf设置文字水印
     *
     * @param fileInputStream
     * @param markStr
     * @throws DocumentException
     * @throws IOException
     */
    public static File setPdfWatermark(FileInputStream fileInputStream, String markStr) throws DocumentException, IOException {

        String tempFileName = UUID.randomUUID().toString().substring(0, 8).replace("-", "") + ".";
        String outImgPath = tempFileName + "pdf";
        File file = new File(outImgPath);
        BufferedOutputStream bufferOut = new BufferedOutputStream(new FileOutputStream(file));

        PdfReader reader = new PdfReader(fileInputStream);
        PdfStamper stamper = new PdfStamper(reader, bufferOut);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;

        String PATH_FONT_COUR = "F:/air star/waterMark/msjh.ttc,0";
        BaseFont base = BaseFont.createFont(PATH_FONT_COUR, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        PdfGState gs = new PdfGState();

        com.itextpdf.text.Rectangle pageSize = reader.getPageSize(1);
        float height = pageSize.getHeight();
        float width = pageSize.getWidth();
        for (int i = 1; i < total; i++) {

            gs.setFillOpacity(0.2f);
            int x = 0, y = 0;
            while (y < height + 80) {
                while (x < width * 2) {
                    // content = stamper.getUnderContent(i);// 在内容下方加水印
                    content = stamper.getOverContent(i);// 在内容上方加水印
                    content.beginText();
                    content.setGState(gs);
                    content.setRGBColorFill(0, 0, 0);
                    content.setFontAndSize(base, 18);
                    content.showTextAligned(Element.ALIGN_LEFT, markStr, x, y, 50);
                    content.endText();
                    x += 80;
                }
                x = 0;
                y += 80;
            }
        }
        stamper.close();
        bufferOut.flush();
        bufferOut.close();
        return file;
    }


    /**
     * word文字水印
     *
     * @param inputPath
     * @param outPath
     * @param markStr
     */
    public static void setWordWaterMark(String inputPath, String outPath, String markStr, String fileType)
            throws Exception {
        if ("docx".equals(fileType)) {
            File inputFile = new File(inputPath);
            XWPFDocument doc = null;
            try {
                doc = new XWPFDocument(new FileInputStream(inputFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            XWPFParagraph paragraph = doc.createParagraph();
            // XWPFRun run=paragraph.createRun();
            // run.setText("The Body:");
            // create header-footer
            XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
            if (headerFooterPolicy == null) {
                headerFooterPolicy = doc.createHeaderFooterPolicy();
            }
            // create default Watermark - fill color black and not rotated
            headerFooterPolicy.createWatermark(markStr);
            // get the default header
            // Note: createWatermark also sets FIRST and EVEN headers
            // but this code does not updating those other headers
            XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
            paragraph = header.getParagraphArray(0);
            System.out.println(paragraph.getCTP().getRArray(0));
            System.out.println(paragraph.getCTP().getRArray(0).getPictArray(0));
            // get com.microsoft.schemas.vml.CTShape where fill color and
            // rotation is set
            org.apache.xmlbeans.XmlObject[] xmlobjects = paragraph.getCTP().getRArray(0).getPictArray(0)
                    .selectChildren(new javax.xml.namespace.QName("urn:schemas-microsoft-com:vml", "shape"));
            if (xmlobjects.length > 0) {
                com.microsoft.schemas.vml.CTShape ctshape = (com.microsoft.schemas.vml.CTShape) xmlobjects[0];
                // set fill color
                // ctshape.setFillcolor("#d8d8d8");
                ctshape.setFillcolor("#CC00FF");
                // set rotation
                ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
                // System.out.println(ctshape);
            }
            File file = new File(outPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                doc.write(new FileOutputStream(file));
                doc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("doc".equals(fileType)) {

        }

    }

    /*
     * 为Excel打上水印工具函数 请自行确保参数值，以保证水印图片之间不会覆盖。 在计算水印的位置的时候，并没有考虑到单元格合并的情况，请注意
     * @param wb Excel Workbook
     * @param sheet 需要打水印的Excel
     * @param waterRemarkPath 水印地址，classPath，目前只支持png格式的图片，
     * 因为非png格式的图片打到Excel上后可能会有图片变红的问题，且不容易做出透明效果。
     * 同时请注意传入的地址格式，应该为类似："\\excelTemplate\\test.png"
     * @param startXCol 水印起始列
     * @param startYRow 水印起始行
     * @param betweenXCol 水印横向之间间隔多少列
     * @param betweenYRow 水印纵向之间间隔多少行
     * @param XCount 横向共有水印多少个
     * @param YCount 纵向共有水印多少个
     * @param waterRemarkWidth 水印图片宽度为多少列
     * @param waterRemarkHeight 水印图片高度为多少行
     * @throws IOException
     */
    public static void putWaterRemarkToExcel(Workbook wb, Sheet sheet, String waterRemarkPath, int startXCol,
                                             int startYRow, int betweenXCol, int betweenYRow, int XCount, int YCount, int waterRemarkWidth,
                                             int waterRemarkHeight) throws IOException {

        // 校验传入的水印图片格式
        if (!waterRemarkPath.endsWith("png") && !waterRemarkPath.endsWith("PNG")) {
            throw new RuntimeException("向Excel上面打印水印，目前支持png格式的图片。");
        }

        // 加载图片
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        InputStream imageIn = new FileInputStream(waterRemarkPath);
        // InputStream imageIn =
        // Thread.currentThread().getContextClassLoader().getResourceAsStream(waterRemarkPath);
        if (null == imageIn || imageIn.available() < 1) {
            throw new RuntimeException("向Excel上面打印水印，读取水印图片失败(1)。");
        }
        BufferedImage bufferImg = ImageIO.read(imageIn);
        if (null == bufferImg) {
            throw new RuntimeException("向Excel上面打印水印，读取水印图片失败(2)。");
        }
        ImageIO.write(bufferImg, "png", byteArrayOut);

        // 开始打水印
        Drawing drawing = sheet.createDrawingPatriarch();

        // 按照共需打印多少行水印进行循环
        for (int yCount = 0; yCount < YCount; yCount++) {
            // 按照每行需要打印多少个水印进行循环
            for (int xCount = 0; xCount < XCount; xCount++) {
                // 创建水印图片位置
                int xIndexInteger = startXCol + (xCount * waterRemarkWidth) + (xCount * betweenXCol);
                int yIndexInteger = startYRow + (yCount * waterRemarkHeight) + (yCount * betweenYRow);
                /*
                 * 参数定义： 第一个参数是（x轴的开始节点）； 第二个参数是（是y轴的开始节点）； 第三个参数是（是x轴的结束节点）；
                 * 第四个参数是（是y轴的结束节点）； 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                 * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）； 第七个参数是（图片宽度，共多少列）；
                 * 第8个参数是（图片高度，共多少行）；
                 */
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger, yIndexInteger,
                        xIndexInteger + waterRemarkWidth, yIndexInteger + waterRemarkHeight);

                Picture pic = drawing.createPicture(anchor,
                        wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                pic.resize();
            }
        }
    }

    /**
     * 根据文字生成水印图片
     *
     * @param content
     * @param path
     * @throws IOException
     */
    public static void createWaterMarkImage(String content, String path) throws IOException {
        Integer width = 300;
        Integer height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
        Integer fontSize = 50;
        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // g2d.setColor(new Color(0, 0, 0, 80)); //设置字体颜色和透明度
        g2d.setColor(new Color(255, 180, 0, 80)); // 设置字体颜色和透明度
        g2d.setStroke(new BasicStroke(1)); // 设置字体
        g2d.setFont(font); // 设置字体类型 加粗 大小
        g2d.rotate(Math.toRadians(-10), (double) image.getWidth() / 2, (double) image.getHeight() / 2);// 设置倾斜度
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        // 写入水印文字原定高度过小，所以累计写水印，增加高度
        g2d.drawString(content, (int) x, (int) baseY);
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        ImageIO.write(image, "png", new File(path));
    }

    /**
     * excel设置水印
     *
     * @param inputPath
     * @param outPath
     * @param markStr
     */
    public static void setExcelWaterMark(String inputPath, String outPath, String markStr) throws Exception {
        // 读取excel文件
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(inputPath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // 设置水印图片路径

        String imgPath = "";
        try {
            createWaterMarkImage(markStr, imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // 获取excel sheet个数
        int sheets = wb.getNumberOfSheets();
        // 循环sheet给每个sheet添加水印
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = wb.getSheetAt(i);
            // excel加密只读
            // sheet.protectSheet(UUID.randomUUID().toString());
            // 获取excel实际所占行
            // int row = sheet.getFirstRowNum() + sheet.getLastRowNum();
            int row = 0;
            // 获取excel实际所占列
            int cell = 0;
            /*
             * if(null != sheet.getRow(sheet.getFirstRowNum())) { cell =
             * sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() + 1; }
             */

            // 根据行与列计算实际所需多少水印
            try {
                putWaterRemarkToExcel(wb, sheet, imgPath, 0, 0, 5, 5, cell / 5 + 1, row / 5 + 1, 0, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] content = os.toByteArray();
        // Excel文件生成后存储的位置。
        File file = new File(outPath);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File imageTempFile = new File(imgPath);
        if (imageTempFile.exists()) {
            imageTempFile.delete();
        }
    }

    // 改变所有文本，不改变样式
    public static void setPPTWaterMark(String path, String targetpath, String markStr, String fileType)
            throws IOException {
        // 设置水印图片路径

        String imgPath = "";
        try {
            createWaterMarkImage(markStr, imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if ("pptx".equals(fileType)) {
            XMLSlideShow slideShow = new XMLSlideShow(new FileInputStream(path));
            byte[] pictureData = IOUtils.toByteArray(new FileInputStream(imgPath));
            // byte[] pictureData = IOUtils.toByteArray(new
            // FileInputStream("E:\\1.png"));
            PictureData pictureData1 = slideShow.addPicture(pictureData, PictureType.PNG);
            for (XSLFSlide slide : slideShow.getSlides()) {
                XSLFPictureShape pictureShape = slide.createPicture(pictureData1);
                pictureShape.setAnchor(new java.awt.Rectangle(50, 300, 100, 100));
                CTSlide ctSlide = slide.getXmlObject();
                XmlObject[] allText = ctSlide.selectPath(
                        "declare namespace a='http://schemas.openxmlformats.org/drawingml/2006/main' " + ".//a:t");
                /*
                 * for (int i = 0; i < allText.length; i++) { if (allText[i]
                 * instanceof XmlString) { XmlString xmlString =
                 * (XmlString)allText[i]; String text =
                 * xmlString.getStringValue(); if (text==null||text.equals(""))
                 * continue; if (status==1)
                 * xmlString.setStringValue(WordAPi.Encrypt(text)); else
                 * xmlString.setStringValue(WordAPi.Decrypt(text)); } }
                 */
            }

            FileOutputStream out = new FileOutputStream(targetpath);
            slideShow.write(out);
            slideShow.close();
            out.close();
        } else if ("ppt".equals(fileType)) {
            /*
             * HSLFSlideShow ppt = new HSLFSlideShow(); SlideShow ppt = new
             * SlideShow(new HSLFSlideShow("PPT测试.ppt")); SlideShow slideShow =
             * new (new FileInputStream(path)); byte[] pictureData =
             * IOUtils.toByteArray(new FileInputStream(imgPath)); // byte[]
             * pictureData = IOUtils.toByteArray(new
             * FileInputStream("E:\\1.png")); PictureData pictureData1 =
             * slideShow.addPicture(pictureData, PictureType.PNG); for
             * (XSLFSlide slide : slideShow.getSlides()) { XSLFPictureShape
             * pictureShape = slide.createPicture(pictureData1);
             * pictureShape.setAnchor(new java.awt.Rectangle(50, 300, 100,
             * 100)); CTSlide ctSlide = slide.getXmlObject(); XmlObject[]
             * allText = ctSlide.selectPath(
             * "declare namespace a='http://schemas.openxmlformats.org/drawingml/2006/main' "
             * + ".//a:t" ); }
             *
             * FileOutputStream out = new FileOutputStream(targetpath);
             * slideShow.write(out); slideShow.close(); out.close();
             */
        }
    }
}
