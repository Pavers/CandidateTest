package com.pavers.candidatetest.Modals;

import java.io.Serializable;

public class UserHeaderModal implements Serializable {

    private int userID;
    private String userName;
    private int userPermissionLevel;
    private String userTeam;
    private String userSex;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPermissionLevel() {
        return userPermissionLevel;
    }

    public void setUserPermissionLevel(int userPermissionLevel) {
        this.userPermissionLevel = userPermissionLevel;
    }

    public String getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}
