package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Announcement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Mapper
public interface AnnouncementDAO {

    @Insert("insert into announcement(id, title, content, create_time) " +
            "values(#{id}, #{title}, #{content}, #{createTime});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Announcement announcement);

    @Select("select id, title, content, create_time as createTime from announcement " +
            "order by id desc " +
            "limit #{start},#{length};")
    List<Announcement> getAnnouncements(@Param("start") int start, @Param("length") int length);

    @Select("select id, title, content, create_time as createTime from announcement " +
            "order by id desc limit 1;")
    Announcement getLastAnnouncement();

    @Select("select count(id) from announcement " +
            "where id>=#{startId};")
    int getCount(@Param("startId") int startId);
}
