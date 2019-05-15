package com.brightstar.trpgfate.controller.restful.console.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.restful.console.vo.GameInstanceGetResp;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.GameTableService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GameTable;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController("consoleGameInstance")
@RequestMapping("/console/game-instance")
public final class GameInstanceController {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameTableService gameTableService;

    @GetMapping
    public GameInstanceGetResp getAllGameInstances() {
        List<GameTable> gameTables = gameTableService.getAllGameTables();
        ArrayList<GameInstanceGetResp.GameInstance> respList = new ArrayList<>();
        for (GameTable gameTable : gameTables) {
            GameTable.RunningGame runningGame = gameTable.getRunningGame();
            if (runningGame == null) continue;
            GameInstanceGetResp.GameInstance resp = new GameInstanceGetResp.GameInstance();
            resp.setGameUuid(gameTable.getGameUuid());
            User dm = gameTable.getDm();
            if (dm != null) {
                resp.setDmId(dm.getId());
            } else {
                resp.setDmId(-1);
            }
            User[] players = gameTable.getPlayers();
            int[] playersId = new int[players.length];
            for (int i = 0; i < players.length; ++i) {
                playersId[i] = players[i].getId();
            }
            resp.setPlayersId(playersId);
            resp.setCreateTime(DatetimeConverter.calendar2UtilDate(gameTable.getCreateTime()));
            resp.setLog(runningGame.getLog());
            resp.setSocketAddress(runningGame.getAddress().getHostAddress());
            resp.setSocketPort(runningGame.getPort());
            respList.add(resp);
        }
        GameInstanceGetResp ret = new GameInstanceGetResp();
        ret.setGameInstances(respList);
        return ret;
    }

    @DeleteMapping("/{gameUuid}")
    public void forceCloseGameTable(@PathVariable String gameUuid) {
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        gameTableService.forceStopRunningGame(game);
    }
}
