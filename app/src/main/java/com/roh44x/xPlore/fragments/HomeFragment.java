package com.roh44x.xPlore.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.roh44x.xPlore.R;
import com.roh44x.xPlore.model.Post;
import com.roh44x.xPlore.viewholder.PostViewHolder;

public  class HomeFragment extends PostsFragment {




    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);
        return recentPostsQuery;
    }
}
