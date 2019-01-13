package com.pavers.candidatetest.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pavers.candidatetest.Controller.UserController;
import com.pavers.candidatetest.Modals.UserHeaderModal;
import com.pavers.candidatetest.Modals.UserImageModal;
import com.pavers.candidatetest.Modals.UserInfoModal;
import com.pavers.candidatetest.Modals.UserModal;
import com.pavers.candidatetest.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateNewUser extends AppCompatActivity implements TextWatcher {

    private UserModal userModal = new UserModal();
    private Button saveUserButton;
    private EditText userName;
    private boolean userCreated = false;
    private UserController userController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userController = new UserController(this, UserData.getRecyclerView());
        setContentView(R.layout.create_user);
        createDummyUser();

        saveUserButton = findViewById(R.id.bSaveButton);
        userName = findViewById(R.id.etUserName);
        userName.addTextChangedListener(this);
        buttonListeners();
    }

    private void createDummyUser() {


        userModal.setUserImageModal(new UserImageModal());
        userModal.setUserInfoModal(new UserInfoModal());
        userModal.setUserHeaderModal(new UserHeaderModal());

        userModal.setId(UserData.getUserID());
        userModal.getUserHeaderModal().setUserTeam("Packing");
        userModal.getUserHeaderModal().setUserSex("m");
        userModal.getUserHeaderModal().setUserPermissionLevel(1);
        long currentTime = (System.currentTimeMillis() / 1000);
        int startDate = (int) currentTime;
        userModal.getUserInfoModal().setUserStartDate(startDate);
        userModal.getUserInfoModal().setUserLeaveDate(0);
        userModal.getUserInfoModal().setIsActive(1);
        userModal.getUserInfoModal().setPayGrade(1);
    }


    private void buttonListeners() {
        saveUserButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View px) {
                        if (userCreated) {
                            RequestBody body = convertUserToJson();

                            Log.d("button", "createing user....");
                            userController.createUser(body);
                            userCreated = false;
                        }
                    }
                }
        );
    }

    private RequestBody convertUserToJson() {

        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(new Gson().toJson(userModal));
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json.toString());
        return body;
    }

    @Override
    public void afterTextChanged(Editable s) {
        userModal.getUserHeaderModal().setUserName(userName.getText().toString());
        userCreated = true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


}
