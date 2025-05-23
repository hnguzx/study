package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CouponPool {
    @ExcelProperty(index = 0)
    private String code;
    @ExcelProperty(index = 1)
    private String codeType;
    @ExcelProperty(index = 2)
    private String activity;
}
