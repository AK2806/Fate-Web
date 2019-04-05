package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@Mapper
public interface AccountDAO {

    @Insert("insert into account(user_id, name, avatar, gender, birthday, residence, privacy) " +
            "values(#{userId}, #{name}, #{avatarId}, #{gender}, #{birthday}, #{residence}, #{privacy});")
    int insert(Account account);

    @Update("update account set name=#{name}, avatar=#{avatarId}, gender=#{gender}, birthday=#{birthday}, residence=#{residence}, privacy=#{privacy} " +
            "where user_id=#{userId};")
    int update(Account account);

    @Select("select user_id as userId, name, avatar as avatarId, gender, birthday, residence, privacy from account where user_id=#{id};")
    Account findById(@Param("id") int id);
}
