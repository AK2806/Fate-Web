package com.brightstar.trpgfate.controller.ordinary.handler;

import com.brightstar.trpgfate.config.file.AvatarConfig;
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
import java.util.regex.Pattern;

@Controller
@RequestMapping("/img")
public final class ImageController {
    @Autowired
    private AvatarConfig avatarConfig;

    @GetMapping
    @RequestMapping("/avatar/{uuid}")
    public void downloadImage(@PathVariable String uuid, HttpServletResponse response) {
        Pattern pattern = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        if (!pattern.matcher(uuid).matches()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image not exists");
        String fullPath = FilenameUtils.concat(avatarConfig.getBaseDirectory(),   uuid + ".png");
        File image = new File(fullPath);
        if (!image.exists()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image not exists");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        try (InputStream is = new FileInputStream(image);
             OutputStream os = response.getOutputStream()) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fetching image is failure");
        }
    }
}
