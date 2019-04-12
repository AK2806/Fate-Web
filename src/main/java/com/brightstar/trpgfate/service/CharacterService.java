package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.CharacterCreationException;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface CharacterService {
    void createCharacter(Character character, User user, InputStream portraitStream)  throws CharacterCreationException;
    void removeCharacter(Character character);
    void updateCharacter(Character character, InputStream portraitStream) throws CharacterCreationException;
    void updateCharacterOnlyData(Character character) throws CharacterCreationException;
    Character getCharacterById(UUID characterId);
    List<Character> getCharactersOfUser(User user, int bundle);
    int getCharacterBundleCountOfUser(User user);
}
