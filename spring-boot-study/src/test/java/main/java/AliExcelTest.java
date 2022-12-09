package main.java;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import main.model.AliModel;
import main.model.ExcelModel;
import main.model.FillData;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.assertj.core.internal.BigDecimals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class AliExcelTest {

    @Test
    void testBaseExport() {
        List<AliModel> list = new ArrayList<AliModel>();
        for (int i = 1; i < 10; i++) {
            list.add(AliModel.builder().name("name: " + i)
                    .id(i)
                    .identityCardNumber("43145241521234567" + i)
                    .sex(i % 2)
                    .isLeave(i / 2 == 0)
                    .wage((double) (i * i))
                    .inductionTime(new Date())
                    .build());
        }
        File file = new File("D:\\file\\file\\ali_userInfo.xls");
        ExcelWriterBuilder write = EasyExcel.write(file, AliModel.class);
        ExcelWriterSheetBuilder sheet = write.sheet("表名");
        sheet.doWrite(list);
    }

    @Test
    void testBaseImport() {
        File file = new File("D:\\file\\file\\ali_userInfo.xls");
        ExcelReaderBuilder read = EasyExcel.read(file);
        ExcelReaderSheetBuilder sheet = read.head(AliModel.class).sheet();
        List<AliModel> objects = sheet.doReadSync();
        log.info("read objects:{}", objects);

    }

    @Test
    void testExport() {
        List<AliModel> list = new ArrayList<AliModel>();
        for (int i = 1; i < 10; i++) {
            list.add(AliModel.builder().name("name: " + i)
                    .id(i)
                    .identityCardNumber("43145241521234567" + i)
                    .sex(i % 2)
                    .isLeave(i / 2 == 0)
                    .wage((double) (i * i))
                    .inductionTime(new Date())
                    .build());
        }
        File file = new File("D:\\file\\file\\ali_userInfo.xls");
        ExcelWriterBuilder write = EasyExcel.write(file, AliModel.class);

        // 头的策略
       /* WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        write.registerWriteHandler(horizontalCellStyleStrategy);*/
        ExcelWriterSheetBuilder sheet = write.sheet("表名");
        sheet.doWrite(list);
    }

    @Test
    void testFill() {
        String tempFileName = "D:\\file\\file\\test.xlsx";
        String simpleFill = "D:\\file\\file\\simpleFill.xlsx";

        FillData fillData = new FillData();
        fillData.setDate(new Date());
        fillData.setName("guzx");
        fillData.setNumber(5.2);

        EasyExcel.write(simpleFill).withTemplate(tempFileName).sheet().doFill(fillData);
    }
}
