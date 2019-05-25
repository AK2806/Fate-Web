package com.brightstar.trpgfate.config.custom_property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.mod")
public class ModConfig {
    private String baseDirectory;
    private int bundleSize;

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public int getBundleSize() {
        return bundleSize;
    }

    public void setBundleSize(int bundleSize) {
        this.bundleSize = bundleSize;
    }
}
