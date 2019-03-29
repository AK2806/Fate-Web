package com.brightstar.trpgfate.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.avatar")
public class AvatarConfig {
    private String baseDirectory;
    private String defaultUUID;

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getDefaultUUID() {
        return defaultUUID;
    }

    public void setDefaultUUID(String defaultUUID) {
        this.defaultUUID = defaultUUID;
    }
}
