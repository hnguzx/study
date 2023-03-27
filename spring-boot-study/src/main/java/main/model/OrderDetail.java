package main.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(20)
@ColumnWidth(20)
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 1)
public class OrderDetail {
    @ExcelProperty(value = {"订单号"}, index = 0)
    private String orderNo;
    @ExcelProperty(value = {"客户编码"}, index = 1)
    private String customerNo;
    @ExcelProperty(value = {"类型"}, index = 2)
    private String orderType;
    @ExcelProperty(value = {"异常说明"}, index = 3)
    private String exceptionDesc;
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm")
    @ExcelProperty(value = {"完成"}, index = 4)
    private Date completeTime;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "提货到厂"})
    private Date internalType01;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "提货离厂"})
    private Date internalType02;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "保税进口报关"})
    private Date internalType03;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "保税出口报关"})
    private Date internalType04;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "保税入区"})
    private Date internalType05;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "保税离区"})
    private Date internalType06;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "中转到仓"})
    private Date internalType07;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "中转离仓"})
    private Date internalType08;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "口岸申报"})
    private Date internalType09;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "口岸放行"})
    private Date internalType10;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "运达口岸"})
    private Date internalType11;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "离开口岸"})
    private Date internalType12;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "口岸提货"})
    private Date internalType13;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "送达完成"})
    private Date internalType14;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国内段", "签收完成"})
    private Date internalType15;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "口岸通关"})
    private Date hkType01;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "香港提货"})
    private Date hkType02;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "香港入仓"})
    private Date hkType03;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "备货完成"})
    private Date hkType04;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "香港出仓"})
    private Date hkType05;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"香港段", "香港派送"})
    private Date hkType06;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外到港"})
    private Date foreignType01;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外申报"})
    private Date foreignType02;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外放行"})
    private Date foreignType03;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外提货到仓"})
    private Date foreignType04;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外提货出仓"})
    private Date foreignType05;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外送达"})
    private Date foreignType06;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外签收"})
    private Date foreignType07;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外提货到厂"})
    private Date foreignType08;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外提货离厂"})
    private Date foreignType09;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外交仓"})
    private Date foreignType10;

    @DateTimeFormat(value = "yyyy-MM-dd hh:mm")
    @ExcelProperty(value = {"国外段", "国外起运"})
    private Date foreignType11;
}
