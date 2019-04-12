package com.brightstar.trpgfate.controller.restful.userdata.vo;

import java.util.List;

public final class FollowerGetListResp {
    private List<Integer> usersId;

    public List<Integer> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<Integer> usersId) {
        this.usersId = usersId;
    }
}
