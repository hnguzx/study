package main.java;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import main.model.ExcelModel;
import main.util.excel.easyPoi.ExcelExportStyler;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

@Slf4j
public class EasyExcelTest {

    @Test
    void testExport() throws IOException {
        List<ExcelModel> list = new ArrayList<ExcelModel>();
        for (int i = 0; i < 10; i++) {
            list.add(ExcelModel.builder().name("name: " + i)
                    .id(i)
                    .identityCardNumber(i + "12345698778965412")
                    .sex(i % 2)
                    .isLeave(i / 2 == 0)
                    .wage((double) (i * i))
                    .inductionTime(new Date())
                    .build());
        }
        ExportParams exportParams = new ExportParams("", "");
        // 整体样式
        exportParams.setStyle(ExcelExportStyler.class);
        // 是否创建title,secondTitle,表头
        exportParams.setCreateHeadRows(true);
        // 是否固定表头
        exportParams.setFixedTitle(true);
        // 标题
        exportParams.setTitle("用户信息");
        // 表名与表头的高度
        exportParams.setTitleHeight((short) 30);
        // 表头高度（不生效）
//        exportParams.setHeaderHeight(50);
//        exportParams.setColor(HSSFColor.HSSFColorPredefined.CORNFLOWER_BLUE.getIndex());
        // 表名
        exportParams.setSheetName("表格名称");
        // 副标题
        exportParams.setSecondTitle("副标题");
        exportParams.setSecondTitleHeight((short) 10);
//        exportParams.setHeaderColor(HSSFColor.HSSFColorPredefined.CORNFLOWER_BLUE.getIndex());
        // 行高
        exportParams.setHeight((short) 30);
        // 表格只读
        exportParams.setReadonly(true);
        // 宽度自适应
        exportParams.setAutoSize(true);
        // 单表最大行数
        exportParams.setMaxNum(2000);
        // 不生效
//        exportParams.setAddIndex(true);
//        exportParams.setIndexName("序号");
        // 导出文件格式 HSSF(xls),XSSF(xlsx)
        exportParams.setType(ExcelType.HSSF);
        // 排除指定列
        List<String> exclude = new ArrayList<String>();
        exclude.add("工资");
        exportParams.setExclusions(exclude.toArray(new String[exclude.size()]));

        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, ExcelModel.class, list);
        FileOutputStream outputStream = new FileOutputStream("D:\\file\\file\\userInfo.xls");
//        FileOutputStream outputStream = new FileOutputStream("D:\\file\\file\\userInfo.xlsx");
        sheets.write(outputStream);
        outputStream.close();
    }


    @Test
    void testImport() throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        File file = new File("D:\\file\\file\\userInfo.xls");
        log.info("file size: " + file.length());
        List<Object> objects = ExcelImportUtil.importExcel(file, ExcelModel.class, params);
        log.info("size of objects:{}", objects.size());
        log.info("excel model: {}", objects);
    }
}
