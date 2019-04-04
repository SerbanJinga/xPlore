package com.roh44x.xPlore.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.roh44x.xPlore.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventFragment extends EventsAdapaterFragment {


    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query hourQuery = databaseReference.child("Schedgule").child("clasa a 9-a").limitToFirst(100);
        return hourQuery;
    }
}
