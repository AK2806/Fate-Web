package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NotificationDAO {
    @Insert("insert into notification(user_id, last_time_read) " +
            "values(#{userId}, #{lastTimeRead});")
    int insert(Notification notification);

    @Update("update notification " +
            "set last_time_read=#{lastTimeRead} " +
            "where user_id=#{userId};")
    int update(Notification notification);

    @Select("select user_id as userId, last_time_read as lastTimeRead from notification " +
            "where user_id=#{userId};")
    Notification getByUserId(@Param("userId") int userId);
}
