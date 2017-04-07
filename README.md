# excel-exporter 

> 基于yaml模板的excel导出工具

* zhacker-common：定义了Paging、Response等的接口常用类
* zhacker-exporter: 实现导出工具
* zhacker-exporter-showcase: 示例如何使用导出工具

## 使用详解

### step1 定义导出模板
在showcase`resources/export.yaml`示例了如何为Item配置导出的标题类型等。
默认的格式化器有：default, isoDate(yyyy-MM-dd HH:mm:ss), price(###.##)

```yaml
export.tables:
  Item: #实体类名
    display: 商品列表  #表格名称
    columns:
      - name: id  #实体类字段名称
        display: ID #表格列标题
        width: 1800 #表格宽度
      - name: shopId 
        display: 店铺ID
        width: 1800
      - name: name
        display: 商品名称
        width: 7200
        format: isoDate #格式化器，为空时用DefaultFormatter（格式化为普通字符串）
      - name: details
        display: 商品详情
        width: 1800
      - name: price
        display: 价格
        width: 1800
        format: price
      - name: status
        display: 状态
        width: 1800
      - name: createdAt
        display: 创建时间
        width: 1800
        format: isoDate
```

### step2 调用导出工具
```java
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

public interface ItemReadService {

    Response<Paging<Item>> pagingItems(ItemPagingCriteria criteria);
}

```
注意：

 * 服务方法的参数：必须继承`PagingCriteria`
 * 服务方法的返回：必须用`Response< Paging<T> >`
 * 关键点在于`Exporter.export`方法中中的用`itemReadService::pagingItems`引用服务方法
 
### step3 定义自己的格式化器

当某个字段要特殊处理时，可定义格式化器，注册到`ColumnFormatterRegistry`；
在模板中设置format时，按注册时的key来引用。

```java
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

```