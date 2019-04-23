package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.GamingRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GamingRecordDAO {

    @Insert("insert into gaming_record(game_guid, instance_guid, begin_time, end_time) " +
            "values(#{gameGuid}, #{instanceGuid}, #{beginTime}, #{endTime});")
    int insert(GamingRecord record);

    @Select("select game_guid as gameGuid, instance_guid as instanceGuid, begin_time as beginTime, end_time as endTime from gaming_record " +
            "where game_guid=#{gameGuid};")
    List<GamingRecord> getGamingRecords(@Param("gameGuid") byte[] gameGuid);

    @Select("select game_guid as gameGuid, instance_guid as instanceGuid, begin_time as beginTime, end_time as endTime from gaming_record " +
            "where game_guid=#{gameGuid} order by begin_time desc limit 1;")
    GamingRecord getLastRecordByGameId(@Param("gameGuid") byte[] gameGuid);
}
