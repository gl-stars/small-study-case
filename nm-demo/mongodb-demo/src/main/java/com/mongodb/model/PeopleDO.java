package com.mongodb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.utils.ObjectIdJsonSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *  创建一个人的实体类
 *  <p>
 *      {@code @Indexed}注意这里的indexed标识索引，因为编号添加索引没有说的，用户编号也不用说。年龄添加索引是因为有的时候回根据年龄筛选，或者根据年龄排序。状态也是，可以会根据装填筛选。
 *      @Document(collection = "peopleDO") 这里的 collection属性表示指定文档的名称，如果不指定，那么默认为当类的名称，把首字母默认变为小写。
 *
 *      <b>特别注意：编号 id 如果我们添加的时候没有指定，那么MongoDB会自动添加一个，但是自动添加的这个是ObjectID类型，如果这里的编号指定为其他类型就会报数据类型异常。
 *      如果我们今后想一直都是自定指定，那么这里可以书写为自己喜欢的类型，例如Long类型也是一个不错的选择。如果有些数据时需要MongoDB自动生成的，那么这里必须为ObjectID类型。</b>
 *  </p>
 * @author: gl_stars
 * @data: 2020年 09月 12日 11:47
 * @see org.bson.types.ObjectId
 **/
@Data
@Document(collection = "peopleDO")
public class PeopleDO {

    /**
     * 编号
     * <p>@JsonSerialize(using = ObjectIdJsonSerializer.class)如果不这样指定，那么mongodb生成出来的_id是一个对象形式的。使用postman查询出来就很好的看出来了。
     * 没有指定的样子 <url src="https://img-blog.csdnimg.cn/20200912150455544.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center"></url></p>
     */
    @Id
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id ;

    /**
     * 用户名编号
     */
    @Indexed
    private Long userId ;

    /**
     * 年龄
     */
    @Indexed
    private Integer age ;

    /**
     * 状态
     */
    @Indexed
    private Integer status ;

    /***
     * 这个字段是最后添加上，更改没有的字段时，将当前文档删除，重新创建一个文档，将更新的数据添加进去。
     */
    private String stateCode ;

    /***
     * 创建时间
     */
    @CreatedDate
    private Date addTime ;

    /***
     * 更改时间
     */
    @LastModifiedDate
    private Date updateTime ;
}
