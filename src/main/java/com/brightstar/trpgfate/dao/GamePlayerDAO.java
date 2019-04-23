package com.brightstar.trpgfate.dao;

import com.brightstar.trpgfate.dao.po.GamePlayer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GamePlayerDAO {
    @Insert("insert into game_player(game_guid, user_id, character_guid) " +
            "values(#{gameGuid}, #{userId}, #{characterGuid});")
    int insert(GamePlayer gamePlayer);

    @Update("update game_player " +
            "set character_guid=#{characterGuid} " +
            "where game_guid=#{gameGuid} and user_id=#{userId};")
    int updateCharacter(GamePlayer gamePlayer);

    @Delete("delete from game_player " +
            "where game_guid=#{gameGuid} and user_id=#{userId};")
    int delete(GamePlayer gamePlayer);

    @Select("select game_guid as gameGuid, user_id as userId, character_guid as characterGuid from game_player " +
            "where game_guid=#{gameGuid};")
    List<GamePlayer> getGamePlayersByGameId(@Param("gameGuid") byte[] gameGuid);

    @Select("select game_guid as gameGuid, user_id as userId, character_guid as characterGuid from game_player " +
            "where user_id=#{userId} " +
            "limit #{start},#{length};")
    List<GamePlayer> getGamePlayersByPlayerId(@Param("userId") int userId, int start, int length);

    @Select("select count(game_guid) from game_player " +
            "where user_id=#{userId};")
    int getGamePlayersCountByPlayerId(@Param("userId") int userId);

    @Select("select game_guid as gameGuid, user_id as userId, character_guid as characterGuid from game_player " +
            "where game_guid=#{gameGuid} and user_id=#{userId};")
    GamePlayer get(@Param("gameGuid") byte[] gameGuid, @Param("userId") int userId);
}
