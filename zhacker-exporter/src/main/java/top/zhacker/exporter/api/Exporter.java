package top.zhacker.exporter.api;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import top.zhacker.common.Paging;
import top.zhacker.common.PagingCriteria;
import top.zhacker.common.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Function;

/**
 * 导出工具类, 默认实现为 {@link top.zhacker.exporter.api.impl.DefaultExporter}
 *
 * DATE: 16/11/21 下午3:44 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
public interface Exporter {

    /**
     * 将指定列表中的数据, 导出为一个excel对象。
     * 在导出之前, 具体实现会要求在指定位置配置样式。
     * 在DefaultExporter中, 会要求在export.yaml中配置样式。
     *
     * @param dataList 数据列表
     * @param clazz 列表元素的数据类型
     * @return 待导出的excel表单
     */
    XSSFWorkbook export(List<?> dataList, Class<?> clazz);

    /**
     * 将指定列表中的数据, 导出到客户端。
     * 在导出之前, 具体实现会要求在指定位置配置样式。
     * 在DefaultExporter中, 会要求在export.yaml中配置样式。
     *
     * @param dataList 数据列表
     * @param clazz 列表元素的数据类型
     * @param request 请求实体
     * @param response 响应实体, 具体导出的数据会写入这个实体中。
     */
    void export(List<?> dataList, Class<?> clazz, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过指定的分页读取方法, 自动调用并导出数据到客户端
     * 在导出之前, 具体实现会要求在指定位置配置样式。
     * 在DefaultExporter中, 会要求在export.yaml中配置样式。
     *
     * @param clazz 导出元素的类型
     * @param criteria 分页查询条件
     * @param func 分页查询方法
     * @param request 请求实体
     * @param response 响应实体, 具体导出的数据会写入这个实体中。
     * @param <T> 导出元素的类型约束
     * @param <C> 查询条件的类型约束, 必须是PagingCriteria的子类。
     */
    <T, C extends PagingCriteria> void export(Class<?> clazz, C criteria, Function<C, Response<Paging<T>>> func, HttpServletRequest request, HttpServletResponse response);

}
