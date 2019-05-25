package com.brightstar.trpgfate.controller.ordinary.general.handler;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.custom_property.AvatarConfig;
import com.brightstar.trpgfate.config.custom_property.CharacterConfig;
import com.brightstar.trpgfate.config.custom_property.ModConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/img")
public final class ImageController {
    @Autowired
    private AvatarConfig avatarConfig;
    @Autowired
    private CharacterConfig characterConfig;
    @Autowired
    private ModConfig modConfig;

    @GetMapping
    @RequestMapping("/avatar/{uuid}")
    public void downloadAvatar(@PathVariable String uuid, HttpServletResponse response) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not exists");
        downloadImage(avatarConfig.getBaseDirectory(), uuid, response);
    }

    @GetMapping
    @RequestMapping("/character/{uuid}")
    public void downloadPortrait(@PathVariable String uuid, HttpServletResponse response) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not exists");
        downloadImage(characterConfig.getBaseDirectory(), uuid, response);
    }

    @GetMapping
    @RequestMapping("/mod/{uuid}/thumbnail")
    public void downloadModThumbnail(@PathVariable String uuid, HttpServletResponse response) {
        if (!UUIDHelper.isUUIDString(uuid)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not exists");
        String directory = FilenameUtils.concat(modConfig.getBaseDirectory(), uuid);
        downloadImage(directory, "thumbnail", response);
    }

    private void downloadImage(String baseDir, String name, HttpServletResponse response) {
        String fullPath = FilenameUtils.concat(baseDir,   name + ".png");
        File image = new File(fullPath);
        if (!image.exists()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not exists");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        try (InputStream is = new FileInputStream(image);
             OutputStream os = response.getOutputStream()) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fetching image is failure");
        }
    }
}
