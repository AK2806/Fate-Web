package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.*;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.GameTableService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GameTable;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController("personaGameTable")
@RequestMapping("/persona/game-table")
public final class GameTableController {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameTableService gameTableService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @GetMapping
    public GameTableGetResp getGameTableData(HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        List<GameTable> gameTables = gameTableService.getAllGameTables();
        GameTableGetResp ret = new GameTableGetResp();
        GameTableGetResp.GameTableData[] data = new GameTableGetResp.GameTableData[gameTables.size()];
        int i = 0;
        for (GameTable gameTable : gameTables) {
            GameTableGetResp.GameTableData d = data[i++] = new GameTableGetResp.GameTableData();
            d.setGameUuid(gameTable.getGameUuid().toString());
            User dm = gameTable.getDm();
            if (dm != null) {
                d.setDmId(dm.getId());
            } else {
                d.setDmId(-1);
            }
            User[] players = gameTable.getPlayers();
            int[] playersId = new int[players.length];
            for (int j = 0; j < playersId.length; ++j) {
                playersId[j] = players[j].getId();
            }
            d.setPlayersId(playersId);
            d.setPlaying(gameTable.getRunningGame() != null);
            Game game = gameService.getGame(gameTable.getGameUuid());
            d.setAbleToParticipate(game.getBelongUserId() == self.getId() || gameService.isPlayerJoinedGame(game, self));
        }
        ret.setGameTableData(data);
        return ret;
    }

    @PostMapping
    public void openGameTable(@RequestBody @Valid GameTablePostReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Game game = gameService.getGame(UUID.fromString(req.getGameUuid()));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (self.getId() != game.getBelongUserId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        try {
            gameTableService.createGameTable(self, game);
        } catch (InvalidGameStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Nullable
    @PutMapping("/{gameUuid}")
    public GameTablePutResp activeIntoGameTable(@PathVariable String gameUuid, @RequestBody @Valid GameTablePutReq req,
                                                HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (req.isLeave()) {
            gameTableService.gameTableUserLeaved(game, self);
            return null;
        } else {
            try {
                gameTableService.keepJoinGameTable(self, game);
            } catch (InvalidGameStateException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } catch (UserDoesntExistException e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            GameTable gameTable = gameTableService.getGameTable(game);
            GameTablePutResp ret = new GameTablePutResp();
            ret.setGameUuid(gameUuid);
            User dm = gameTable.getDm();
            if (dm != null) {
                ret.setDmId(dm.getId());
            } else {
                ret.setDmId(-1);
            }
            User[] players = gameTable.getPlayers();
            int[] playersId = new int[players.length];
            for (int i = 0; i < playersId.length; ++i) {
                playersId[i] = players[i].getId();
            }
            ret.setPlayersId(playersId);
            ret.setPlaying(gameTable.getRunningGame() != null);
            return ret;
        }
    }

    @PostMapping("/run")
    public void runGame(@RequestBody @Valid GameTablePostRunReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Game game = gameService.getGame(UUID.fromString(req.getGameUuid()));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (self.getId() != game.getBelongUserId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (!gameTableService.isGameRunning(game)) {
            try {
                gameTableService.runGame(game);
            } catch (InvalidGameStateException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This game is not ready.");
            }
        }
    }

    @GetMapping("/{gameUuid}/instance")
    public GameTableGetInstanceResp getGameInstanceData(@PathVariable String gameUuid, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (game.getBelongUserId() != self.getId() && !gameService.isPlayerJoinedGame(game, self))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        GameTable gameTable = gameTableService.getGameTable(game);
        if (gameTable == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        GameTable.RunningGame runningGame = gameTable.getRunningGame();
        if (runningGame == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        byte[] accessKey = runningGame.getAccessKey(self);
        GameTableGetInstanceResp ret = new GameTableGetInstanceResp();
        ret.setIpAddress(runningGame.getAddress().getHostAddress());
        ret.setPort(runningGame.getPort());
        ret.setAccessKeyHex(String.valueOf(Hex.encode(accessKey)));
        return ret;
    }
}
