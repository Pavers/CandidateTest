package com.pavers.candidatetest.Controller;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pavers.candidatetest.API.API_Database;
import com.pavers.candidatetest.API.API_RandomUser;
import com.pavers.candidatetest.Adaptors.UserAdaptor;
import com.pavers.candidatetest.Config;
import com.pavers.candidatetest.Modals.APIResponseModal;
import com.pavers.candidatetest.Modals.UpdateUserModal;
import com.pavers.candidatetest.Modals.UserHeaderModal;
import com.pavers.candidatetest.Modals.UserImageModal;
import com.pavers.candidatetest.Modals.UserInfoModal;
import com.pavers.candidatetest.Modals.UserModal;
import com.pavers.candidatetest.View.UserData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserController {

    private API_Database api_database;
    private API_RandomUser api_randomUser;
    private List<UserModal> allUsers;
    private Activity mainActivity;
    private RecyclerView rvUser;

    private UserAdaptor userAdaptor;
    private static int globalUserId = 200;


    public UserController(Activity _mainActivity, RecyclerView _rvUser) {

        this.mainActivity = _mainActivity;
        this.rvUser = _rvUser;

        Config config = new Config();
        Retrofit rfDatabase = config.databaseServer();
        api_database = rfDatabase.create(API_Database.class);

        Config config2 = new Config();
        Retrofit rfImageDb = config2.imageServer();
        api_randomUser = rfImageDb.create(API_RandomUser.class);
    }

    public void getUsers() {

        Observable<List<UserModal>> getAllUsers = api_database.getAllUsers();

        getAllUsers
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserModal>>() {
                    @Override
                    public void onCompleted() {
                        displayUsers();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Fetch All Users", e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserModal> userModals) {
                        if (userModals.size() > 0) {
                            sortList(userModals);
                            allUsers = userModals;
                        }
                    }
                });
    }

    public void updateUser(final RequestBody requestBody) {

        try {
            Observable<APIResponseModal> updateUser = api_database.updateUser(requestBody);

            updateUser
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<APIResponseModal>() {
                        @Override
                        public void onCompleted() {
                            displayUsers();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Update User", e.getMessage());
                        }

                        @Override
                        public void onNext(APIResponseModal userModals) {
                            // Always an empty response.
                        }
                    });
        } catch (Exception ex) {
            Log.d("Error saving!", ex.getMessage());
        }
    }

    public void deleteUser(final UserModal userModal) {
        try {
            Observable<APIResponseModal> deleteUser = api_database.deleteUser(userModal.getUserHeaderModal().getUserID());

            deleteUser
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<APIResponseModal>() {
                        @Override
                        public void onCompleted() {
                            displayUsers();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Delete User", e.getMessage());
                            Log.v("logcat", "delete user", e.getCause());
                        }

                        @Override
                        public void onNext(APIResponseModal userModals) {
                            // Always an empty response.
                        }
                    });
        } catch (Exception ex) {
            Log.d("Error deleting user!", ex.getMessage());
        }
    }

    public void createUser(RequestBody body) {

        Observable<UserModal> createUser = api_database.createUser(body);

        createUser
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserModal>() {
                    @Override
                    public void onCompleted() {
                         //displayUsers();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Create user", e.getMessage());
                        Log.v("logcat", "create", e.getCause());
                    }

                    @Override
                    public void onNext(UserModal userModal) {
//                            String strName = userModal.getUserHeaderModal().getUserName();
                        Log.d("Create user", "User created!");
                    }
                });
    }

    private void displayUsers() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        userAdaptor = new UserAdaptor(allUsers, mainActivity, rvUser);
        rvUser.setLayoutManager(linearLayoutManager);
        rvUser.setAdapter(userAdaptor);
    }

    public void getUserPicture() {

        Observable<UserImageModal> getImage = api_randomUser.getImage("male");

        getImage
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserImageModal>() {
                    @Override
                    public void onCompleted() {
                        int i = 0;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Fetch User Image", e.getMessage());
                    }

                    @Override
                    public void onNext(UserImageModal userImageModel) {
                        //TODO find out this this doesn't work.
                        //  Gson gson = new Gson();
                        //  UserImageModal ob = (UserImageModal)  gson.fromJson(UserImageModal.class);
                    }

                    ;
                });
    }

    public void saveUserData(final UpdateUserModal updateUserModal) {
        String postJson = new Gson().toJson(updateUserModal);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), postJson);
        updateUser(body);
    }

    public void createNewUser() {

        // This is hard-coded as a test and proof of concept.
        int userId = globalUserId++;
        UserModal freshUser = new UserModal();
        UserInfoModal freshUserInfoModel = new UserInfoModal();
        UserHeaderModal freshUserHeadModel = new UserHeaderModal();
        UserImageModal freshUserImageModel = new UserImageModal();
        freshUser.setUserHeaderModal(freshUserHeadModel);
        freshUser.setUserInfoModal(freshUserInfoModel);
        freshUser.setUserImageModal(freshUserImageModel);

        freshUser.getUserInfoModal().setIsActive(1);
        freshUser.getUserInfoModal().setPayGrade(1);
        freshUser.getUserInfoModal().setUserID(userId);
        freshUser.getUserInfoModal().setUserStartDate(1234);
        freshUser.getUserInfoModal().setUserLeaveDate(0);

        freshUser.getUserHeaderModal().setUserName("RJ Macready");
        freshUser.getUserHeaderModal().setUserPermissionLevel(1);
        freshUser.getUserHeaderModal().setUserSex("m");
        freshUser.getUserHeaderModal().setUserTeam("Stock Control");
        freshUser.getUserHeaderModal().setUserID(userId);

        freshUser.getUserImageModal().setPicture(UserData.getMalePicUri());
        freshUser.getUserImageModal().setUserID(userId);

        // String postJson = new Gson().toJson(freshUser);
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(new Gson().toJson(freshUser));

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json.toString());
        createUser(body);
    }

    private void sortList(List<UserModal> listIn) {

        Collections.sort(listIn, new Comparator<UserModal>() {
            public int compare(UserModal left, UserModal right) {

                return left.getUserHeaderModal().getUserName().compareToIgnoreCase(right.getUserHeaderModal().getUserName());
            }
        });

    }

}
