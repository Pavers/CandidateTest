package com.pavers.candidatetest.Modals;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserModal implements Serializable {

    private UserHeaderModal userHeaderModal;
    private UserInfoModal userInfoModal;
    private UserImageModal userImageModal;
    private int mData;

    public UserHeaderModal getUserHeaderModal() {
        return userHeaderModal;
    }

    public void setUserHeaderModal(UserHeaderModal userHeaderModal) {
        this.userHeaderModal = userHeaderModal;
    }

    public UserInfoModal getUserInfoModal() {
        return userInfoModal;
    }

    public void setUserInfoModal(UserInfoModal userInfoModal) {
        this.userInfoModal = userInfoModal;
    }

    public UserImageModal getUserImageModal() {
        return userImageModal;
    }

    public void setUserImageModal(UserImageModal userImageModal) {
        this.userImageModal = userImageModal;
    }

    public void setId(final int id){

        // TODO need to auto-generate unique userID
        userHeaderModal.setUserID(id);
        userInfoModal.setUserID(id);
        userImageModal.setUserID(id);
    }

}
