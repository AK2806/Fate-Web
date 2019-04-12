package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Character;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CharacterDAO {

    @Insert("insert into `character`(guid, user_id) " +
            "values(#{guid}, #{userId});")
    int insert(Character character);

    @Delete("delete from `character` where guid=#{guid} and user_id=#{userId};")
    int remove(Character character);

    @Select("select guid, user_id as userId from `character` " +
            "where guid=#{guid};")
    Character getByGuid(@Param("guid") byte[] guid);

    @Select("select guid, user_id as userId from `character` " +
            "where user_id=#{userId} " +
            "limit #{start},#{length};")
    List<Character> findByUserId(@Param("userId") int userId, @Param("start") int start, @Param("length") int length);

    @Select("select count(guid) from `character` " +
            "where user_id=#{userId};")
    int getCountByUserId(@Param("userId") int userId);
}
