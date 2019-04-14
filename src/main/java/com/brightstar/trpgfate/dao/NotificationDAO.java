package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NotificationDAO {
    @Insert("insert into notification(user_id, last_view_follower_time, last_read_announcement_id) " +
            "values(#{userId}, #{lastViewFollowerTime}, #{lastReadAnnouncementId});")
    int insert(Notification notification);

    @Update("update notification " +
            "set last_view_follower_time=#{lastViewFollowerTime}, last_read_announcement_id=#{lastReadAnnouncementId} " +
            "where user_id=#{userId};")
    int update(Notification notification);

    @Select("select user_id as userId, last_view_follower_time as lastViewFollowerTime, last_read_announcement_id as lastReadAnnouncementId from notification " +
            "where user_id=#{userId};")
    Notification getByUserId(@Param("userId") int userId);
}
