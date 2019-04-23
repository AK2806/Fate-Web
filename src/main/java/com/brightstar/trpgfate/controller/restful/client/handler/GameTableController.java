package com.brightstar.trpgfate.controller.restful.client.handler;

import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.client.vo.GameTablePostReq;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController("clientGameTable")
@RequestMapping("/client/game-table")
public class GameTableController {
    @Autowired
    private GameService gameService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PostMapping
    public void startGame(@RequestBody @Valid GameTablePostReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Game game = gameService.getGame(UUID.fromString(req.getGameUuid()));
        if (game.getStatus() == Game.STATUS_GAME_OVER)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This game is already over");
        game.setStatus(Game.STATUS_PLAYING);
        try {
            gameService.openGameTable(game);
        } catch (InvalidGameStateException e) {
            e.printStackTrace();
        }
    }

}
