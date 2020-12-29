package com.mongodb.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.model.UserDO;
import com.mongodb.service.UserService;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: gl_stars
 * @data: 2020年 09月 09日 16:53
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate ;

    public Boolean insertUserOne(UserDO userDO){
        // 方法一
        UserDO save = mongoTemplate.save(userDO);
        // 方法二
        UserDO insert = mongoTemplate.insert(userDO);

        // 因为添加成功，会默认将编号赋值给实体对象。
        if (save.getId() != null){
            return true ;
        }
        return false ;
    }


    /**
     * 批量添加
     * @param list
     * @return
     */
    @Override
    public List<UserDO> insertUserList(List<UserDO> list) {
        // 方式一 。返回的是Collection类，List的父类。
        List<UserDO> userList = (List<UserDO>) mongoTemplate.insertAll(list);

        // 方式二
        List<UserDO> insert = mongoTemplate.insert(list);

        // 方式三 返回的是Collection类
        List<UserDO> lists = (List<UserDO>) mongoTemplate.insert(list, UserDO.class);

        return userList;
    }

    /***
     * 这三个更改方法，返回值都是 {@link UpdateResult}对象，这个对象具有以下常见的方法。
     * <p>
     *      {@link UpdateResult#wasAcknowledged()}更改成功返回true，失败返回false。
     *      {@link UpdateResult#getMatchedCount()} 获取查询匹配的文档数
     *      {@link UpdateResult#getModifiedCount()} 更改数量
     * </p>
     * @param nickname
     * @param id
     * @see UpdateResult
     * @see MongoTemplate
     */
    @Override
    public void updateUserOne(String nickname,String id) {
        Query query = new Query(Criteria.where("id").is(id));

        Update update = new Update();
                update.set("nickname", nickname);
        // 存在就更新，不存在就插入  （根据id更改昵称）
        mongoTemplate.upsert(query,update,UserDO.class);

        // 只更改一条数据 （根据id更改昵称）
        mongoTemplate.updateFirst(query,update, User.class);

        // 更改所有匹配条件的数据
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, UserDO.class);

        boolean b = updateResult.wasAcknowledged();
        long matchedCount = updateResult.getMatchedCount();
        long modifiedCount = updateResult.getModifiedCount();
    }

    @Override
    public void updateUserOne(UserDO userDO) {
        mongoTemplate.update(userDO.getClass());
    }

    /**
     * 删除
     * <p>注意返回值</p>
     * <p>有很多删除的方法返回 {@link DeleteResult}对象，这个对象中常用的方法如下：
     * {@link DeleteResult#wasAcknowledged()}删除成功过返回true，失败：false
     * {@link DeleteResult#getDeletedCount()} 删除的总数</p>
     * @param id
     */
    @Override
    public void deleteUser(String id) {
        // 在指定的集合中删除指定的对象（在user集合中删除spouse这个对象）
        mongoTemplate.remove("spouse","user");

        // 根据id删除
        DeleteResult remove = mongoTemplate.remove(id);

        // 根据昵称删除
        mongoTemplate.remove(new Query(Criteria.where("nickname").is("昵称")),UserDO.class);

        // 根据昵称删除，指定集合名称
        mongoTemplate.remove(new Query(Criteria.where("nickname").is("昵称")),"user");

        // 根据给定的查询条件删除文档，如果文档存在，将需要删除的这个文档编号转换为 UserDO.class 给定实体类的编号类型。并且指定集合名称。
        // 多余了，查询哪个集合都知道了，id会不一样吗？里面的数据都是存到实体类在存进去的。
        mongoTemplate.remove(new Query(Criteria.where("nickname").is("昵称")),UserDO.class,"nickname");

        // 根据条件查询，查询出来出来的数据返回第一条数据，并且将这条数据从集合中删除。
        UserDO id2 = mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(id)), UserDO.class);

        // 和上面这个效果一样，只是指定集合名称而已。
        mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(id)), UserDatabase.class,"user");

        // 查询返回全部匹配的数据，并将这些数据删除。
        List<UserDO> id1 = mongoTemplate.findAllAndRemove(new Query(Criteria.where("id").is(id)), UserDO.class);
        // …………类似的方法还有很多，不列举了。
    }

    /****************************       查询（重点）        ********************************************/
}
