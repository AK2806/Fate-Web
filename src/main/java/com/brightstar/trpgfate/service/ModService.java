package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;

import java.io.InputStream;
import java.util.List;

public interface ModService {
    void publishMod(User user, Mod mod, InputStream thumbnailStream);
    Mod cloneMod(User user, Mod mod);
    void inactiveMod(Mod mod);

    List<Mod> getModsByOwnUser(User owner, int bundle);
    int getModBundleCountByOwnUser(User owner);
}
