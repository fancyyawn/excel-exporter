package top.zhacker.exporter.api.impl.formatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhacker.exporter.api.ColumnFormatter;
import top.zhacker.exporter.api.impl.ColumnFormatterRegistry;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * DATE: 16/11/21 下午5:12 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Component
public class IsoDateFormatter implements ColumnFormatter {

    @Autowired
    private ColumnFormatterRegistry registry;

    @PostConstruct
    public void init(){
        registry.register("isoDate", this);
    }

    private static final DateTimeFormatter DFT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(Object value) {
        if(value==null){
            return "";
        }
        if(value instanceof Date) {
            return DFT.print(new DateTime(value));
        }else if (value instanceof String){
            return (String)value;
        }else if(value instanceof Long){
            return DFT.print(new DateTime((long)value));
        }else{
            return "";
        }
    }
}
