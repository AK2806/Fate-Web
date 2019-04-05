package com.brightstar.trpgfate.application.character.stunt;

public final class StuntInfo {
    private String id;
    private String name;
    private String description;

    public StuntInfo() {
    }

    public StuntInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StuntInfo && id.equals(((StuntInfo) obj).id);
    }
}
