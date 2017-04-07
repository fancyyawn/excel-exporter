package top.zhacker.exporter.api;

import lombok.Data;

import java.io.Serializable;

/**
 * DATE: 16/11/21 下午3:46 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
public class ExportColumn implements Serializable {

    private String name;
    private String display;
    private Integer width;
    private String format;
}
