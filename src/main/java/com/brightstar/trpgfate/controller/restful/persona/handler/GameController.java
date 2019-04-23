package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.*;
import com.brightstar.trpgfate.service.CharacterService;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.ModDoesntExistException;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController("personaGame")
@RequestMapping("/persona/game")
public final class GameController {
    @Autowired
    private UserService userService;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private GameService gameService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PostMapping
    public GamePostResp createGame(@RequestBody @Valid GamePostReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Game game;
        try {
            if (req.getModGuid().equals("")) {
                game = gameService.createGame(self, null, req.getTitle());
            } else {
                game = gameService.createGame(self, UUID.fromString(req.getModGuid()), req.getTitle());
            }
        } catch (ModDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        GamePostResp ret = new GamePostResp();
        ret.setUuid(game.getUuid().toString());
        return ret;
    }

    @DeleteMapping("/{gameUuid}")
    public void removeGame(@PathVariable String gameUuid, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        gameService.invalidGame(game);
    }

    @PostMapping("/{gameUuid}/player")
    public void addPlayer(@PathVariable String gameUuid, @RequestBody @Valid GamePostPlayerReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (game.getBelongUserId() != self.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        User player;
        try {
            player = userService.getUser(req.getUserId());
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            gameService.addPlayer(game, player);
        } catch (UserConflictException | InvalidGameStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{gameUuid}/player/{userId}")
    public void removePlayer(@PathVariable String gameUuid, @PathVariable int userId, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (userId < 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (game.getBelongUserId() != self.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        User player;
        try {
            player = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            gameService.removePlayer(game, player);
        } catch (InvalidGameStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{gameUuid}/as-player")
    public void playerChangeCharacter(@PathVariable String gameUuid, @RequestBody @Valid GamePutAsPlayerReq req, HttpServletRequest request) {
        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!UUIDHelper.isUUIDString(gameUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Game game = gameService.getGame(UUID.fromString(gameUuid));
        if (game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!gameService.isPlayerJoinedGame(game, self)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Character character = characterService.getCharacterById(UUID.fromString(req.getCharacterId()));
        if (character == null || character.getBelongUserId() != self.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            gameService.setPlayerCharacter(game, self, character);
        } catch (UserDoesntExistException | InvalidGameStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
