package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.Announcement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Mapper
public interface AnnouncementDAO {

    @Insert("insert into announcement(create_time, title, content) " +
            "values(#{createTime}, #{title}, #{content});")
    int insert(Announcement announcement);

    @Select("select create_time as createTime, title, content from announcement " +
            "order by create_time desc " +
            "limit #{start},#{length};")
    List<Announcement> getAnnouncements(@Param("start") int start, @Param("length") int length);

    @Select("select count(create_time) from announcement " +
            "where create_time>=#{from};")
    int getCount(@Param("from") Timestamp from);
}
