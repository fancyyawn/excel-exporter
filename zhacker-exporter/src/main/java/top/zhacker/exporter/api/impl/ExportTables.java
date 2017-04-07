package top.zhacker.exporter.api.impl;

import lombok.Data;
import top.zhacker.exporter.api.ExportTable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DATE: 16/11/21 下午4:06 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
public class ExportTables implements Serializable {
    private static final long serialVersionUID = -932906409943841732L;

    private Map<String, ExportTable> tables = new HashMap<>();
}
