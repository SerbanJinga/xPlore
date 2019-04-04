package com.roh44x.xPlore.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.roh44x.xPlore.R;
import com.roh44x.xPlore.model.Post;
import com.roh44x.xPlore.model.User;
import com.roh44x.xPlore.viewholder.PostViewHolder;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private ImageView profilePicture;
    private TextView profileName, profileDegree;
    private DatabaseReference userDatabase;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, null);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        profilePicture = (ImageView) rootView.findViewById(R.id.photoProfile);
        profileName = (TextView) rootView.findViewById(R.id.nameProfile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = rootView.findViewById(R.id.profileList);
        mRecycler.setHasFixedSize(true);
        profileDegree = (TextView) rootView.findViewById(R.id.profileDegree);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String profile = user.firstName + " " + user.lastName;
                profileName.setText(profile);
                profileDegree.setText(user.degree);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userDatabase.addValueEventListener(userListener);
    }


}
