package com.brightstar.trpgfate.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.character")
public class CharacterConfigInfo {
    private String baseDirectory;

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
}
