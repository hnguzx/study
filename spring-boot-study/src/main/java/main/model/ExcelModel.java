package main.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelModel {
    @Excel(name = "序号", isImportField = "true")
    private Integer id;
    @Excel(name = "姓名", isImportField = "true")
    private String name;
    @Excel(name = "性别", replace = {"男_1", "女_0"}, isImportField = "true")
    private Integer sex;
    @Excel(name = "身份证号码", isImportField = "true")
    private String identityCardNumber;
    @Excel(name = "工资", isImportField = "true")
    private Double wage;
    @Excel(name = "是否离职", replace = {"是_true", "否_false"}, isImportField = "true")
    private Boolean isLeave;
    @Excel(name = "入职时间", format = "yyyy-MM-dd", isImportField = "true")
    private Date inductionTime;
}
