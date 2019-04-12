package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.application.character.ability.AbilityInfo;
import com.brightstar.trpgfate.application.character.ability.AbilityPool;
import com.brightstar.trpgfate.application.character.stunt.PresetStuntPool;
import com.brightstar.trpgfate.application.character.stunt.StuntInfo;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.CharacterData;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.PortraitData;
import com.brightstar.trpgfate.controller.restful.persona.vo.CharacterPatchReq;
import com.brightstar.trpgfate.controller.restful.persona.vo.CharacterPortraitPatchReq;
import com.brightstar.trpgfate.controller.restful.persona.vo.CharacterPostReq;
import com.brightstar.trpgfate.service.CharacterService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.*;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.CharacterCreationException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@RestController("personaCharacterController")
@RequestMapping("/persona/character")
public final class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PostMapping
    public void createCharacter(@RequestPart("data") @Valid CharacterPostReq req,
                                @RequestPart("img") @Valid @NotNull @NotBlank MultipartFile imageFile,
                                HttpServletRequest request) {
        if (imageFile.getSize() > 2 * 1024L * 1024L) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "图片大小不得超过2MB");
        String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        if (!"png".equals(extension)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "只支持PNG格式图片");

        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Character character = new Character();
        character.setUuid(UUID.randomUUID());
        character.setBelongUserId(user.getId());
        applyPortraitData(character, req.getPortrait());
        applyCharacterData(character, req.getData());
        try (InputStream is = imageFile.getInputStream()) {
            characterService.createCharacter(character, user, is);
        } catch (IOException | CharacterCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Character creation failed", e);
        }
    }

    @PatchMapping
    public void editCharacter(@RequestBody @Valid CharacterPatchReq req, HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Character character = characterService.getCharacterById(UUID.fromString(req.getUuid()));
        if (character.getBelongUserId() != user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        applyCharacterData(character, req.getData());
        try {
            characterService.updateCharacterOnlyData(character);
        } catch (CharacterCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Character modification failed", e);
        }
    }

    @PatchMapping
    @RequestMapping("/portrait")
    public void editCharacterPortrait(@RequestPart("data") @Valid CharacterPortraitPatchReq req,
                                      @RequestPart("img") @Valid @NotNull @NotBlank MultipartFile imgFile,
                                      HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Character character = characterService.getCharacterById(UUID.fromString(req.getUuid()));
        if (character.getBelongUserId() != user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        applyPortraitData(character, req.getPortrait());
        try (InputStream is = imgFile.getInputStream()) {
            characterService.updateCharacter(character, is);
        } catch (IOException | CharacterCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Character modification failed", e);
        }
    }

    @DeleteMapping
    @RequestMapping("/{uuid}")
    public void deleteCharacter(@PathVariable String uuid, HttpServletRequest request) {
        if (!UUIDHelper.isUUID(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Character character = characterService.getCharacterById(UUID.fromString(uuid));
        if (character.getBelongUserId() != user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        characterService.removeCharacter(character);
    }
    
    private void applyPortraitData(Character dto, PortraitData vo) {
        Portrait portrait = new Portrait();
        portrait.setStature(vo.getStature());
        portrait.setHeadLeft(vo.getHeadLeft());
        portrait.setHeadTop(vo.getHeadTop());
        portrait.setHeadRight(vo.getHeadRight());
        portrait.setHeadBottom(vo.getHeadBottom());
        dto.setPortrait(portrait);
    }
    
    private void applyCharacterData(Character dto, CharacterData vo) {
        dto.setName(vo.getName());
        dto.setDescription(vo.getDescription());
        dto.setRefreshFatePoint(vo.getRefreshFatePoint());
        ArrayList<Aspect> aspects = new ArrayList<>();
        for (CharacterData.AspectData aspectData : vo.getAspects()) {
            Aspect aspect = new Aspect();
            aspect.setName(aspectData.getName());
            aspects.add(aspect);
        }
        dto.setAspects(aspects);
        HashSet<Ability> abilities = new HashSet<>();
        for (CharacterData.AbilityData abilityData : vo.getAbilities()) {
            if (!AbilityPool.getAbilities().contains(new AbilityInfo(abilityData.getId()))) continue;
            Ability ability = new Ability();
            ability.setId(abilityData.getId());
            ability.setLevel(abilityData.getLevel());
            abilities.add(ability);
        }
        dto.setAbilities(abilities);
        ArrayList<Stunt> stunts = new ArrayList<>();
        for (CharacterData.StuntData stuntData : vo.getStunts()) {
            Stunt stunt = new Stunt();
            stunt.setType(stuntData.getType());
            switch (stunt.getType()) {
                case Stunt.TYPE_PRESET:
                    if (!PresetStuntPool.getStunts().contains(new StuntInfo(stuntData.getPresetId()))) continue;
                    stunt.setPresetId(stuntData.getPresetId());
                    break;
                case Stunt.TYPE_CUSTOM:
                    if (!UUIDHelper.isUUID(stuntData.getUuid())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    stunt.setUuid(UUID.fromString(stuntData.getUuid()));
                    break;
            }
            stunts.add(stunt);
        }
        dto.setStunts(stunts);
        ArrayList<Extra> extras = new ArrayList<>();
        for (CharacterData.ExtraData extraData : vo.getExtras()) {
            Extra extra = new Extra();
            if (!UUIDHelper.isUUID(extraData.getUuid())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            extra.setUuid(UUID.fromString(extraData.getUuid()));
            extras.add(extra);
        }
        dto.setExtras(extras);
        ArrayList<Consequence> consequences = new ArrayList<>();
        for (CharacterData.ConsequenceData consequenceData : vo.getConsequences()) {
            Consequence consequence = new Consequence();
            consequence.setCapacity(consequenceData.getCapacity());
            consequences.add(consequence);
        }
        dto.setConsequences(consequences);
        dto.setPhysics(vo.getPhysics());
        dto.setMental(vo.getMental());
    }
}
