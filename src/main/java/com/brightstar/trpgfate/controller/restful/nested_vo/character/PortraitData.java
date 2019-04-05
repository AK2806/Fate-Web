package com.brightstar.trpgfate.controller.restful.nested_vo.character;

import javax.validation.constraints.Min;

public final class PortraitData {
    @Min(1)
    private int stature;
    @Min(0)
    private int headLeft;
    @Min(0)
    private int headTop;
    @Min(0)
    private int headRight;
    @Min(0)
    private int headBottom;

    public int getStature() {
        return stature;
    }

    public void setStature(int stature) {
        this.stature = stature;
    }

    public int getHeadLeft() {
        return headLeft;
    }

    public void setHeadLeft(int headLeft) {
        this.headLeft = headLeft;
    }

    public int getHeadTop() {
        return headTop;
    }

    public void setHeadTop(int headTop) {
        this.headTop = headTop;
    }

    public int getHeadRight() {
        return headRight;
    }

    public void setHeadRight(int headRight) {
        this.headRight = headRight;
    }

    public int getHeadBottom() {
        return headBottom;
    }

    public void setHeadBottom(int headBottom) {
        this.headBottom = headBottom;
    }
}
