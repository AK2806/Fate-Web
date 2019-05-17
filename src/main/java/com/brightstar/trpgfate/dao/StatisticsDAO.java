package com.brightstar.trpgfate.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@Mapper
public interface StatisticsDAO {
    @Select("select count(id) from user;")
    int getUsersCount();

    @Select("select count(game_guid) from gaming_record where begin_time>=#{timeFrom} and begin_time<=#{timeTo};")
    int getRecordsCountBetween(@Param("timeFrom") Timestamp timeFrom, @Param("timeTo") Timestamp timeTo);

    @Select("select count(game_guid) from gaming_record;")
    int getRecordsCount();
}
