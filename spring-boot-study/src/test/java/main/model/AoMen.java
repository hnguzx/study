package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AoMen {
    @ExcelProperty("regionNameTC")
    private String regionNameTC;
    @ExcelProperty("regionNameSC")
    private String regionNameSC;
    @ExcelProperty("regionNameEN")
    private String regionNameEN;
}
