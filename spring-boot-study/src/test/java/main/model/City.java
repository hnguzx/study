package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class City {

    @ExcelProperty(index = 0)
    private String country;

    @ExcelProperty(index = 1)
    private String city;
}
