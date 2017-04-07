package top.zhacker.exporter.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DATE: 16/11/21 下午3:45 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
public class ExportTable implements Serializable {
    private static final long serialVersionUID = -3289658533196621989L;

    private String name;
    private String display;
    private List<ExportColumn> columns;
}
