package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.restful.userdata.vo.*;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController("userdataGame")
@RequestMapping("/userdata/game")
public final class GameController {
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;

    @GetMapping("/{uuid}")
    public GameGetResp getGameData(@PathVariable String uuid) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(uuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return generateGameResp(game, gameService.getPlayersOfGame(game));
    }

    @GetMapping("/dm/{userId}")
    public GameGetPageCountResp getPageCountAsDM(@PathVariable int userId) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        int count = gameService.getCreatedGameBundleCountByUserAsDM(user);
        GameGetPageCountResp ret = new GameGetPageCountResp();
        ret.setCount(count);
        return ret;
    }

    @GetMapping("/dm/{userId}/{page}")
    public List<GameGetResp> getPageAsDM(@PathVariable int userId, @PathVariable int page) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Game> games = gameService.getCreatedGameByUserAsDM(user, page);
        ArrayList<GameGetResp> ret = new ArrayList<>();
        for (Game game : games) {
            ret.add(generateGameResp(game, gameService.getPlayersOfGame(game)));
        }
        return ret;
    }

    @GetMapping("/player/{userId}")
    public GameGetPageCountResp getPageCountAsPlayer(@PathVariable int userId) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        int count = gameService.getCreatedGameBundleCountByUserAsPlayer(user);
        GameGetPageCountResp ret = new GameGetPageCountResp();
        ret.setCount(count);
        return ret;
    }

    @GetMapping("/player/{userId}/{page}")
    public List<GameGetResp> getPageAsPlayer(@PathVariable int userId, @PathVariable int page) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Game> games = gameService.getCreatedGameByUserAsPlayer(user, page);
        ArrayList<GameGetResp> ret = new ArrayList<>();
        for (Game game : games) {
            ret.add(generateGameResp(game, gameService.getPlayersOfGame(game)));
        }
        return ret;
    }

    private GameGetResp generateGameResp(Game game, List<User> gamePlayers) {
        GameGetResp ret = new GameGetResp();
        ret.setUuid(game.getUuid().toString());
        ret.setBelongUserId(game.getBelongUserId());
        UUID modUuid = game.getModUuid();
        ret.setModUuid(modUuid != null ? modUuid.toString() : null);
        ret.setCreateTime(DatetimeConverter.calendar2UtilDate(game.getCreateTime()));
        ret.setStatus(game.getStatus());
        ret.setTitle(game.getTitle());
        ArrayList<GameGetResp.Player> playerResps = new ArrayList<>();
        for (User player : gamePlayers) {
            try {
                Character character = gameService.getPlayerCharacter(game, player);
                GameGetResp.Player playerResp = new GameGetResp.Player();
                playerResp.setUserId(player.getId());
                if (character == null) {
                    playerResp.setCharacterUuid(null);
                } else {
                    playerResp.setCharacterUuid(character.getUuid().toString());
                }
                playerResps.add(playerResp);
            } catch (UserDoesntExistException e) {
                e.printStackTrace();
            }
        }
        ret.setPlayers(playerResps);
        return ret;
    }
}
