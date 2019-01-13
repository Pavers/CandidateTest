package com.pavers.candidatetest.View;


import android.support.v7.widget.RecyclerView;

// I couldn't find any other way of getting the recyclerview passed through to the UserCard class
public final class UserData {

    private static RecyclerView recyclerView;
    private static String malePicUri = "https://randomuser.me/api/portraits/men/83.jpg";
    private static String femalPicUri = "https://randomuser.me/api/portraits/women/21.jpg";
    private static int userID = 300;

    private UserData(){};

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static void setRecyclerView(RecyclerView pRecyclerView) {
        recyclerView = pRecyclerView;
    }

    public static String getFemalePicUri() {
        return femalPicUri;
    }

    public static String getMalePicUri() {
        return malePicUri;
    }

    public static int getUserID() {
        return ++userID;
    }


}
