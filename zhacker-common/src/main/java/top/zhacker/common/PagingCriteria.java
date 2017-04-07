package top.zhacker.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * DATE: 2017/4/6 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Data
public class PagingCriteria implements Serializable {


    protected static final ObjectMapper MAPPER = new ObjectMapper();
    static{
        //设置输出时包含属性的风格
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //规范化日期类型
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }


    private static final long serialVersionUID = 2598875146576926658L;

    /**
     * 分页号, 从1开始
     */
    @JsonIgnore
    @Getter
    @Setter
    private Integer pageNo=1;

    /**
     * 分页大小
     */
    @JsonIgnore
    @Getter
    @Setter
    private Integer pageSize=20;

    /**
     * 是否还有下一页
     */
    @JsonIgnore
    @Setter
    private Boolean hasNext=true;

    @JsonIgnore
    public Boolean  hasNext(){
        return this.hasNext;
    }

    /**
     * 跳转到下一页
     */
    public void nextPage(){
        if(pageNo==null){
            pageNo=1;
        }
        pageNo += 1;
    }

    /**
     * 分页大小, 默认20, 用于数据库
     * @return
     */
    public Integer getLimit(){
        PageInfo pageInfo=new PageInfo(pageNo,pageSize);
        return pageInfo.getLimit();
    }

    /**
     * 分页起始, 从0开始, 用于数据库
     * @return
     */
    public Integer getOffset(){
        PageInfo pageInfo=new PageInfo(pageNo,pageSize);
        return pageInfo.getOffset();
    }

    public Map<String, Object> toMap() {
        formatDate();
        return MAPPER.convertValue(this, MAPPER.getTypeFactory().constructParametricType(Map.class, String.class, Object.class));
    }

    /**
     * 如果Start的时间和End的时间一致, 则End+1day
     */
    protected void formatDate(){

    }

}