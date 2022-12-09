package main.util.excel.easyExcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * 单元格处理器
 */
public class CustomCellWriteHandler implements CellWriteHandler {

    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {
        CellWriteHandler.super.beforeCellCreate(context);
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        CellWriteHandler.super.beforeCellCreate(writeSheetHolder, writeTableHolder, row, head, columnIndex, relativeRowIndex, isHead);
    }

    @Override
    public void afterCellCreate(CellWriteHandlerContext context) {
        CellWriteHandler.super.afterCellCreate(context);
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        CellWriteHandler.super.afterCellCreate(writeSheetHolder, writeTableHolder, cell, head, relativeRowIndex, isHead);
    }

    @Override
    public void afterCellDataConverted(CellWriteHandlerContext context) {
        CellWriteHandler.super.afterCellDataConverted(context);
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        CellWriteHandler.super.afterCellDataConverted(writeSheetHolder, writeTableHolder, cellData, cell, head, relativeRowIndex, isHead);
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        CellWriteHandler.super.afterCellDispose(context);
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        CellWriteHandler.super.afterCellDispose(writeSheetHolder, writeTableHolder, cellDataList, cell, head, relativeRowIndex, isHead);
    }
}
