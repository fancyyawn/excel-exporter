package top.zhacker.exporter.showcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.zhacker.exporter.api.Exporter;
import top.zhacker.exporter.showcase.dto.ItemPagingCriteria;
import top.zhacker.exporter.showcase.model.Item;
import top.zhacker.exporter.showcase.service.ItemReadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@RestController
public class Items {

    private final Exporter exporter;

    private final ItemReadService itemReadService;

    @Autowired
    public Items(Exporter exporter, ItemReadService itemReadService) {
        this.exporter = exporter;
        this.itemReadService = itemReadService;
    }

    @RequestMapping(value = "/item-export", method = RequestMethod.GET)
    public void exportSettleOrders(ItemPagingCriteria criteria, HttpServletResponse response, HttpServletRequest request) {
        exporter.export(Item.class, criteria, itemReadService::pagingItems, request, response);
    }

}
