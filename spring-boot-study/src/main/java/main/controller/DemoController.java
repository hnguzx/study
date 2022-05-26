package main.controller;

//import com.luciad.imageio.webp.WebPReadParam;
//import com.luciad.imageio.webp.WebPWriteParam;

import lombok.extern.slf4j.Slf4j;
import main.dto.WebResp;
import main.util.WaterMarkUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/**
 * @author 志雄
 */
@Slf4j
@RestController
public class DemoController {

    @GetMapping("/demo")
    public WebResp<String> demo() {
        return WebResp.retOk("hello word");
    }

    @PostMapping("/upload")
    public void testFile(@RequestParam MultipartFile multipartFile, @RequestParam String name) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        log.info("图片/PDF文件名称：{},上传参数：{}", fileName, name);

//        String filePath = "F:/opt/ebank/file/tempFile/";
//        File file = new File(filePath + fileName);
//        boolean newFile = file.createNewFile();
//        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
//        log.info("文件创建是否成功：{}", newFile);

        File file = File.createTempFile(fileName, prefix);
//        multipartFile.transferTo(file);

        FileCopyUtils.copy(multipartFile.getInputStream(), Files.newOutputStream(file.toPath()));

        File mark = WaterMarkUtils.mark(file, prefix, "水印");
        log.info("水印文件名：{}",mark.getName());

    }

    @PostMapping("/testPost")
    public void testPost(@RequestParam(name = "par1", required = false) String par1) {
        log.info("上送参数：{}", par1);
    }
}
