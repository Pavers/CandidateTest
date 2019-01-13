package com.pavers.candidatetest.Modals;

public class UpdateUserModal {

    private int userPermissionLevel;
    private String userTeam;
    private String userPayGrade;
    private String picture;

    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }

    public void setUserPayGrade(String userPayGrade) {
        this.userPayGrade = userPayGrade;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUserPermissionLevel(int userPermissionLevel) {

        this.userPermissionLevel = userPermissionLevel;
    }
}
