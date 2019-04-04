package com.roh44x.xPlore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roh44x.xPlore.model.Comment;
import com.roh44x.xPlore.model.Hour;
import com.roh44x.xPlore.model.Post;
import com.roh44x.xPlore.model.User;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POST_KEY = "post_key";
    private DatabaseReference mEventReference;
    private ValueEventListener mEventListener;
    private String mEventKey;
    private TextView startHour, subjectHour, hourInterval, teacherHour, classRoom;
    private EditText gradesHour, absencesHour;
    private Button saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        mEventKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        mEventReference = FirebaseDatabase.getInstance().getReference().child("Schedgule").child("clasa a 9-a").child(mEventKey);



        if (mEventKey == null) {
            throw new IllegalArgumentException("Must pass extra post key");
        }
        startHour = (TextView) findViewById(R.id.startHour);
        subjectHour = (TextView) findViewById(R.id.currentHour);
        hourInterval = (TextView) findViewById(R.id.intervalHour);
        teacherHour = (TextView) findViewById(R.id.teacherHour);
        classRoom = (TextView) findViewById(R.id.classRoom);
        gradesHour = (EditText)findViewById(R.id.grades);


    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Hour hour = dataSnapshot.getValue(Hour.class);

                startHour.setText(hour.startHour);
                subjectHour.setText(hour.subjectHour);
                hourInterval.setText(hour.hourInterval);
                teacherHour.setText(hour.teacherHour);
                classRoom.setText(hour.classRoom);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(EventDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mEventReference.addValueEventListener(eventListener);
        mEventListener = eventListener;

    }



}