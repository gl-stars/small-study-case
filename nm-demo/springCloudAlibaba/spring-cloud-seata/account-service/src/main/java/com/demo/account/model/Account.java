package com.demo.account.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 账号
 * <p>
 *     @Accessors 注解使用
 *     chain = true ;则setter方法返回当前对象
 *     fluent = true ;则getter和setter方法的方法名都是基础属性名，且setter方法返回当前对象
 *     prefix="p" ;prefix的中文含义是前缀，用于生成getter和setter方法的字段名会忽视指定前缀（遵守驼峰命名）
 *     参考：https://blog.csdn.net/weixin_38229356/article/details/82937420
 * </p>
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Data
@Accessors(chain = true)
@TableName("account_tbl")
public class Account {
  @TableId
  private Long id;
  private String userId;
  private Integer money;
}
