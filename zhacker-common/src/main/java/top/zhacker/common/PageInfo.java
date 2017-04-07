package top.zhacker.common;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * DATE: 2017/4/6 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
@Accessors(chain = true)
public class PageInfo {
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    private Integer offset;
    private Integer limit;

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer size) {
        pageNo = MoreObjects.firstNonNull(pageNo, 1);
        size = MoreObjects.firstNonNull(size, 20);
        this.limit = size > 0 ? size :20;
        this.offset = (pageNo - 1) * size;
        this.offset = offset > 0 ? offset : 0;
    }

    public static PageInfo of(Integer pageNo, Integer size) {
        return new PageInfo(pageNo, size);
    }
}
