package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Character;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CharacterDAO {

    @Insert("insert into character(guid, user_id, name, portrait) " +
            "values(#{guid}, #{userId}, #{name}, #{portraitId});")
    int insert(Character character);

    @Update("update character set name=#{name}, portrait=#{portraitId} where guid=#{guid};")
    int update(Character character);

    @Delete("delete from character where guid=#{guid} and user_id=#{userId};")
    int remove(Character character);

    @Select("select guid, user_id as userId, name, portrait as portraitId from character " +
            "where guid=#{guid};")
    Character getByGuid(byte[] guid);

    @Select("select guid, user_id as userId, name, portrait as portraitId from character " +
            "where user_id=#{userId};")
    List<Character> findByUserId(int userId);
}
