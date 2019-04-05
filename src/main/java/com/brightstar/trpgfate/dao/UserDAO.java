package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@Mapper
public interface UserDAO {

    @Insert("insert into user(id, passwd_hash, role, create_time, active, email)" +
            "values(#{id}, #{passwdSha256Salted}, #{role}, #{createTime}, #{active}, #{email});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Update("update user set passwd_hash=#{passwdSha256Salted} where id=#{id};")
    int updatePassword(User user);

    @Select("select id, email, passwd_hash as passwdSha256Salted, role, create_time as createTime, active from user where id=#{id};")
    User getById(@Param("id") int id);

    @Select("select id, email, passwd_hash as passwdSha256Salted, role, create_time as createTime, active from user where email=#{email};")
    User findByEmail(@Param("email") String email);
}
