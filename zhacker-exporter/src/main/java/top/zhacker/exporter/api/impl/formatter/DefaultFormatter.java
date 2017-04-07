package top.zhacker.exporter.api.impl.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhacker.exporter.api.ColumnFormatter;
import top.zhacker.exporter.api.impl.ColumnFormatterRegistry;

import javax.annotation.PostConstruct;

/**
 * DATE: 16/11/21 下午5:16 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Component
public class DefaultFormatter implements ColumnFormatter {

    @Autowired
    private ColumnFormatterRegistry registry;

    @PostConstruct
    public void init(){
        registry.register("default", this);
    }

    public static final DefaultFormatter INSTANCE = new DefaultFormatter();

    @Override
    public String format(Object value) {
        if(value == null){
            return "";
        }
        return String.valueOf(value);
    }
}
