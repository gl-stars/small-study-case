# mongodb查询大全springdataMongoDB版本

# 一、前言

关于环境方面的东西就不用介绍，因为我这里不是介绍如何入门。而是专门演示`mongodb`在`java`中的各种操作，虽然`mongodb`不支持链表查询，但是支持在一个对象中嵌套对象对象集合或者数组的方式。在程序设计的时候就可以思考到这些东西，其实我们也没有必要再做连表之类型的东西。不过万一有这种要求怎么办呢？`java`代码也可以满足你的需求。这里的案例首先是对应 [https://blog.csdn.net/qq_41853447/article/details/108539155](https://blog.csdn.net/qq_41853447/article/details/108539155)这篇文档来的，但是最后面会做一个扩展，这里的扩展就是这个文档中没有提到的东西。

需要注意：因为上面这个文档中使用的对象名是重复的，但是我们不能去改变对象属性，毕竟需要留下记录的。所以需要对象名重复的情况，会在对象后面添加 `1\2\3\4`这样的英文做区分。



在做数据查询测时候的时候，不管代码怎么写。关键我们得看一下执行的`nosql`语句长成什么样吧，所以我们需要在`springboot`中打开日志，然后才能看到执行的`nosql`语句。

## 在控制台打印nosql语句

只需要在`application.yml`文件中添加这个配置即可。

```yaml
logging:
  level:
    org.springframework.data.mongodb.core: debug
```

# 二、插入测试数据

## 3.1、创建实体类

```java
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
```

## 2.2、插入数据

添加的方法非常多，简单介绍一下常用的就KO咯。

```java
// 一下是添加单个数据
<T> T save(T objectToSave);
<T> T save(T objectToSave, String collectionName);
<T> T insert(T objectToSave);
<T> T insert(T objectToSave, String collectionName);
// 一下是批量添加
<T> Collection<T> insertAll(Collection<? extends T> objectsToSave);
<T> Collection<T> insert(Collection<? extends T> batchToSave, String collectionName);
<T> Collection<T> insert(Collection<? extends T> batchToSave, Class<?> entityClass);
```

这个几个添加方法的使用都差不多，为一个不同的是在后面指定集合名称和不指定集合名称而已。但是需要注意一个方法 `<T> T insert(T objectToSave)`这个方法的参数是一个任意类型，如果我们放一个集合进去时，编译是没问题的，但是如果不指定集合名称是添加不进去的。

- 范例

```java
public List<PeopleDO> addTestData(List<PeopleDO> list) {

    // 方法一(取出第一条数据添加到mongodb中)
    PeopleDO save = mongoTemplate.save(list.get(0));
    // 方法二（不能获取第一条了，因为编号id重复了，即使id是自动生成的那种类型也不能获取第一条。即使我们编号没有指定，但是第一条数据添加成功，会自动将编号赋值给对象，到这里来编号是有数据的。）
    PeopleDO insert = mongoTemplate.insert(list.get(1));

    // 批量增加
    // 方式一 。返回的是Collection类，List的父类。
    List<PeopleDO> insertAll = (List<PeopleDO>)mongoTemplate.insertAll(list);

    // 方式二
    List<PeopleDO> doList = (List<PeopleDO>)mongoTemplate.insert(list,"peopleDO");

    // 方式三 返回的是Collection类
    List<PeopleDO> lists = (List<PeopleDO>) mongoTemplate.insert(list, PeopleDO.class);

    return (List<PeopleDO>) mongoTemplate.insertAll(list);
}
```

# 三、	携带简单条件查询

## 3.1、查询全部和分页查询

- 关注方法

```java
<T> List<T> findAll(Class<T> entityClass);
<T> List<T> findAll(Class<T> entityClass, String collectionName);
<T> List<T> find(Query query, Class<T> entityClass);
```

第一个方法就直接给出需要查询这个文档的实体类就OK

第二个方法增加一个指定文档名称，不过第一个方法不指定也能搞定了，我觉得这个方法有些多余了。

第三个方法就是将`Query`对象搞成一个空对象，并指定实体类就OK 。	



- 分页查询

就是给`Query`对象中指定 `skip()`和 `limit()`方法的值就OK。

`skip()`方法和 `mongodb`中的 `skip()`方法一样，从第一个开始查询，而 `limit()`方法是查询多少条数据。如果`skip(2)`表示从第2条数据查询。如果是从第 `2` 页查询，需要使用 `(当前页数 - 1) * 每页显示的数据条数` 得到的结果给 `skip()`方法就OK。



- 范例

```java
public List<PeopleDO> findall(Integer page, Integer size) {
    // 查询全部
    mongoTemplate.findAll(PeopleDO.class);

    // 指定集合名称
    mongoTemplate.findAll(PeopleDO.class,"peopleDO");

    // 这样也可以查询全部，就是不带任何条件
    mongoTemplate.find(new Query(),PeopleDO.class);


    /*********  分页查询    **************/

    /***
         * <b>需要注意的</b>
         * 这的 skip 和 limit 和mongodb中的语法一样，skip表示从第几条开始查询，limit表示查询几条。
         *  (page - 1) * size  计算第page 也应该是从第几条数据开始查询。
         */
    //        mongoTemplate.find(new Query().skip((page - 1) * size).limit(size),PeopleDO.class);
    return mongoTemplate.find(new Query().skip((page - 1) * size).limit(size),PeopleDO.class);
}
```



## 3.2、查询返回指定的字段

我们在查询的过程当中，有些时候我们只需要文档中的某些字段，并不需要全部。如果我们每次都去查询全部数据过来，有些浪费。

关注 `com.mongodb.BasicDBObject`这个类。这个类可以调用`Map`集合中的`put(K key, V value)`方法，`key`表示文档中的域，也就是字段。`value`如果为`true`表示该字段显示，`false`为不显示。

- 范例

```java
public List<PeopleDO> findCheckField() {
        DBObject obj = new BasicDBObject();
        //添加条件 userId <= 677 的数据 但是建议条件使用Query对象里面去封装，那里要人性化一些。
        obj.put("userId", new BasicDBObject("$lte", 677));

        // 这里封装返回指定的字段
        BasicDBObject fieldsObject = new BasicDBObject();
        //也可以返回数组内Document的字段
        fieldsObject.put("userId", true);
        //返回普通字段
        fieldsObject.put("age", true);
        fieldsObject.put("status", true);
        fieldsObject.put("_id", false);
        // BasicDBObject 查询条件在这个对象里面封装不太方便，所以条件就统一放到 Query对象里面吧。
        Query query = new BasicQuery( obj.toString(), fieldsObject.toString());
        return mongoTemplate.find(query,PeopleDO.class);
    }
```

`DBObject`这个对象也可以添加条件，但是条件放到`Query`这里面封装比较合适，稍微要人性化一些。虽然`DBObject`这个对象中可以封装条件，但是没有在`Query`对象里面封装方便。平时开发的时候我们可以这样写。

```java
 // 这里封装返回指定的字段
BasicDBObject fieldsObject = new BasicDBObject();
//也可以返回数组内Document的字段
fieldsObject.put("userId", true);
//返回普通字段
fieldsObject.put("age", true);
fieldsObject.put("status", true);
fieldsObject.put("_id", false);

Query query = new BasicQuery(null,fieldsObject.toString());
```

## 3.3、常见的`and`和`or`拼接多个条件

- `"status" : 1`的所有数据

```java
mongoTemplate.find(new Query(Criteria.where("status").is(1)),PeopleDO.class);
```

- 查询 `"status" : 1 and "age" : 22` 所有数据

```java
mongoTemplate.find(new Query(Criteria.where("status").is(1).and("age").is(22)),PeopleDO.class);
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200914154720437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### status ！= 1

```java
mongoTemplate.find(new Query(Criteria.where("status").ne(1)),PeopleDO.class);
```

打印日志

```lua
find using query: { "status" : { "$ne" : 1 } } fields: Document{{}} for class: class com.mongodb.model.PeopleDO in collection: peopleDO
```

### status ！= 0 and age = 20

```java
mongoTemplate.find(new Query(Criteria.where("status").ne(0).and("age").is(20)),PeopleDO.class);
```

打印日志，只截取关键部分

```lua
find using query: { "status" : { "$ne" : 0 }, "age" : 20 } fields: Document{{}} 
```

### status ！= 0 and age = 20 and userId = 101

```java
mongoTemplate.find(new Query(Criteria.where("status").ne(0).and("age").is(20).and("userId").is(101)),PeopleDO.class);
```

```json
find using query: { "status" : { "$ne" : 0 }, "age" : 20, "userId" : 101 } fields: Document{{}} 
```



### age = 20 or age = 21

```java
mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21))),PeopleDO.class);
```

将这个语句拆分开

```java
Query query = new Query();
Criteria criteria = new Criteria();
// 这里拼接or 相关的条件
criteria.orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21));
// 将条件添加到query对象中
query.addCriteria(criteria);
return mongoTemplate.find(query,PeopleDO.class);
```



```lua
find using query: { "$or" : [{ "age" : 20 }, { "age" : 21 }] } fields: Document{{}}
```

<font style="font-weight: bold;color:rgb(200,20,20);font-size:20px;">注意：这里的拼接“或者”`orOperator`这个方法不是静态的，所以需要new 一下Criteria对象。`orOperator`这个方法是传入多个`Criteria`对象，也就是使用的时候注意，一个or条件需要传入两个Criteria对象。例如：这里的 age = 20 是一个 Criteria对象，age = 21 也是一个Criteria对象。</font>



### status= 0 and age = 20 or age = 21 or age = 25 or userId != 287

```java
 mongoTemplate.find(new Query(Criteria
                .where("status")
                .is(0)
                .orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21),Criteria.where("age").is(25),
                        Criteria.where("userId").ne(287)
                )
        ),PeopleDO.class);
```

```lua
find using query: { "status" : 0, "$or" : [{ "age" : 20 }, { "age" : 21 }, { "age" : 25 }, { "userId" : { "$ne" : 287 } }] } fields: Document{{}}
```

这里首先是筛选出  `status= 0`的所以数据，然后在筛选 age = 20 的也筛选了，等于21、25 都筛选了。但是后面还有一个 `userId != 287`的也筛选了，这样数据就多了，如果userId = 287 ,那么age = 20 或者 21 、25 的同样也能筛选。

## 3.4、in使用

```java
List<Integer > lists = new ArrayList<>();
lists.add(21);
lists.add(22);
lists.add(23);
return mongoTemplate.find(new Query(Criteria.where("age").in(lists)),PeopleDO.class);
```

```
 find using query: { "age" : { "$in" : [21, 22, 23] } } fields: Document{{}} 
```

# 常见的错误

如果添加的`id`也就是`MongoDB`中的 `_id`出现重复是报一下异常。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200912140507590.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



