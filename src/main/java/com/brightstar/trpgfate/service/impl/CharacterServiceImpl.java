package com.brightstar.trpgfate.service.impl;

import com.alibaba.fastjson.JSON;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.file.CharacterConfigInfo;
import com.brightstar.trpgfate.dao.CharacterDAO;
import com.brightstar.trpgfate.service.CharacterService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.CharacterCreationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    private CharacterDAO characterDAO;
    @Autowired
    private CharacterConfigInfo characterConfigInfo;

    @Override
    public void createCharacter(Character character, User user, InputStream portraitStream) throws CharacterCreationException {
        character.setUuid(UUID.randomUUID());
        character.setBelongUserId(user.getId());
        if (!createPortrait(character, portraitStream, false)) throw new CharacterCreationException();
        if (!writeData(character, false)) throw new CharacterCreationException();
        com.brightstar.trpgfate.dao.po.Character po = new com.brightstar.trpgfate.dao.po.Character();
        po.setGuid(UUIDHelper.toBytes(character.getUuid()));
        po.setUserId(user.getId());
        characterDAO.insert(po);
    }

    @Override
    public void removeCharacter(Character character) {
        com.brightstar.trpgfate.dao.po.Character po = new com.brightstar.trpgfate.dao.po.Character();
        po.setGuid(UUIDHelper.toBytes(character.getUuid()));
        po.setUserId(character.getBelongUserId());
        characterDAO.remove(po);
        String baseDir = characterConfigInfo.getBaseDirectory();
        String fullPath = FilenameUtils.concat(baseDir, character.getUuid() + ".png");
        File file = new File(fullPath);
        file.delete();
        fullPath = FilenameUtils.concat(baseDir, character.getUuid() + ".json");
        file = new File(fullPath);
        file.delete();
    }

    @Override
    public void updateCharacter(Character character, InputStream portraitStream) throws CharacterCreationException {
        com.brightstar.trpgfate.dao.po.Character po = characterDAO.getByGuid(UUIDHelper.toBytes(character.getUuid()));
        if (po == null) throw new CharacterCreationException();
        if (!createPortrait(character, portraitStream, true)) throw new CharacterCreationException();
        if (!writeData(character, true)) throw new CharacterCreationException();
    }

    @Override
    public void updateCharacterOnlyData(Character character) throws CharacterCreationException {
        com.brightstar.trpgfate.dao.po.Character po = characterDAO.getByGuid(UUIDHelper.toBytes(character.getUuid()));
        if (po == null) throw new CharacterCreationException();
        Character oldOne = getCharacterById(character.getUuid());
        character.setPortrait(oldOne.getPortrait());
        if (!writeData(character, true)) throw new CharacterCreationException();
    }

    @Override
    public Character getCharacterById(UUID characterId) {
        com.brightstar.trpgfate.dao.po.Character po = characterDAO.getByGuid(UUIDHelper.toBytes(characterId));
        if (po == null) return null;
        Character ret = readData(characterId);
        ret.setBelongUserId(po.getUserId());
        return ret;
    }

    @Override
    public List<Character> getCharactersOfUser(User user, int bundle) {
        List<com.brightstar.trpgfate.dao.po.Character> pos = characterDAO.findByUserId(user.getId(), bundle * 10, 10);
        ArrayList<Character> ret = new ArrayList<>();
        for (com.brightstar.trpgfate.dao.po.Character po : pos) {
            Character character = readData(UUIDHelper.fromBytes(po.getGuid()));
            character.setBelongUserId(po.getUserId());
            ret.add(character);
        }
        return ret;
    }

    private File createFile(String path, boolean overwrite) {
        File file = new File(path);
        try {
            if (!overwrite) {
                if (!file.createNewFile()) return null;
            } else {
                if (file.exists()) file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private boolean createPortrait(Character character, InputStream imgStream, boolean overwrite) {
        String baseDir = characterConfigInfo.getBaseDirectory();
        String fullPath = FilenameUtils.concat(baseDir, character.getUuid() + ".png");
        File file = createFile(fullPath, overwrite);
        if (file == null) return false;
        try (OutputStream os = new FileOutputStream(file)) {
            IOUtils.copy(imgStream, os);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean writeData(Character character, boolean overwrite) {
        String baseDir = characterConfigInfo.getBaseDirectory();
        String fullPath = FilenameUtils.concat(baseDir, character.getUuid() + ".json");
        File file = createFile(fullPath, overwrite);
        if (file == null) return false;
        try (FileWriter jsonWriter = new FileWriter(file)) {
            String json = JSON.toJSONString(character);
            jsonWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Character readData(UUID characterId) {
        String baseDir = characterConfigInfo.getBaseDirectory();String fullPath = FilenameUtils.concat(baseDir, characterId + ".json");
        File file = new File(fullPath);
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader jsonReader = new FileReader(file)) {
            char[] charSeq = new char[2048];
            int offset = 0;
            while (jsonReader.read(charSeq, offset, 2048) == 2048) {
                stringBuilder.append(charSeq);
                offset += 2048;
            }
            stringBuilder.append(charSeq);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(stringBuilder.toString(), Character.class);
    }
}
