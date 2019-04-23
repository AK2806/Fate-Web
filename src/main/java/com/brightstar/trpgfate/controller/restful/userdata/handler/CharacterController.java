package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.CharacterData;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.PortraitData;
import com.brightstar.trpgfate.controller.restful.userdata.vo.CharacterGetByIdResp;
import com.brightstar.trpgfate.controller.restful.userdata.vo.CharacterGetListByUserResp;
import com.brightstar.trpgfate.controller.restful.userdata.vo.CharacterGetListGroupCountByUserResp;
import com.brightstar.trpgfate.service.CharacterService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.dto.character.*;
import com.brightstar.trpgfate.service.dto.character.Character;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController("userdataCharacterController")
@RequestMapping("/userdata/character")
public final class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private UserService userService;

    @GetMapping
    @RequestMapping("/concrete/{uuid}")
    public CharacterGetByIdResp fetchById(@PathVariable String uuid) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Character character = characterService.getCharacterById(UUID.fromString(uuid));
        if (character == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User target;
        try {
            target = userService.getUser(character.getBelongUserId());
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        CharacterGetByIdResp ret = new CharacterGetByIdResp();
        ret.setUuid(uuid);
        ret.setBelongUserId(target.getId());
        PortraitData portrait = new PortraitData();
        portrait.setStature(character.getPortrait().getStature());
        portrait.setHeadLeft(character.getPortrait().getHeadLeft());
        portrait.setHeadTop(character.getPortrait().getHeadTop());
        portrait.setHeadRight(character.getPortrait().getHeadRight());
        portrait.setHeadBottom(character.getPortrait().getHeadBottom());
        ret.setPortrait(portrait);
        CharacterData data = new CharacterData();
        data.setName(character.getName());
        data.setDescription(character.getDescription());
        data.setRefreshFatePoint(character.getRefreshFatePoint());
        LinkedList<CharacterData.AspectData> aspectData = new LinkedList<>();
        for (Aspect aspect : character.getAspects()) {
            CharacterData.AspectData val = new CharacterData.AspectData();
            val.setName(aspect.getName());
            aspectData.addLast(val);
        }
        data.setAspects(aspectData);
        LinkedList<CharacterData.AbilityData> abilityData = new LinkedList<>();
        for (Ability ability : character.getAbilities()) {
            CharacterData.AbilityData val = new CharacterData.AbilityData();
            val.setId(ability.getId());
            val.setLevel(ability.getLevel());
            abilityData.addLast(val);
        }
        data.setAbilities(abilityData);
        LinkedList<CharacterData.StuntData> stuntData = new LinkedList<>();
        for (Stunt stunt : character.getStunts()) {
            CharacterData.StuntData val = new CharacterData.StuntData();
            val.setType(val.getType());
            switch (val.getType()) {
                case Stunt.TYPE_PRESET:
                    val.setPresetId(stunt.getPresetId());
                    break;
                case Stunt.TYPE_CUSTOM:
                    val.setUuid(stunt.getUuid().toString());
                    break;
            }
            stuntData.addLast(val);
        }
        data.setStunts(stuntData);
        LinkedList<CharacterData.ExtraData> extraData = new LinkedList<>();
        for (Extra extra : character.getExtras()) {
            CharacterData.ExtraData val = new CharacterData.ExtraData();
            val.setUuid(extra.getUuid().toString());
            extraData.addLast(val);
        }
        data.setExtras(extraData);
        LinkedList<CharacterData.ConsequenceData> consequenceData = new LinkedList<>();
        for (Consequence consequence : character.getConsequences()) {
            CharacterData.ConsequenceData val = new CharacterData.ConsequenceData();
            val.setCapacity(consequence.getCapacity());
            consequenceData.addLast(val);
        }
        data.setConsequences(consequenceData);
        data.setPhysics(character.getPhysics());
        data.setMental(character.getMental());
        ret.setData(data);
        return ret;
    }

    @GetMapping
    @RequestMapping("/list/{id}")
    public CharacterGetListGroupCountByUserResp fetchListGroupCountByUser(@PathVariable int id) {
        User target;
        try {
            target = userService.getUser(id);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        int count = characterService.getCharacterBundleCountOfUser(target);
        CharacterGetListGroupCountByUserResp ret = new CharacterGetListGroupCountByUserResp();
        ret.setCount(count);
        return ret;
    }

    @GetMapping
    @RequestMapping("/list/{id}/{page}")
    public CharacterGetListByUserResp fetchListByUser(@PathVariable int id, @PathVariable int page) {
        User target;
        try {
            target = userService.getUser(id);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<Character> characters = characterService.getCharactersOfUser(target, page);
        CharacterGetListByUserResp ret = new CharacterGetListByUserResp();
        LinkedList<CharacterGetListByUserResp.CharacterListItem> retListItem = new LinkedList<>();
        ret.setCharacters(retListItem);
        for (Character character : characters) {
            CharacterGetListByUserResp.CharacterListItem item = new CharacterGetListByUserResp.CharacterListItem();
            item.setUuid(character.getUuid().toString());
            item.setName(character.getName());
            retListItem.addLast(item);
        }
        return ret;
    }
}
