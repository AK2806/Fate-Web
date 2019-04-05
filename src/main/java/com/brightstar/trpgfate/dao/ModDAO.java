package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Mod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModDAO {
    @Insert("insert into mod(guid, user_id, title, thumbnail, origin) " +
            "values(#{guid}, #{userId}, #{title}, #{thumbnail}, #{originModGuid});")
    int insert(Mod mod);

    @Update("update mod set title=#{title}, thumbnail=#{thumbnail}" +
            "where guid=#{guid} and user_id=#{userId};")
    int update(Mod mod);

    @Delete("delete from mod where guid=#{guid} and user_id=#{userId};")
    int remove(Mod mod);

    @Select("select guid, user_id as userId, title, thumbnail, origin as originModGuid from mod " +
            "where guid=#{guid};")
    Mod getByGuid(@Param("guid") byte[] guid);

    @Select("select guid, user_id as userId, title, thumbnail, origin as originModGuid from mod " +
            "where user_id=#{userId};")
    List<Mod> findByUserId(@Param("userId") int userId);
}
