package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Follower;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FollowerDAO {
    @Insert("insert into follower(user_id, follower_id, time) " +
            "values(#{userId}, #{followerId}, #{time});")
    int insert(Follower follower);

    @Delete("delete from follower where user_id=#{userId} and follower_id=#{followerId};")
    int remove(Follower follower);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where user_id=#{userId};")
    List<Follower> findByUserId(int userId);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where follower_id=#{followerId};")
    List<Follower> findByFollowerId(int followerId);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where user_id=#{userId} and follower_id=#{followerId};")
    Follower get(int userId, int followerId);
}