package com.brightstar.trpgfate.controller.restful.nested_vo.character;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public final class CharacterData {
    public final static class AbilityData {
        @NotBlank
        private String id;
        private int level;

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
    }

    public final static class AspectData {
        @NotBlank
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public final static class StuntData {
        @Min(0) @Max(1)
        private int type;
        private String presetId;
        private String uuid;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPresetId() {
            return presetId;
        }

        public void setPresetId(String presetId) {
            this.presetId = presetId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public final static class ExtraData {
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public final static class ConsequenceData {
        @Min(1)
        private int capacity;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }

    @NotBlank
    private String name;
    @NotNull
    private String description;
    @Min(0)
    private int refreshFatePoint;
    @Valid @NotNull
    private List<AspectData> aspects;
    @Valid @NotNull
    private List<AbilityData> abilities;
    @Valid @NotNull
    private List<StuntData> stunts;
    @Valid @NotNull
    private List<ExtraData> extras;
    @Valid @NotNull
    private List<ConsequenceData> consequences;
    @Min(1)
    private int physics;
    @Min(1)
    private int mental;

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

    public List<AspectData> getAspects() {
        return aspects;
    }

    public void setAspects(List<AspectData> aspects) {
        this.aspects = aspects;
    }

    public List<AbilityData> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilityData> abilities) {
        this.abilities = abilities;
    }

    public List<StuntData> getStunts() {
        return stunts;
    }

    public void setStunts(List<StuntData> stunts) {
        this.stunts = stunts;
    }

    public List<ExtraData> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtraData> extras) {
        this.extras = extras;
    }

    public List<ConsequenceData> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<ConsequenceData> consequences) {
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
