package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.service.ModService;
import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ModServiceImpl implements ModService {

    @Override
    public void publishMod(User user, Mod mod, InputStream thumbnailStream) {

    }

    @Override
    public Mod cloneMod(User user, Mod mod) {
        return null;
    }

    @Override
    public void inactiveMod(Mod mod) {

    }

    @Override
    public List<Mod> getModsByOwnUser(User owner, int bundle) {
        return null;
    }

    @Override
    public int getModBundleCountByOwnUser(User owner) {
        return 0;
    }
}
