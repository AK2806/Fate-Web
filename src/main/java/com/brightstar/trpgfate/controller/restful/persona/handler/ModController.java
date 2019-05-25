package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.ModPatchReq;
import com.brightstar.trpgfate.controller.restful.persona.vo.ModPostReq;
import com.brightstar.trpgfate.service.ModService;
import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.ModCreationException;
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
import java.util.UUID;

@RestController("personaMod")
@RequestMapping("/persona/mod")
public final class ModController {
    @Autowired
    private ModService modService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PostMapping
    public void createMod(@RequestPart("info") @Valid ModPostReq req,
                          @RequestPart("thumbnail") @Valid @NotNull @NotBlank MultipartFile thumbnailFile,
                          HttpServletRequest request) {
        if (thumbnailFile.getSize() > 2 * 1024L * 1024L) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "图片大小不得超过2MB");
        String extension = FilenameUtils.getExtension(thumbnailFile.getOriginalFilename());
        if (!"png".equals(extension)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "只支持PNG格式图片");

        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Mod mod = new Mod();
        mod.setUuid(UUID.randomUUID());
        mod.setOwnUserId(self.getId());
        mod.setAuthorUserId(-1);
        mod.setTitle(req.getTitle());
        mod.setDescription(req.getDescription());
        try (InputStream is = thumbnailFile.getInputStream()){
            modService.createMod(self, mod, is);
        } catch (IOException | ModCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mod creation failed.", e);
        }
    }

    @PatchMapping("/{modUuid}")
    public void editModInfo(@PathVariable String modUuid,
                            @RequestBody @Valid ModPatchReq req,
                            HttpServletRequest request) {
        if (!UUIDHelper.isUUIDString(modUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Mod mod = modService.getMod(UUID.fromString(modUuid));
        if (mod == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (mod.getOwnUserId() != self.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        mod.setTitle(req.getTitle());
        mod.setDescription(req.getDescription());
        try {
            modService.updateModInfo(mod);
        } catch (ModCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mod update failed.", e);
        }
    }

    @PatchMapping("/{modUuid}/thumbnail")
    public void editModPortrait(@PathVariable String modUuid,
                                @RequestPart("thumbnail") @Valid @NotNull @NotBlank MultipartFile thumbnailFile,
                                HttpServletRequest request) {
        if (!UUIDHelper.isUUIDString(modUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Mod mod = modService.getMod(UUID.fromString(modUuid));
        if (mod == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (mod.getOwnUserId() != self.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        try (InputStream is = thumbnailFile.getInputStream()){
            modService.updateModThumbnail(mod, is);
        } catch (IOException | ModCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mod update failed.", e);
        }
    }

    @DeleteMapping("/{modUuid}")
    public void removeMod(@PathVariable String modUuid, HttpServletRequest request) {
        if (!UUIDHelper.isUUIDString(modUuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Mod mod = modService.getMod(UUID.fromString(modUuid));
        if (mod == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User self;
        try {
            self = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (mod.getOwnUserId() != self.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        modService.deleteMod(mod);
    }
}
