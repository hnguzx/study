package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class City {

    @ExcelProperty("city")
    private String countryCity;
}
