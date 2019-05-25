package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.ModCreationException;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface ModService {
    void createMod(User user, Mod mod, InputStream thumbnailStream) throws ModCreationException;
    Mod getMod(UUID uuid);
    void updateModInfo(Mod mod) throws ModCreationException;
    void updateModThumbnail(Mod mod, InputStream thumbnailStream) throws ModCreationException;
    void deleteMod(Mod mod);

    List<Mod> getModsByOwnUser(User owner, int bundle);
    int getModBundleCountByOwnUser(User owner);

    void publishMod(Mod mod, InputStream dataStream);
}
