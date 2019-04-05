package com.brightstar.trpgfate.application.character.ability;

public final class AbilityInfo {
    private String id;
    private String name;
    private String description;
    private String onHinder;
    private String onAttack;
    private String onCreateAspect;

    public AbilityInfo() {
    }

    public AbilityInfo(String id) {
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

    public String getOnHinder() {
        return onHinder;
    }

    public void setOnHinder(String onHinder) {
        this.onHinder = onHinder;
    }

    public String getOnAttack() {
        return onAttack;
    }

    public void setOnAttack(String onAttack) {
        this.onAttack = onAttack;
    }

    public String getOnCreateAspect() {
        return onCreateAspect;
    }

    public void setOnCreateAspect(String onCreateAspect) {
        this.onCreateAspect = onCreateAspect;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbilityInfo && id.equals(((AbilityInfo) obj).id);
    }
}
