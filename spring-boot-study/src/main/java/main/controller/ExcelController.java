package main.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import lombok.extern.slf4j.Slf4j;
import main.dto.WebResp;
import main.model.OrderDetail;
import main.util.excel.easyExcel.ReadDataListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequestMapping("/excel")
@RestController
public class ExcelController {


    @PostMapping("/upload")
    public WebResp<String> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReaderBuilder read = EasyExcel.read(inputStream, new ReadDataListener());
        ExcelReaderSheetBuilder sheet = read.head(OrderDetail.class).sheet();
        List<OrderDetail> objects = sheet.doReadSync();
        log.info("read objects:{}", objects);
        return WebResp.retOk();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam("orderNos") List<String> orderNos) throws IOException {
        log.info("download objects:{}", orderNos);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        List<OrderDetail> list = new ArrayList<OrderDetail>();
        for (int i = 1; i < 10; i++) {
            list.add(OrderDetail.builder()
                    .orderNo("ABC123456789" + i)
                    .customerNo("J00" + i)
                    .orderType(i % 2 == 0 ? "进口" : "出口")
                    .exceptionDesc("异常说明")
                    .completeTime(new Date())
                    .internalType01(new Date())
                    .internalType02(new Date())
                    .internalType03(new Date())
                    .internalType04(new Date())
                    .internalType05(new Date())
                    .internalType06(new Date())
                    .internalType07(new Date())
                    .internalType08(new Date())
                    .internalType09(new Date())
                    .internalType10(new Date())
                    .internalType11(new Date())
                    .internalType12(new Date())
                    .internalType13(new Date())
                    .internalType14(new Date())
                    .internalType15(new Date())
                    .hkType01(new Date())
                    .hkType02(new Date())
                    .hkType03(new Date())
                    .hkType04(new Date())
                    .hkType05(new Date())
                    .hkType06(new Date())
                    .foreignType01(new Date())
                    .foreignType02(new Date())
                    .foreignType03(new Date())
                    .foreignType04(new Date())
                    .foreignType05(new Date())
                    .foreignType06(new Date())
                    .foreignType07(new Date())
                    .foreignType08(new Date())
                    .foreignType09(new Date())
                    .foreignType10(new Date())
                    .foreignType11(new Date())
                    .build());
        }
        ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), OrderDetail.class);
        ExcelWriterSheetBuilder sheet = write.sheet("模板");
        sheet.doWrite(list);
    }
}
