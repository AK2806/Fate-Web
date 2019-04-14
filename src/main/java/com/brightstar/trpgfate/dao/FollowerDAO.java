package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Follower;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
            "where user_id=#{userId} " +
            "limit #{start},#{length};")
    List<Follower> findFollowerByUserId(@Param("userId") int userId, @Param("start") int start, @Param("length") int length);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where user_id=#{userId} and time>=#{fromTime} " +
            "limit #{start},#{length};")
    List<Follower> findFollowerByUserIdAndTime(@Param("userId") int userId, @Param("fromTime") Timestamp from, @Param("start") int start, @Param("length") int length);

    @Select("select count(follower_id) from follower " +
            "where user_id=#{userId};")
    int getFollowerCountByUserId(@Param("userId") int userId);

    @Select("select count(follower_id) from follower " +
            "where user_id=#{userId} and time>=#{fromTime};")
    int getFollowerCountByUserIdAndTime(@Param("userId") int userId, @Param("fromTime") Timestamp from);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where follower_id=#{followerId} " +
            "limit #{start},#{length};")
    List<Follower> findFolloweeByFollowerId(@Param("followerId") int followerId, @Param("start") int start, @Param("length") int length);

    @Select("select count(user_id) from follower " +
            "where follower_id=#{followerId};")
    int getFolloweeCountByFollowerId(@Param("followerId") int followerId);

    @Select("select user_id as userId, follower_id as followerId, time from follower " +
            "where user_id=#{userId} and follower_id=#{followerId};")
    Follower get(@Param("userId") int userId, @Param("followerId") int followerId);
}
