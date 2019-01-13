package com.pavers.candidatetest.Adaptors;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavers.candidatetest.Controller.UserController;
import com.pavers.candidatetest.Modals.UserModal;
import com.pavers.candidatetest.R;
import com.pavers.candidatetest.View.UserCard;

import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ContactViewHolder> {

    private Activity mainActivity;
    private List<UserModal> allUsers;

    private UserModal currentUser;
    private RecyclerView rvUser;

    public UserAdaptor(List<UserModal> _allUsers, Activity mainActivity, RecyclerView recyclerView) {
        this.allUsers = _allUsers;
        this.mainActivity = mainActivity;
        this.rvUser = recyclerView;
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private View clickedView;
        TextView userName;

        ContactViewHolder(View view) {
            super(view);

            this.clickedView = view;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Item Clicked", "Clicked");
                }
            });

            userName = view.findViewById(R.id.tvName);

        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_allusers, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, final int position) {

        UserModal modal = allUsers.get(position);

        contactViewHolder.userName.setText(modal.getUserHeaderModal().getUserName());

        contactViewHolder.clickedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked", "Clicked Item" + position);

                currentUser = getItemAtPosition(position);

                if (currentUser != null) {
                    sendUserData();
                }
            }
        });
    }

    private void sendUserData() {
        //  I'm not sure if this is the best in-point for this task. I couldn't find another way.
        Intent intent = new Intent(this.mainActivity, UserCard.class);
        intent.putExtra("selectedUser", currentUser);
        mainActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    public UserModal getItemAtPosition(int position) {
        return allUsers.get(position);
    }


}
