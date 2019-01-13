package com.pavers.candidatetest.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pavers.candidatetest.Controller.UserController;
import com.pavers.candidatetest.Modals.UpdateUserModal;
import com.pavers.candidatetest.Modals.UserModal;
import com.pavers.candidatetest.R;

public class UserCard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private UserModal userModal;
    private TextView userName;
    private TextView userGender;
    private TextView userActive;
    private TextView userStartDate;
    private TextView userEndDate;
    private ImageView userPicUri;

    private UpdateUserModal updateUserModal = new UpdateUserModal();
    private UserController userController;

    private Button saveUserButton;
    private Button deleteUserButton;
    private RecyclerView rvUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardview_single_user);
        userModal = (UserModal) getIntent().getSerializableExtra("selectedUser");

        this.rvUser = UserData.getRecyclerView();

        userController = new UserController(this, this.rvUser);

        userName = findViewById(R.id.tvNameEdit);
        userGender = findViewById(R.id.tvGender);
        userActive = findViewById(R.id.tvActive);
        userStartDate = findViewById(R.id.tvStartDate);
        userEndDate = findViewById(R.id.tvEndDate);
        userPicUri = findViewById(R.id.card_image);

        saveUserButton = findViewById(R.id.tvSaveButton);
        deleteUserButton = findViewById(R.id.delete_button);
        displayUser();
        getSpinners();
        buttonListeners();
    }


    private void displayUser() {

        userName.setText(userModal.getUserHeaderModal().getUserName());
        userGender.setText(convertGender(userModal.getUserHeaderModal().getUserSex()));
        userActive.setText(convertActive(userModal.getUserInfoModal().getIsActive()));

        int lStartDate = userModal.getUserInfoModal().getUserStartDate();
        if (lStartDate != 0) {
            userStartDate.setText(convertDate(lStartDate));
        } else {
            userStartDate.setText(String.valueOf(lStartDate));
        }

        int lEndDate = userModal.getUserInfoModal().getUserLeaveDate();
        if (lEndDate != 0) {
            userEndDate.setText(convertDate(lEndDate));
        } else {
            userEndDate.setText(String.valueOf(lEndDate));
        }

    }

    private void getSpinners() {

        // Permission Spinner
        Spinner permSpinner = findViewById(R.id.permissions_spinner);
        String[] perms = new String[]{"0", "1"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, perms);
        permSpinner.setAdapter(adapter);
        permSpinner.setOnItemSelectedListener(this);
        permSpinner.setSelection(userModal.getUserHeaderModal().getUserPermissionLevel());

        // Paygrade Spinner
        Spinner paygradeSpinner = findViewById(R.id.paygrade_spinner);
        String[] paygrades = new String[]{"0", "1"};
        ArrayAdapter<String> paygradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paygrades);
        paygradeSpinner.setAdapter(paygradeAdapter);
        paygradeSpinner.setOnItemSelectedListener(this);
        paygradeSpinner.setSelection(userModal.getUserInfoModal().getPayGrade());

        // Team Spinner
        Spinner teamSpinner = findViewById(R.id.team_spinner);
        String[] teamChoices = new String[]{"Picking", "Packing", "Stock Control"};
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teamChoices);
        teamSpinner.setAdapter(teamAdapter);
        teamSpinner.setOnItemSelectedListener(this);
        int selectedPos = convertTeamValue(userModal.getUserHeaderModal().getUserTeam());
        teamSpinner.setSelection(selectedPos);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int spinnerId = parent.getId();
        switch (spinnerId) {
            case R.id.permissions_spinner:
                switch (position) {
                    case 0:
                        Log.d("Permission level: ", "zero");
                        updateUserModal.setUserPermissionLevel(0);
                        break;
                    case 1:
                        Log.d("Permission level: ", "one");
                        updateUserModal.setUserPermissionLevel(1);
                        break;
                    default:
                        Log.d("Error choosing perm", "invalid permission level");
                } // end of permissions case

            case R.id.paygrade_spinner: {
                switch (position) {
                    case 0:
                        Log.d("Paygrade level: ", "zero");
                        updateUserModal.setUserPayGrade("0");
                        break;
                    case 1:
                        Log.d("Paygrade level: ", "one");
                        updateUserModal.setUserPayGrade("1");
                        break;
                    default:
                        Log.d("Error Paygrade spinner", "default case");
                } // end of paygrade case
            }
            case R.id.team_spinner: {
                switch (position) {
                    case 0:
                        Log.d("Team: ", "Picking");
                        updateUserModal.setUserTeam("Picking");
                        break;
                    case 1:
                        Log.d("Team: ", "Packing");
                        updateUserModal.setUserTeam("Packing");
                        break;
                    case 2:
                        Log.d("Team: ", "Stock Control");
                        updateUserModal.setUserTeam("Stock Control");
                        break;
                    default:
                        Log.d("Error in team spinner", "default case");
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void buttonListeners() {

        saveUserButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View pv) {
                        updateUserModal.setPicture(userPicUri.toString());
                        userController.saveUserData(updateUserModal);
                    }
                }
        );

        deleteUserButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View px) {
                        userController.deleteUser(userModal);
                    }
                }
        );
    }

    // ------------------------ Helper Methods -------------------------------------- //

    private String convertDate(final int epoch) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(epoch));
    }

    private String convertGender(final String gender) {
        String result = gender.equalsIgnoreCase("m") ? setMalePic() : setFemalPic();

        return result;
    }

    // Another hack because I can't get the pictures from the random user API
    private String setMalePic() {

        Glide.with(this)
                .load(UserData.getMalePicUri())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(userPicUri);

        return "Male";
    }

    private String setFemalPic() {

        Glide.with(this)
                .load(UserData.getFemalePicUri())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(userPicUri);

        return "Female";
    }

    private String convertActive(final int isActive) {
        String activeResult = isActive == 1 ? "Yes" : "No";

        return activeResult;
    }

    private int convertTeamValue(final String teamVal) {

        int retVal = 0;
        switch (teamVal) {
            case "Picking":
                retVal = 0;
                break;
            case "Packing":
                retVal = 1;
                break;
            case "Stock Control":
                retVal = 2;
                break;
            default:
                Log.d("Error textToInt", "convert team value");
        }

        return retVal;
    }
}
