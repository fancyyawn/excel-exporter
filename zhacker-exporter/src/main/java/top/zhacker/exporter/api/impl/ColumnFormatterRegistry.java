package top.zhacker.exporter.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.zhacker.exporter.api.ColumnFormatter;
import top.zhacker.exporter.api.impl.formatter.DefaultFormatter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DATE: 16/11/21 下午6:00 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Component
@Slf4j
public class ColumnFormatterRegistry {
    /**
     * 导出字段样式器
     */
    private final Map<String, ColumnFormatter> formatters;

    public ColumnFormatterRegistry() {
        formatters = new ConcurrentHashMap<>();
    }

    /**
     * 注册字段样式器
     *
     * @param name  字段样式器名称
     * @param formatter  字段样式器
     */
    public void register(String name, ColumnFormatter formatter){
        formatters.put(name, formatter);
    }

    /**
     * 获取字段样式器
     *
     * @param name  字段样式器名称
     * @return  对应的字段样式器, 如果不存在则抛出异常
     */
    public ColumnFormatter getFormatter(String name){
        final ColumnFormatter formatter = formatters.get(name);
        if(formatter == null){
            log.warn("no column formatter (name={}) configured, return defaultFormatter", name);
            return DefaultFormatter.INSTANCE;
        }
        return formatter;
    }
}
