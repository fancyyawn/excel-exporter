package top.zhacker.exporter.api.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhacker.common.Paging;
import top.zhacker.common.PagingCriteria;
import top.zhacker.common.Response;
import top.zhacker.exporter.api.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * DATE: 16/11/24 上午9:22 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Slf4j
@Component
public class DefaultExporter implements Exporter {

    private final ExportTables tables;

    private final ColumnFormatterRegistry formatterRegistry;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    private static final JavaType MAP_STRING_OBJECT = OBJECT_MAPPER.getTypeFactory()
            .constructParametricType(Map.class, String.class, Object.class);

    @Autowired
    public DefaultExporter(ExportTables tables, ColumnFormatterRegistry formatterRegistry) {
        this.tables = tables;
        this.formatterRegistry = formatterRegistry;
    }

    @Override
    public XSSFWorkbook export(List<?> dataList, Class<?> clazz){

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String tableName = clazz.getSimpleName();
            ExportTable table = tables.getTables().get(tableName);

            XSSFSheet s = wb.createSheet(table.getDisplay());

            //标题
            Row titleRow = s.createRow(0);
            int i=0;
            for (ExportColumn column : table.getColumns()){
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(column.getDisplay());
                s.setColumnWidth(i, column.getWidth());
                i++;
            }
            //内容
            for (i=0; i<dataList.size(); i++){
                Row row = s.createRow(i+1);
                int j = 0;
                String json =  OBJECT_MAPPER.writeValueAsString(dataList.get(i));
                Map<String, Object> fields = OBJECT_MAPPER.readValue(json, MAP_STRING_OBJECT);
                for(ExportColumn column : table.getColumns()){
                    Cell cell = row.createCell(j);

                    if(column.getFormat()== null){
                        column.setFormat("default");
                    }
                    ColumnFormatter formatter = formatterRegistry.getFormatter(column.getFormat());

                    Object value = fields.get(column.getName());
                    cell.setCellValue(formatter.format(value));
                    ++j;
                }
            }

        }catch (Exception e){
            log.error("export fail, cause={}", Throwables.getStackTraceAsString(e));
        }
        return wb;
    }

    @Override
    public void export(List<?> dataList, Class<?> clazz, HttpServletRequest request, HttpServletResponse response){

        ExportTable table = tables.getTables().get(clazz.getSimpleName());
        if(table==null){
            log.error("download the excel of {} failed, cause={}",
                    clazz.getSimpleName(), "table.config.missing");
            throw new ExportException("table.config.missing");
        }

        try {
            setHttpServletResponse(request, response, table.getDisplay());
            XSSFWorkbook book = this.export(dataList, clazz);
            book.write(response.getOutputStream());
        } catch (Exception e){
            log.error("download the excel of {} failed, cause={}",
                    clazz.getSimpleName(), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public <T, C extends PagingCriteria> void export(Class<?> clazz, C criteria, Function<C, Response<Paging<T>>> func, HttpServletRequest request, HttpServletResponse response) {
        List<T> list = findPages(criteria, func);
        export(list, clazz, request, response);
    }

    /**
     * 根据传入的分页查询方法和参数去查询出所有的数据。
     * todo 待优化, 应该与export结合起来, 读出一个分页, 处理一个分页。
     *
     * @param criteria 分页查询条件
     * @param func 分页查询方法
     * @param <T> 元素类型约束
     * @param <C> 分页查询条件的类型约束
     * @return
     */
    public static  <T, C extends PagingCriteria> List<T>  findPages(C criteria, Function<C, Response<Paging<T>>> func){

        List<T> data = Lists.newArrayList();

        criteria.setPageNo(1);
        criteria.setHasNext(true);
        criteria.setPageSize(200);
        while(criteria.hasNext()) {
            Response<Paging<T>> resp = func.apply(criteria);
            List<T> list = resp.getResult().getData();
            if (list == null || list.size() == 0){
                break; //or criteria.setHasNext(false);
            }else{
                data.addAll(list);
                criteria.nextPage();
            }
        }
        return data;
    }

    /**
     * 设置导出时的响应实体, 注意避免名称乱码问题。
     *
     * @param request
     * @param response
     * @param fileName 文件名称
     *
     * @throws UnsupportedEncodingException
     */
    public static void setHttpServletResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = fileName + ".xlsx";

        final String userAgent = request.getHeader("USER-AGENT");
        log.debug("user-agent={}", userAgent);

        String finalFileName = null;
        if(userAgent.toUpperCase().contains("MSIE")){//IE浏览器
            finalFileName = URLEncoder.encode(fileName,"UTF8");
        }else if(userAgent.toUpperCase().contains("Mozilla".toUpperCase())){//safari,火狐浏览器
            finalFileName = new String(fileName.getBytes(), "ISO8859-1");
        }else{
            finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器, 如chrome等
        }
        //这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8;");
        response.setContentType("application/octet-stream;charset=utf-8");
    }
}

