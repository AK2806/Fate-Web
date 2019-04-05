package com.brightstar.trpgfate.service.dto.character;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class Character {
    private UUID uuid;
    private int belongUserId;
    private Portrait portrait;
    private String name;
    private String description;
    private int refreshFatePoint;
    private List<Aspect> aspects;
    private Set<Ability> abilities;
    private List<Stunt> stunts;
    private List<Extra> extras;
    private List<Consequence> consequences;
    private int physics;
    private int mental;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getBelongUserId() {
        return belongUserId;
    }

    public void setBelongUserId(int belongUserId) {
        this.belongUserId = belongUserId;
    }

    public Portrait getPortrait() {
        return portrait;
    }

    public void setPortrait(Portrait portrait) {
        this.portrait = portrait;
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

    public int getRefreshFatePoint() {
        return refreshFatePoint;
    }

    public void setRefreshFatePoint(int refreshFatePoint) {
        this.refreshFatePoint = refreshFatePoint;
    }

    public List<Aspect> getAspects() {
        return aspects;
    }

    public void setAspects(List<Aspect> aspects) {
        this.aspects = aspects;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Stunt> getStunts() {
        return stunts;
    }

    public void setStunts(List<Stunt> stunts) {
        this.stunts = stunts;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public List<Consequence> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<Consequence> consequences) {
        this.consequences = consequences;
    }

    public int getPhysics() {
        return physics;
    }

    public void setPhysics(int physics) {
        this.physics = physics;
    }

    public int getMental() {
        return mental;
    }

    public void setMental(int mental) {
        this.mental = mental;
    }
}
