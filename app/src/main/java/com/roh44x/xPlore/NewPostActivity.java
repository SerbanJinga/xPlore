package com.roh44x.xPlore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roh44x.xPlore.model.Post;
import com.roh44x.xPlore.model.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {


    //declare firebase

    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;
    public DatabaseReference userDatabase;
    public DatabaseReference postDatabase;


    //declare widgets

    public EditText etTitlePost, etBodyPost;
    public Button btnSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //init firebase

        initFirebase();

        //init widgets

        etTitlePost = (EditText)findViewById(R.id.etTitlePost);
        etBodyPost = (EditText)findViewById(R.id.etBodyPost);
        btnSubmitPost = (Button)findViewById(R.id.btnSubmitPost);


        btnSubmitPost.setOnClickListener(this);
    }

    public void initFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userDatabase = mDatabase.getReference();
        postDatabase = mDatabase.getReference();
    }



    public void submitPost()
    {
        final String title = etTitlePost.getText().toString();
        final String body = etBodyPost.getText().toString();

        final String userId = mAuth.getCurrentUser().getUid();

        userDatabase.child("Users")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if(user == null)
                        {
                            Toast.makeText(NewPostActivity.this, "User could not be fetched.", Toast.LENGTH_SHORT).show();
                        }else{
                            postNewEvent(userId, user.firstName, title, body);
                        }

                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public void postNewEvent(String userId, String username, String title, String body)
    {
        String key = postDatabase.child("posts").push().getKey();
        Post post =  new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts " + userId + "/" + key, postValues);

        postDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.btnSubmitPost)
        {
            submitPost();
        }
    }
}
