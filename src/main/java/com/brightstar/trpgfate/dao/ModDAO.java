package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Mod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModDAO {
    @Insert("insert into `mod`(guid, user_id, author_id, create_time, last_publish_time, title, description) " +
            "values(#{guid}, #{userId}, #{authorId}, #{createTime}, #{lastPublishTime}, #{title}, #{description});")
    int insert(Mod mod);

    @Update("update `mod` set title=#{title}, description=#{description}, last_publish_time=#{lastPublishTime} " +
            "where guid=#{guid};")
    void updateInfo(Mod mod);

    @Delete("delete from `mod` " +
            "where guid=#{guid};")
    void delete(Mod mod);

    @Select("select guid, user_id as userId, author_id as authorId, " +
            "create_time as createTime, last_publish_time as lastPublishTime, title, description " +
            "from `mod` where guid=#{guid};")
    Mod getByGuid(@Param("guid") byte[] guid);

    @Select("select guid, user_id as userId, author_id as authorId, " +
            "create_time as createTime, last_publish_time as lastPublishTime, title, description " +
            "from `mod` where user_id=#{userId} " +
            "limit #{start},#{length};")
    List<Mod> findModsByUserId(@Param("userId") int userId, @Param("start") int start, @Param("length") int length);

    @Select("select count(guid) from `mod` " +
            "where user_id=#{userId};")
    int getModCountByUserId(@Param("userId") int userId);
}
