package top.zhacker.exporter.showcase.service;

import org.springframework.stereotype.Service;
import top.zhacker.common.Paging;
import top.zhacker.common.Response;
import top.zhacker.exporter.showcase.dto.ItemPagingCriteria;
import top.zhacker.exporter.showcase.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Service
public class ItemReadServiceImpl implements ItemReadService {

    private final List<Item> repo = new ArrayList<>();
    {
        repo.add(new Item().setId(1L).setShopId(1L).setName("item1").setDetails("item1 details").setPrice(100).setStatus(2).setCreatedAt(new Date()));
        repo.add(new Item().setId(2L).setShopId(1L).setName("item2").setDetails("item2 details").setPrice(200).setStatus(3).setCreatedAt(new Date()));
        repo.add(new Item().setId(3L).setShopId(1L).setName("item3").setDetails("item3 details").setPrice(300).setStatus(1).setCreatedAt(new Date()));
        repo.add(new Item().setId(4L).setShopId(1L).setName("item4").setDetails("item4 details").setPrice(400).setStatus(1).setCreatedAt(new Date()));
        repo.add(new Item().setId(5L).setShopId(2L).setName("item5").setDetails("item5 details").setPrice(500).setStatus(1).setCreatedAt(new Date()));
    }

    @Override
    public Response<Paging<Item>> pagingItems(ItemPagingCriteria criteria) {

        Long count = repo.stream().filter(x-> itemMatched(x, criteria.getShopId())).count();

        List<Item> items = repo.stream().filter(x-> itemMatched(x, criteria.getShopId()))
                .skip(criteria.getOffset()).limit(criteria.getLimit()).collect(Collectors.toList());

        return Response.ok(new Paging<>(count, items));
    }

    private boolean itemMatched(Item item, Long shopId){

        return shopId == null || shopId.equals(item.getShopId());
    }
}
