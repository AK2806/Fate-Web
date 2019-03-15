package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {

    @Insert("insert into user values(#{email}, #{passwdSha256Salted}, #{role})")
    int insert(User user);
    @Update("update user set passwd_hash=#{passwdSha256Salted} where email=#{email}")
    void updatePasswd(User user);
    @Select("select email, passwd_hash as passwdSha256Salted, role from user where email = #{email}")
    User findByEmail(String email);
}
