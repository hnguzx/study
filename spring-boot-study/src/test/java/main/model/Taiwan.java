package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Taiwan {
    @ExcelProperty(index = 0)
    private String regionNameTC;
    @ExcelProperty(index = 1)
    private String regionNameSC;
    @ExcelProperty(index = 2)
    private String regionNameEN;
    @ExcelProperty(index = 3)
    private String regionNameTC2;
    @ExcelProperty(index = 4)
    private String regionNameSC2;
    @ExcelProperty(index = 5)
    private String regionNameEN2;
}
