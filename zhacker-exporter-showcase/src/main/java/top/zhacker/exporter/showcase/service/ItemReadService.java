package top.zhacker.exporter.showcase.service;

import top.zhacker.common.Paging;
import top.zhacker.common.Response;
import top.zhacker.exporter.showcase.dto.ItemPagingCriteria;
import top.zhacker.exporter.showcase.model.Item;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
public interface ItemReadService {

    Response<Paging<Item>> pagingItems(ItemPagingCriteria criteria);
}
