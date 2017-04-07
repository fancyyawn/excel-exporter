package top.zhacker.exporter.showcase.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.zhacker.common.PagingCriteria;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemPagingCriteria extends PagingCriteria {

    private Long shopId;
}
