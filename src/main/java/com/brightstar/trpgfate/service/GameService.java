package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GamingRecord;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.ModDoesntExistException;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game createGame(User dm, UUID modId, String title) throws ModDoesntExistException;
    Game getGame(UUID gameId);
    void invalidGame(Game game);
    void updateGameStatus(Game game) throws InvalidGameStateException;
    void updateGameInfo(Game game);
    List<GamingRecord> getGamingRecords(Game game);

    void addPlayer(Game game, User player) throws UserConflictException, InvalidGameStateException;
    void setPlayerCharacter(Game game, User player, Character character) throws UserDoesntExistException, InvalidGameStateException;
    Character getPlayerCharacter(Game game, User player) throws UserDoesntExistException;
    List<User> getPlayersOfGame(Game game);
    boolean removePlayer(Game game, User player) throws InvalidGameStateException;
    boolean isPlayerJoinedGame(Game game, User player);

    List<Game> getCreatedGameByUserAsPlayer(User user, int bundle);
    int getCreatedGameBundleCountByUserAsPlayer(User user);
    List<Game> getCreatedGameByUserAsDM(User user, int bundle);
    int getCreatedGameBundleCountByUserAsDM(User user);
}
