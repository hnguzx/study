package main.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.util.excel.easyExcel.NumberConverter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ExcelIgnoreUnannotated
@HeadRowHeight(30)
@ColumnWidth(50)
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 10)
@HeadFontStyle(fontHeightInPoints = 20)
@ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
@ContentFontStyle(fontHeightInPoints = 20)
public class AliModel {
    @ExcelIgnore
    private Integer id;

    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 14)
    @HeadFontStyle(fontHeightInPoints = 30)
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
    @ContentFontStyle(fontHeightInPoints = 30)
//    @ExcelProperty(value = {"员工信息","姓名"})
    @ExcelProperty(value = {"姓名"})
    private String name;
    @ColumnWidth(20)
//    @ExcelProperty(value = {"员工信息","性别"})
    @ExcelProperty(value = {"性别"})
    private Integer sex;
    //    @ExcelProperty(value = {"员工信息","身份证号码"}, converter = NumberConverter.class)
    @ExcelProperty(value = {"身份证号码"}, converter = NumberConverter.class)
    private String identityCardNumber;
    //    @ExcelProperty(value = {"员工信息","薪资"})
    @ExcelProperty(value = {"薪资"})
    private Double wage;
    //    @ExcelProperty(value = {"员工信息","是否离职"})
    @ExcelProperty(value = {"是否离职"})
    private Boolean isLeave;
    @DateTimeFormat(value = "yyyy-MM-dd")
//    @ExcelProperty(value = {"员工信息","入职时间"})
    @ExcelProperty(value = {"入职时间"})
    private Date inductionTime;
}
