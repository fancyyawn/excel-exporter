package top.zhacker.exporter.api.impl.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhacker.exporter.api.ColumnFormatter;
import top.zhacker.exporter.api.impl.ColumnFormatterRegistry;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;

/**
 * DATE: 16/11/21 下午5:20 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Component
public class PriceFormatter implements ColumnFormatter {

    @Autowired
    private ColumnFormatterRegistry registry;

    @PostConstruct
    public void init(){
        registry.register("price", this);
    }

    /**
     * 2位小数
     */
    public static final DecimalFormat DECIMAL_FMT_2 = new DecimalFormat("0.00");

    @Override
    public String format(Object price) {
        if (price == null) {
            return "";
        }
        if(price instanceof Number){
            return DECIMAL_FMT_2.format(((Number)price).doubleValue() / 100);
        }else{
            return "";
        }
    }
}
