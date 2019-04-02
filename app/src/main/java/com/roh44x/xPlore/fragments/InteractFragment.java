package com.roh44x.xPlore.fragments;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class InteractFragment extends SearchUserFragment {


    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query search = databaseReference.child("Users").limitToFirst(100);
        return search;
    }
}


