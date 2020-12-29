package com.mongodb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.utils.ObjectIdJsonSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户对象
 * <p>这个类创建的不是很标准，但是为了有集合，对象等形式好做各种各样的操作。</p>
 * @author: gl_stars
 * @data: 2020年 09月 09日 16:26
 **/
@Data
@Document(collection = "user")
public class UserDO {

    /**
     * 数据库自身成的数据库 ID
     */
    @Id
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id;

    /**
     * 用户手机号
     * <p>@NotBlank当前字段不能为空</p>
     */
    @Indexed(unique = true)
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个性签名
     */
    private Set<String> slogan;

    /**
     * 性别: | 0 未知 | 1 女 | 2 男 |
     */
    private Byte gender = 0;

    /**
     * 社交账号： wechat、 qq、 weibo、 facebook、 ins
     */
    private Map<String, String> socialAccount;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户标签
     */
    private List<String> tags;

    /**
     * 年龄
     */
    private Integer age = 0;
    /**
     * 配偶
     * <p>配偶也属于人的类</p>
     */
    private UserDO spouse;

    /**
     * 汽车
     */
    private List<CarDO> cars;

    /***
     * 汽车类，为了测试单个嵌套更改
     */
    private CarDO car ;
}
