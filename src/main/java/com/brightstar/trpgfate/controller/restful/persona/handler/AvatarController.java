package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.config.file.AvatarConfig;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.AvatarPostResp;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Account;
import com.brightstar.trpgfate.service.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/persona/{id}/avatar")
public final class AvatarController {
    @Autowired
    private AvatarConfig avatarConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PostMapping
    public AvatarPostResp uploadImage(@PathVariable int id, @RequestPart @Valid @NotNull MultipartFile imageFile) {
        if (imageFile.getSize() > 1024L * 1024L) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "图片大小不得超过1MB");
        String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        if (!"png".equals(extension)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");

        User user = userFetcher.fetch();
        Account account = userService.getAccountInfo(user);

        UUID uuid;
        File file;
        try {
            do {
                uuid = UUID.randomUUID();
                String fullPath = FilenameUtils.concat(avatarConfig.getBaseDirectory(), uuid.toString() + ".png");
                file = new File(fullPath);
            } while (!file.createNewFile());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating file", e);
        }
        try (InputStream is = imageFile.getInputStream();
             OutputStream os = new FileOutputStream(file)) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating file", e);
        }

        String oldUUID = account.getAvatar().toString();
        if (!oldUUID.equals(avatarConfig.getDefaultUUID())) {
            String oldFullPath = FilenameUtils.concat(avatarConfig.getBaseDirectory(), oldUUID + ".png");
            File oldFile = new File(oldFullPath);
            oldFile.delete();
        }

        account.setAvatar(uuid);
        userService.updateAccountInfo(user, account);
        AvatarPostResp ret = new AvatarPostResp();
        ret.setUuid(uuid.toString());
        return ret;
    }
}
