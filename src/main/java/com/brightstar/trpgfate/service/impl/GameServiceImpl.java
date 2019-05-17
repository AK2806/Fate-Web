package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.custom_property.GameConfig;
import com.brightstar.trpgfate.dao.GameDAO;
import com.brightstar.trpgfate.dao.GamePlayerDAO;
import com.brightstar.trpgfate.dao.GamingRecordDAO;
import com.brightstar.trpgfate.dao.ModDAO;
import com.brightstar.trpgfate.dao.po.GamePlayer;
import com.brightstar.trpgfate.dao.po.Mod;
import com.brightstar.trpgfate.service.CharacterService;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GamingRecord;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.ModDoesntExistException;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private static final class GameImpl implements Game {
        private UUID uuid;
        private int ownUserId;
        private UUID modUuid;
        private Calendar createTime;
        private int status;
        private String title;

        @Override
        public UUID getUuid() {
            return uuid;
        }

        @Override
        public int getBelongUserId() {
            return ownUserId;
        }

        public void setOwnUserId(int ownUserId) {
            this.ownUserId = ownUserId;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public Calendar getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Calendar createTime) {
            this.createTime = createTime;
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public UUID getModUuid() {
            return modUuid;
        }

        public void setModUuid(UUID modUuid) {
            this.modUuid = modUuid;
        }
    }

    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private GamingRecordDAO gamingRecordDAO;
    @Autowired
    private GamePlayerDAO gamePlayerDAO;
    @Autowired
    private ModDAO modDAO;
    @Autowired
    private GameConfig gameConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private CharacterService characterService;

    @Override
    public Game createGame(User dm, UUID modId, String title) throws ModDoesntExistException {
        if (modId != null) {
            Mod modPo = modDAO.getByGuid(UUIDHelper.toBytes(modId));
            if (modPo == null) throw new ModDoesntExistException();
        }
        com.brightstar.trpgfate.dao.po.Game po = new com.brightstar.trpgfate.dao.po.Game();
        po.setGuid(UUIDHelper.toBytes(UUID.randomUUID()));
        po.setUserId(dm.getId());
        po.setModGuid(modId != null ? UUIDHelper.toBytes(modId) : null);
        po.setCreateTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        po.setStatus(Game.STATUS_CREATED);
        po.setTitle(title);
        gameDAO.insert(po);
        return generateGameData(po);
    }

    @Override
    public Game getGame(UUID gameId) {
        com.brightstar.trpgfate.dao.po.Game po = gameDAO.getByGUID(UUIDHelper.toBytes(gameId));
        if (po == null) return null;
        return generateGameData(po);
    }

    @Override
    public void invalidGame(Game game) {
        com.brightstar.trpgfate.dao.po.Game po = gameDAO.getByGUID(UUIDHelper.toBytes(game.getUuid()));
        if (po.getStatus() == Game.STATUS_CREATED) {
            gameDAO.delete(po);
        } else {
            po.setStatus(Game.STATUS_GAME_OVER);
            gameDAO.updateStatus(po);
        }
        game.setStatus(Game.STATUS_GAME_OVER);
    }

    @Override
    public void updateGameStatus(Game game) throws InvalidGameStateException {
        com.brightstar.trpgfate.dao.po.Game po = gameDAO.getByGUID(UUIDHelper.toBytes(game.getUuid()));
        int newStatus = game.getStatus();
        if (po.getStatus() == newStatus) return;
        if (po.getStatus() == Game.STATUS_GAME_OVER)
            throw new InvalidGameStateException();
        if (po.getStatus() != Game.STATUS_CREATED && newStatus == Game.STATUS_CREATED)
            throw new InvalidGameStateException();
        switch (newStatus) {
            case Game.STATUS_PLAYABLE: {
                List<GamePlayer> players = gamePlayerDAO.getGamePlayersByGameId(po.getGuid());
                if (players.size() <= 0) throw new InvalidGameStateException();
                for (GamePlayer player : players) {
                    if (player.getCharacterGuid() == null) throw new InvalidGameStateException();
                }
            }
            break;
            case Game.STATUS_GAME_OVER: {
                String saveDir = FilenameUtils.concat(gameConfig.getSaveLocation(), game.getUuid().toString());
                File dir = new File(saveDir);
                if (dir.exists()) {
                    if (dir.isDirectory()) {
                        File[] files = dir.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                file.delete();
                            }
                        }
                    }
                    dir.delete();
                }
            }
            break;
        }
        po.setStatus(newStatus);
        gameDAO.updateStatus(po);
    }

    @Override
    public void updateGameInfo(Game game) {
        com.brightstar.trpgfate.dao.po.Game po = gameDAO.getByGUID(UUIDHelper.toBytes(game.getUuid()));
        po.setTitle(game.getTitle());
        gameDAO.updateInfo(po);
    }

    @Override
    public List<GamingRecord> getGamingRecords(Game game) {
        ArrayList<GamingRecord> ret = new ArrayList<>();
        List<com.brightstar.trpgfate.dao.po.GamingRecord> poList = gamingRecordDAO.getGamingRecords(UUIDHelper.toBytes(game.getUuid()));
        for (com.brightstar.trpgfate.dao.po.GamingRecord po : poList) {
            GamingRecord gamingRecord = new GamingRecord();
            gamingRecord.setGameUuid(game.getUuid());
            gamingRecord.setGameTableUuid(UUIDHelper.fromBytes(po.getInstanceGuid()));
            gamingRecord.setBeginTime(DatetimeConverter.sqlTimestamp2Calendar(po.getBeginTime()));
            gamingRecord.setEndTime(DatetimeConverter.sqlTimestamp2Calendar(po.getEndTime()));
            ret.add(gamingRecord);
        }
        return ret;
    }

    @Override
    public void addPlayer(Game game, User player) throws UserConflictException, InvalidGameStateException {
        if (game.getStatus() != Game.STATUS_CREATED) throw new InvalidGameStateException();
        GamePlayer oldPo = gamePlayerDAO.get(UUIDHelper.toBytes(game.getUuid()), player.getId());
        if (oldPo != null) throw new UserConflictException("This user is added into the game");
        GamePlayer newPo = new GamePlayer();
        newPo.setGameGuid(UUIDHelper.toBytes(game.getUuid()));
        newPo.setUserId(player.getId());
        newPo.setCharacterGuid(null);
        gamePlayerDAO.insert(newPo);
    }

    @Override
    public void setPlayerCharacter(Game game, User player, Character character) throws UserDoesntExistException, InvalidGameStateException {
        if (game.getStatus() != Game.STATUS_CREATED) throw new InvalidGameStateException();
        GamePlayer po = gamePlayerDAO.get(UUIDHelper.toBytes(game.getUuid()), player.getId());
        if (po == null) throw new UserDoesntExistException("The user is not added into the game");
        po.setCharacterGuid(character != null ? UUIDHelper.toBytes(character.getUuid()) : null);
        gamePlayerDAO.updateCharacter(po);
    }

    @Override
    public Character getPlayerCharacter(Game game, User player) throws UserDoesntExistException {
        GamePlayer po = gamePlayerDAO.get(UUIDHelper.toBytes(game.getUuid()), player.getId());
        if (po == null) throw new UserDoesntExistException("The user is not added into the game");
        if (po.getCharacterGuid() == null) return null;
        return characterService.getCharacterById(UUIDHelper.fromBytes(po.getCharacterGuid()));
    }

    @Override
    public boolean removePlayer(Game game, User player) throws InvalidGameStateException {
        if (game.getStatus() != Game.STATUS_CREATED) throw new InvalidGameStateException();
        GamePlayer po = gamePlayerDAO.get(UUIDHelper.toBytes(game.getUuid()), player.getId());
        if (po != null) {
            gamePlayerDAO.delete(po);
            return true;
        } else return false;
    }

    @Override
    public List<User> getPlayersOfGame(Game game) {
        List<GamePlayer> pos = gamePlayerDAO.getGamePlayersByGameId(UUIDHelper.toBytes(game.getUuid()));
        ArrayList<User> ret = new ArrayList<>();
        for (GamePlayer po : pos) {
            try {
                ret.add(userService.getUser(po.getUserId()));
            } catch (UserDoesntExistException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public boolean isPlayerJoinedGame(Game game, User player) {
        return gamePlayerDAO.get(UUIDHelper.toBytes(game.getUuid()), player.getId()) != null;
    }

    @Override
    public List<Game> getCreatedGameByUserAsPlayer(User user, int bundle) {
        int bundleSize = gameConfig.getBundleSize();
        List<GamePlayer> pos = gamePlayerDAO.getGamePlayersByPlayerId(user.getId(), bundle * bundleSize, bundleSize);
        ArrayList<Game> ret = new ArrayList<>();
        for (GamePlayer po : pos) {
            com.brightstar.trpgfate.dao.po.Game gamePo = gameDAO.getByGUID(po.getGameGuid());
            ret.add(generateGameData(gamePo));
        }
        return ret;
    }

    @Override
    public int getCreatedGameBundleCountByUserAsPlayer(User user) {
        int count = gamePlayerDAO.getGamePlayersCountByPlayerId(user.getId());
        if (count % gameConfig.getBundleSize() != 0) {
            count /= gameConfig.getBundleSize();
            ++count;
        } else {
            count /= gameConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    @Override
    public List<Game> getCreatedGameByUserAsDM(User user, int bundle) {
        int bundleSize = gameConfig.getBundleSize();
        List<com.brightstar.trpgfate.dao.po.Game> pos = gameDAO.getGamesByUserId(user.getId(), bundle * bundleSize, bundleSize);
        ArrayList<Game> ret = new ArrayList<>();
        for (com.brightstar.trpgfate.dao.po.Game po : pos) {
            ret.add(generateGameData(po));
        }
        return ret;
    }

    @Override
    public int getCreatedGameBundleCountByUserAsDM(User user) {
        int count = gameDAO.getGameCountByUserId(user.getId());
        if (count % gameConfig.getBundleSize() != 0) {
            count /= gameConfig.getBundleSize();
            ++count;
        } else {
            count /= gameConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    private Game generateGameData(com.brightstar.trpgfate.dao.po.Game po) {
        GameImpl game = new GameImpl();
        game.setUuid(UUIDHelper.fromBytes(po.getGuid()));
        game.setOwnUserId(po.getUserId());
        game.setModUuid(po.getModGuid() != null ? UUIDHelper.fromBytes(po.getModGuid()) : null);
        game.setCreateTime(DatetimeConverter.sqlTimestamp2Calendar(po.getCreateTime()));
        game.setStatus(po.getStatus());
        game.setTitle(po.getTitle());
        return game;
    }
}
