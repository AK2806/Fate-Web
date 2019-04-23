package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Mod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModDAO {
    @Insert("insert into mod(guid, user_id, origin, active) " +
            "values(#{guid}, #{userId}, #{originModGuid}, 1);")
    int insert(Mod mod);

    @Update("update mod set active=1 " +
            "where guid=#{guid};")
    void active(Mod mod);

    @Update("update mod set active=0 " +
            "where guid=#{guid};")
    void inactive(Mod mod);

    @Select("select guid, user_id as userId, origin as originModGuid from mod " +
            "where guid=#{guid};")
    Mod getByGuid(@Param("guid") byte[] guid);

    @Select("select guid, user_id as userId, origin as originModGuid from mod " +
            "where user_id=#{userId} and active=1 " +
            "limit #{start},#{length};")
    List<Mod> findActiveModsByUserId(@Param("userId") int userId, @Param("start") int start, @Param("length") int length);

    @Select("select count(guid) from mod " +
            "where user_id=#{userId} and active=1;")
    int getActiveModCountByUserId(@Param("userId") int userId);
}
