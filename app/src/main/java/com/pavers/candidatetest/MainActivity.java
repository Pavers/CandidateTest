package com.pavers.candidatetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pavers.candidatetest.Controller.UserController;
import com.pavers.candidatetest.View.CreateNewUser;
import com.pavers.candidatetest.View.UserData;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUsers;
    private UserController userController;
    private Button buttonCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCreateUser = findViewById(R.id.buttonCreateUser);
        addButtonListeners();
        collectReferences();


        displayAllUsers();
        UserData.setRecyclerView(rvUsers);

    }


    private void collectReferences() {

        rvUsers = findViewById(R.id.userRecyler);
        userController = new UserController(this, rvUsers);

    }

    private void addButtonListeners() {
        buttonCreateUser.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View px) {
                        createNewUser();
                    }
                }
        );
    }

    private void createNewUser() {
        Intent intent = new Intent(this, CreateNewUser.class);
        startActivity(intent);

    }

    private void displayAllUsers() {
        userController.getUsers();
    }

    private void testPicture() {

        userController.getUserPicture();
    }

    private void createUser() {

        userController.createNewUser();
    }


}
