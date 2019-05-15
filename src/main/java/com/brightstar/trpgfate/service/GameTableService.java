package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GameTable;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;

import java.util.List;

public interface GameTableService {
    void createGameTable(User dm, Game game) throws InvalidGameStateException;
    void keepJoinGameTable(User user, Game game) throws InvalidGameStateException, UserDoesntExistException;
    void gameTableUserLeaved(Game game, User user);
    List<GameTable> getAllGameTables();
    GameTable getGameTable(Game game);

    void runGame(Game game) throws InvalidGameStateException;
    void forceStopRunningGame(Game game);
    default boolean isGameRunning(Game game) {
        GameTable gameTable = getGameTable(game);
        return gameTable != null && gameTable.getRunningGame() != null;
    }
}
