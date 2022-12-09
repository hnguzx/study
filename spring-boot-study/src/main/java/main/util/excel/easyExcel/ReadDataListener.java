package main.util.excel.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ReadDataListener<Object> implements ReadListener<Object> {

    /**
     * 读取表格中一行
     * 可以读取后入库
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {

    }

    /**
     * 读取完成后的操作
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    /**
     * 读取头数据
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ReadListener.super.invokeHead(headMap, context);
    }

    /**
     * 读取额外信息(批注、超链接、合并单元格信息读取)
     * @param extra   extra information
     * @param context analysis context
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        ReadListener.super.extra(extra, context);
    }

    /**
     * 数据读取异常处理
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        ReadListener.super.onException(exception, context);
    }

    /**
     * 判断是否继续读取
     * @param context
     * @return
     */
    @Override
    public boolean hasNext(AnalysisContext context) {
        return ReadListener.super.hasNext(context);
    }
}
