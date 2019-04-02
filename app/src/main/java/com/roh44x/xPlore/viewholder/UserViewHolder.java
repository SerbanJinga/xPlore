package com.roh44x.xPlore.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.roh44x.xPlore.R;
import com.roh44x.xPlore.model.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView userName, userDegree;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);


        userName = itemView.findViewById(R.id.userName);
        userDegree = itemView.findViewById(R.id.userDegree);
    }


    public void bindToUser(User user){
        userName.setText(user.firstName);
        userDegree.setText(user.degree);
    }
}
