package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.custom_property.ModConfig;
import com.brightstar.trpgfate.dao.ModDAO;
import com.brightstar.trpgfate.service.ModService;
import com.brightstar.trpgfate.service.dto.Mod;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.ModCreationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ModServiceImpl implements ModService {
    @Autowired
    private ModDAO modDAO;
    @Autowired
    private ModConfig modConfig;

    @Override
    public void createMod(User user, Mod mod, InputStream thumbnailStream) throws ModCreationException {
        mod.setUuid(UUID.randomUUID());
        mod.setOwnUserId(user.getId());
        mod.setAuthorUserId(user.getId());
        if (!createThumbnail(mod, thumbnailStream)) throw new ModCreationException();
        com.brightstar.trpgfate.dao.po.Mod po = new com.brightstar.trpgfate.dao.po.Mod();
        po.setGuid(UUIDHelper.toBytes(mod.getUuid()));
        po.setUserId(mod.getOwnUserId());
        po.setAuthorId(mod.getAuthorUserId());
        po.setCreateTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        po.setLastPublishTime(null);
        po.setTitle(mod.getTitle());
        po.setDescription(mod.getDescription());
        modDAO.insert(po);
    }

    @Override
    public Mod getMod(UUID uuid) {
        com.brightstar.trpgfate.dao.po.Mod po = modDAO.getByGuid(UUIDHelper.toBytes(uuid));
        if (po == null) return null;
        return generateModDTO(po);
    }

    @Override
    public void updateModInfo(Mod mod) {
        com.brightstar.trpgfate.dao.po.Mod po = modDAO.getByGuid(UUIDHelper.toBytes(mod.getUuid()));
        if (po == null) return;
        po.setTitle(mod.getTitle());
        po.setDescription(mod.getDescription());
        modDAO.updateInfo(po);
    }

    @Override
    public void updateModThumbnail(Mod mod, InputStream thumbnailStream) throws ModCreationException {
        if (!createThumbnail(mod, thumbnailStream)) throw new ModCreationException();
    }

    @Override
    public void deleteMod(Mod mod) {
        com.brightstar.trpgfate.dao.po.Mod po = modDAO.getByGuid(UUIDHelper.toBytes(mod.getUuid()));
        if (po == null) return;
        modDAO.delete(po);
    }

    @Override
    public List<Mod> getModsByOwnUser(User owner, int bundle) {
        int bundleSize = modConfig.getBundleSize();
        List<com.brightstar.trpgfate.dao.po.Mod> poList = modDAO.findModsByUserId(owner.getId(), bundle * bundleSize, bundleSize);
        ArrayList<Mod> ret = new ArrayList<>();
        for (com.brightstar.trpgfate.dao.po.Mod po : poList) {
            ret.add(generateModDTO(po));
        }
        return ret;
    }

    @Override
    public int getModBundleCountByOwnUser(User owner) {
        int count = modDAO.getModCountByUserId(owner.getId());
        if (count % modConfig.getBundleSize() != 0) {
            count /= modConfig.getBundleSize();
            ++count;
        } else {
            count /= modConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    @Override
    public void publishMod(Mod mod, InputStream dataStream) {

    }

    private boolean createThumbnail(Mod mod, InputStream imgStream) {
        String baseDir = modConfig.getBaseDirectory();
        String directory = FilenameUtils.concat(baseDir, mod.getUuid().toString());
        String fullPath = FilenameUtils.concat(directory, "thumbnail.png");
        File folder = new File(directory);
        if (folder.exists() && folder.isFile()) folder.delete();
        if (!folder.exists()) folder.mkdir();
        File file = new File(fullPath);
        try {
            if (file.exists()) file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try (OutputStream os = new FileOutputStream(file)) {
            IOUtils.copy(imgStream, os);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Mod generateModDTO(com.brightstar.trpgfate.dao.po.Mod po) {
        Mod mod = new Mod();
        mod.setUuid(UUIDHelper.fromBytes(po.getGuid()));
        mod.setOwnUserId(po.getUserId());
        mod.setAuthorUserId(po.getAuthorId() != null ? po.getAuthorId() : -1);
        mod.setCreateTime(DatetimeConverter.sqlTimestamp2Calendar(po.getCreateTime()));
        mod.setLastPublishTime(po.getLastPublishTime() != null ?
                DatetimeConverter.sqlTimestamp2Calendar(po.getLastPublishTime()) : null);
        mod.setTitle(po.getTitle());
        mod.setDescription(po.getDescription());
        return mod;
    }
}
