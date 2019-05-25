package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.restful.userdata.vo.ModGetResp;
import com.brightstar.trpgfate.service.ModService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;
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

@RestController("userdataMod")
@RequestMapping("/userdata/mod")
public final class ModController {
    @Autowired
    private ModService modService;
    @Autowired
    private UserService userService;

    @GetMapping("/{uuid}")
    public ModGetResp getModById(@PathVariable String uuid) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Mod mod = modService.getMod(UUID.fromString(uuid));
        if (mod == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return generateResp(mod);
    }

    @GetMapping("/{userId}/list")
    public int fetchListItemCountByUser(@PathVariable int userId) {
        User owner;
        try {
            owner = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return modService.getModBundleCountByOwnUser(owner);
    }

    @GetMapping("/{userId}/list/{idx}")
    public List<ModGetResp> fetchListByUser(@PathVariable int userId, @PathVariable int idx) {
        User owner;
        try {
            owner = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<Mod> mods = modService.getModsByOwnUser(owner, idx);
        ArrayList<ModGetResp> ret = new ArrayList<>();
        for (Mod mod : mods) {
            ret.add(generateResp(mod));
        }
        return ret;
    }

    private ModGetResp generateResp(Mod mod) {
        ModGetResp ret = new ModGetResp();
        ret.setUuid(mod.getUuid().toString());
        ret.setTitle(mod.getTitle());
        ret.setDescription(mod.getDescription());
        ret.setOwnerId(mod.getOwnUserId());
        ret.setAuthorId(mod.getAuthorUserId());
        ret.setCreateTime(DatetimeConverter.calendar2UtilDate(mod.getCreateTime()));
        ret.setLastPublishTime(mod.getLastPublishTime() != null ?
                DatetimeConverter.calendar2UtilDate(mod.getLastPublishTime()) : null);
        return ret;
    }
}
