package com.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 汽车类
 * @author: gl_stars
 * @data: 2020年 09月 09日 17:39
 **/
@Data
@Document(collection = "user")
public class CarDO {

    @Id
    @Indexed
    private Long id ;

    /**
     * 小汽车名称
     */
    private String name ;

    /**
     * 厂家
     */
    private String manufactor ;

    /**
     * 生产日期
     */
    private Date dateOfManufacture ;
}
