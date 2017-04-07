package top.zhacker.exporter.showcase.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
@Accessors(chain = true)
public class Item implements Serializable {

    private Long id;
    private Long shopId;
    private String name;
    private String details;
    private Integer price;
    private Integer status;
    private Date createdAt;
}
