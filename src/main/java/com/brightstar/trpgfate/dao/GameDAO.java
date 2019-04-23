package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Game;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GameDAO {
    @Insert("insert into game(guid, user_id, mod_guid, create_time, status, title) " +
            "values(#{guid}, #{userId}, #{modGuid}, #{createTime}, #{status}, #{title});")
    int insert(Game game);

    @Update("update game " +
            "set status=#{status} " +
            "where guid=#{guid};")
    int updateStatus(Game game);

    @Update("update game " +
            "set title=#{title} " +
            "where guid=#{guid};")
    int updateInfo(Game game);

    @Delete("delete from game " +
            "where guid=#{guid};")
    int delete(Game game);

    @Select("select guid, user_id as userId, mod_guid as modGuid, create_time as createTime, status, title from game " +
            "where guid=#{guid};")
    Game getByGUID(@Param("guid") byte[] guid);

    @Select("select guid, user_id as userId, mod_guid as modGuid, create_time as createTime, status, title from game " +
            "where user_id=#{userId} " +
            "limit #{start},#{length};")
    List<Game> getGamesByUserId(@Param("userId") int userId, @Param("start") int start, @Param("length") int length);

    @Select("select count(guid) from game " +
            "where user_id=#{userId};")
    int getGameCountByUserId(@Param("userId") int userId);
}
