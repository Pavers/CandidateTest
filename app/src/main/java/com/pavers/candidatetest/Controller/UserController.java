package com.pavers.candidatetest.Controller;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.pavers.candidatetest.API.API_Database;
import com.pavers.candidatetest.Adaptors.UserAdaptor;
import com.pavers.candidatetest.Config;
import com.pavers.candidatetest.Modals.UserModal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserController {

    private API_Database api_database;
    private List<UserModal> allUsers;
    private Activity mainActivity;
    private RecyclerView rvUser;


    public UserController(Activity _mainActivity, RecyclerView _rvUser) {

        this.mainActivity = _mainActivity;
        this.rvUser = _rvUser;

        Config config = new Config();
        Retrofit rfDatabase = config.databaseServer();
        api_database = rfDatabase.create(API_Database.class);

        getUsers();


    }

    private void getUsers() {

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
                        if(userModals.size() > 0) {
                            sorList(userModals);
                            allUsers = userModals;
                        }

                    }
                });
    }

    private void displayUsers() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        UserAdaptor userAdaptor = new UserAdaptor(allUsers);
        rvUser.setLayoutManager(linearLayoutManager);
        rvUser.setAdapter(userAdaptor);
    }

    private void sorList (List<UserModal> listIn) {

        Collections.sort(listIn, new Comparator<UserModal>(){
        public int compare(UserModal left, UserModal right){

            return left.getUserHeaderModal().getUserName().compareToIgnoreCase(right.getUserHeaderModal().getUserName());
        }
    });

    }

}
