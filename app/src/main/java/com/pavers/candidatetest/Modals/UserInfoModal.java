package com.pavers.candidatetest.Modals;

import java.io.Serializable;

public class UserInfoModal implements Serializable {

    private int userID;
    private int userStartDate;
    private int isActive;
    private int userLeaveDate;
    private int payGrade;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserStartDate() {
        return userStartDate;
    }

    public void setUserStartDate(int userStartDate) {
        this.userStartDate = userStartDate;
    }


    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getUserLeaveDate() {
        return userLeaveDate;
    }

    public void setUserLeaveDate(int userLeaveDate) {
        this.userLeaveDate = userLeaveDate;
    }

    public int getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(int payGrade) {
        this.payGrade = payGrade;
    }
}
