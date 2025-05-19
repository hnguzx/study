package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Country {

    @ExcelProperty("cn_tc_country")
    private String cn_tc_country;

    @ExcelProperty("cn_sc_country")
    private String cn_sc_country;

    @ExcelProperty("en_country")
    private String en_country;

    @ExcelProperty("iso_code")
    private String iso_code;

    @ExcelProperty("iso_code_long")
    private String iso_code_long;

    @ExcelProperty("highrisk_country")
    private String highrisk_country;

    @ExcelProperty("iban_mandatory")
    private String iban_mandatory;

    @ExcelProperty("purpose_mandatory")
    private String purpose_mandatory;




}
