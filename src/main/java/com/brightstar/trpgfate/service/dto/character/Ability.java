package com.brightstar.trpgfate.service.dto.character;

public final class Ability {
    private String id;
    private int level;

    public Ability() {
    }

    public Ability(String id, int level) {
        this.id = id;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Ability && id.equals(((Ability) obj).id);
    }
}
