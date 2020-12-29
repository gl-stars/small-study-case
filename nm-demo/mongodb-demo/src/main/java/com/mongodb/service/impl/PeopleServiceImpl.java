package com.mongodb.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.model.PeopleDO;
import com.mongodb.model.UserDO;
import com.mongodb.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: gl_stars
 * @data: 2020年 09月 12日 11:54
 **/
@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private MongoTemplate mongoTemplate ;

    /***
     * <p>这里添加单个对象或者批量添加，批量添加处理 {@link MongoTemplate#insertAll(java.util.Collection)}这个方法之外，其他
     * 的方法都必须添指定集合名称，有些方法让你直接写 {@code PeopleDO.class} 这样，其实间接的已经指定了集合名称了。
     * <b>这里特别需要注意这个批量添加方法，{@link MongoTemplate#insert(java.lang.Object)}这个方法的参数是Object，但是你传入一个集合进去，是不能添加的。</b>
     * </p>
     * @param list
     * @return
     */
    @Override
    public List<PeopleDO> addTestData(List<PeopleDO> list) {

        // 方法一(取出第一条数据添加到mongodb中)
//        PeopleDO save = mongoTemplate.save(list.get(0));
        // 方法二（不能获取第一条了，因为编号id重复了，即使id是自动生成的那种类型也不能获取第一条。即使我们编号没有指定，但是第一条数据添加成功，会自动将编号赋值给对象，到这里来编号是有数据的。）
//        PeopleDO insert = mongoTemplate.insert(list.get(1));

        // 批量增加
        // 方式一 。返回的是Collection类，List的父类。
//        List<PeopleDO> insertAll = (List<PeopleDO>)mongoTemplate.insertAll(list);

        // 方式二
//        List<PeopleDO> doList = (List<PeopleDO>)mongoTemplate.insert(list,"peopleDO");

        // 方式三 返回的是Collection类
//        List<PeopleDO> lists = (List<PeopleDO>) mongoTemplate.insert(list, PeopleDO.class);

        return (List<PeopleDO>) mongoTemplate.insertAll(list);
    }

    @Override
    public List<PeopleDO> findall(Integer page, Integer size) {
        // 查询全部
//        mongoTemplate.findAll(PeopleDO.class);

        // 指定集合名称
//        mongoTemplate.findAll(PeopleDO.class,"peopleDO");

        // 这样也可以查询全部，就是不带任何条件
//        mongoTemplate.find(new Query(),PeopleDO.class);


        /*********  分页查询    **************/

        /***
         * <b>需要注意的</b>
         * 这的 skip 和 limit 和mongodb中的语法一样，skip表示从第几条开始查询，limit表示查询几条。
         *  (page - 1) * size  计算第page 也应该是从第几条数据开始查询。
         */
//        mongoTemplate.find(new Query().skip((page - 1) * size).limit(size),PeopleDO.class);
            return mongoTemplate.find(new Query().skip((page - 1) * size).limit(size),PeopleDO.class);
    }

    @Override
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
//        Query query = new BasicQuery( obj.toString(), fieldsObject.toString());

        Query query = new BasicQuery( null, fieldsObject.toString());

        // 这样写也OK的。
        Query query1 = new BasicQuery( null, fieldsObject.toString());
        return mongoTemplate.find(query,PeopleDO.class);
    }

    /**
     * 返回指定字段，另一个中写法
     * @return
     */
    public List<PeopleDO> findList(){
        // 这里封装返回指定的字段
        BasicDBObject fieldsObject = new BasicDBObject();
        //也可以返回数组内Document的字段
        fieldsObject.put("userId", true);
        //返回普通字段
        fieldsObject.put("age", true);
        fieldsObject.put("status", true);
        fieldsObject.put("_id", false);

        Query query = new BasicQuery(null,fieldsObject.toString());
        return mongoTemplate.find(query,PeopleDO.class) ;
    }

    /***
     * 普通条件查询
     * @param page 当前页
     * @param size 每页显示的条数
     * @return
     */
    @Override
    public List<PeopleDO> findCondition(Integer page, Integer size) {
//        return findStatusEqualOne(page,size);
//        return findStatusAndAge() ;
        return findOderBy() ;
    }

    /***
     * 查询  "status" : 1 的数据
     * @param page
     * @param size
     * @return
     */
    private List<PeopleDO> findStatusEqualOne(Integer page,Integer size){
        // 这是不分页
//        mongoTemplate.find(new Query(Criteria.where("status").is(1)),PeopleDO.class);
        // 分页查询
        return mongoTemplate.find(new Query(Criteria.where("status").is(1)).skip((page - 1) * size).limit(size),PeopleDO.class);
    }

    /***
     * 查询 "status" : 1 and "age" : 22 所有数据
     * @return
     */
    private List<PeopleDO> findStatusAndAge(){
        return mongoTemplate.find(new Query(Criteria.where("status").is(1).and("age").is(22)),PeopleDO.class);
    }

    /***
     * 查询 status ！= 1
     * @return
     */
    private List<PeopleDO> findStatusNoOne(){
        return mongoTemplate.find(new Query(Criteria.where("status").ne(1)),PeopleDO.class);
    }

    /**
     * 查询 status ！= 0 and age = 20
     * @return
     */
    private List<PeopleDO> findStatusNoZeroAndAgeEtcTwenty(){
        return mongoTemplate.find(new Query(Criteria.where("status").ne(0).and("age").is(20)),PeopleDO.class);
    }

    /***
     * 查询 status ！= 0 and age = 20 and userId = 101
     * @return
     */
    private List<PeopleDO> findStatusNoZeroAndAgeEtcTwentyAndUserId(){
        return mongoTemplate.find(new Query(Criteria.where("status").ne(0).and("age").is(20).and("userId").is(101)),PeopleDO.class);
    }

    /**
     * 查询 age = 20 or age = 21
     * @return
     */
    private List<PeopleDO> findStatusOrAge(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 这里拼接or 相关的条件
        criteria.orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21));
        // 将条件添加到query对象中
        query.addCriteria(criteria);
        return mongoTemplate.find(query,PeopleDO.class);
        // 将这个语句合并起来实现
//        return mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21))),PeopleDO.class);
    }

    /**
     * 查询 status= 0 and age = 20 or age = 21 or age = 25 or userId != 287
     * @return
     */
    private List<PeopleDO> findStatusAndAgeOruserId(){
        return mongoTemplate.find(new Query(Criteria
                .where("status")
                .is(0)
                .orOperator(Criteria.where("age").is(20),Criteria.where("age").is(21),Criteria.where("age").is(25),
                        Criteria.where("userId").ne(287)
                )
        ),PeopleDO.class);
    }
    private List<PeopleDO> findAges(){
        List<Integer > lists = new ArrayList<>();
        lists.add(21);
        lists.add(22);
        lists.add(23);
        return mongoTemplate.find(new Query(Criteria.where("age").in(lists)),PeopleDO.class);
    }

    /**
     * 查询 前七天和后七天之间的数据
     * @return
     */
    public List<PeopleDO> findLately(){
//        LocalDateTime.now().plusDays(0 - 7) 获取前七天的时间
        return mongoTemplate.find(new Query(
                Criteria.where("addTime")
                        .gte(LocalDateTime.now().plusDays(0 - 7))
                        .lte(LocalDateTime.now().plusDays(0 + 7))),
                PeopleDO.class);
    }

    /**
     * 去重复查询
     * <p>
     *     查询 status = 1 并且 status 不能重复，去重复查询
     * 	public <T> List<T> findDistinct(Query query, String field, Class<?> entityClass, Class<T> resultClass) {
     * 	query：查询条件
     * 	field：去重复的字段名，查询结果也只会返回该字段的数据
     * 	entityClass：当前对象
     * 	resultClass：返回结果对象
     * </p>
     * @return
     */
    public List<PeopleDO> findDistrictId(){
        return mongoTemplate.findDistinct(new Query(Criteria.where("age").is(20)),"userId","peopleDO",PeopleDO.class);
    }

    /****
     * 排序查询
     * <p>一下提供的这两条数据都满足排序</p>
     * @return
     */
    public List<PeopleDO> findOderBy(){
        return mongoTemplate.find(
                new Query()
//                        .with(Sort.by(Sort.Direction.DESC,"age"))
                        .with(Sort.by(Sort.Order.desc("age")))
                        .limit(1000)
                ,PeopleDO.class);
    }
    /***
     * 删除
     * @return
     */
    @Override
    public Boolean delete() {
        // 在指定的集合中删除指定的对象（在user集合中删除spouse这个对象）
//        mongoTemplate.remove("spouse","user");
        // 根据昵称删除
        mongoTemplate.remove(new Query(Criteria.where("userId").is(101)), PeopleDO.class);

        // 根据昵称删除，指定集合名称
        mongoTemplate.remove(new Query(Criteria.where("userId").is("102")),"peopleDO");

        // 根据给定的查询条件删除文档，如果文档存在，将需要删除的这个文档编号转换为 UserDO.class 给定实体类的编号类型。并且指定集合名称。
        // 多余了，查询哪个集合都知道了，id会不一样吗？里面的数据都是存到实体类在存进去的。
        mongoTemplate.remove(new Query(Criteria.where("userId").is(103)),UserDO.class,"peopleDO");

        // 根据条件查询，查询出来出来的数据返回第一条数据，并且将这条数据从集合中删除。(返回一条)
        PeopleDO id2 = mongoTemplate.findAndRemove(new Query(Criteria.where("status").is(0)), PeopleDO.class);
        System.out.println(id2);

        // 和上面这个效果一样，只是指定集合名称而已。(返回一条)
        PeopleDO peopleDO = mongoTemplate.findAndRemove(new Query(Criteria.where("age").is(35)), PeopleDO.class, "peopleDO");
        System.out.println(peopleDO);

        // 查询返回全部匹配的数据，并将这些数据删除。（全部）
        List<PeopleDO> age = mongoTemplate.findAllAndRemove(new Query(Criteria.where("age").is(36)), PeopleDO.class);
        return null;
    }

    /***
     * 这三个更改方法，返回值都是 {@link UpdateResult}对象，这个对象具有以下常见的方法。
     * <p>
     *      {@link UpdateResult#wasAcknowledged()}更改成功返回true，失败返回false。
     *      {@link UpdateResult#getMatchedCount()} 获取查询匹配的文档数
     *      {@link UpdateResult#getModifiedCount()} 更改数量
     * </p>
     * @see UpdateResult
     * @see MongoTemplate
     */
    @Override
    public Boolean upadtePeople() {
        // 封装更改的条件
        Query query = new Query(Criteria.where("id").is("5f5f236847f2173694b79315"));

        // 封装更改的内容(Update中有很多运算符，可以选择使用)
        Update update = new Update();
        update.set("age", 18);
        update.set("status", 1);
        // 只更改一条数据 （根据id更改昵称）
        mongoTemplate.updateFirst(query,update, PeopleDO.class);


        // 存在就更新，不存在就插入  （根据id更改昵称）
        UpdateResult upsert = mongoTemplate.upsert(new Query(Criteria.where("id").is(2122)), new Update().set("name","张三").set("add","北京"), PeopleDO.class);


        // 更改所有匹配条件的数据
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, PeopleDO.class);

        // true 更改成功
        boolean b = updateResult.wasAcknowledged();

        // 更根据条件匹配的总条数
        long matchedCount = updateResult.getMatchedCount();

        // 最终被更改的数量
        long modifiedCount = updateResult.getModifiedCount();
        return null;
    }

    /***
     * 更改指定的数据，如果存在就更改，如果更改的这个字段不存在就添加。如果条件不符合的就重新添加一条数据。
     * <b>注意：这里只能添加一条数据。</b>
     * @return
     */
    @Override
    public Boolean updateUpsert(){
        /***
         * 更改 stateCode 字段的值为 “这是最后添加上的字段”
         * 如果 stateCode 字段不存在就添加这个字段
         * <p>注意：这样写只能更改一条数据</p>
         */
        return mongoTemplate.upsert(new Query(Criteria.where("status").is(1)), new Update().set("stateCode","这是最后添加上的字段"),"peopleDO").wasAcknowledged();

        /***
         * 更改 stateCode 字段的值为 “这是最后添加上的字段”
         * 如果 stateCode 字段不存在就添加这个字段
         * 并且删除 stateaaa 这个字段
         */
//        return mongoTemplate.upsert(new Query(Criteria.where("status").is(1)), new Update().set("stateCode00","这是最后添加上的字段").unset("stateaaa"),"peopleDO").wasAcknowledged();
    }


    /***
     * 嵌套更改，更改UserDO类中汽车这个类
     * @param User
     */
    public void updateNesting(UserDO user){
        mongoTemplate.updateFirst(
                new Query(),
                new Update().set("car.name","奔驰").set("car.manufactor","奔驰厂家"),
                UserDO.class);
    }
}
