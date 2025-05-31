package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ClientInfo {

    @ExcelProperty(index = 0)
    private String clientId;
}
