package com.roh44x.xPlore;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roh44x.xPlore.fragments.EventFragment;
import com.roh44x.xPlore.fragments.HomeFragment;
import com.roh44x.xPlore.fragments.InteractFragment;
import com.roh44x.xPlore.fragments.ProfileFragment;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    //declare widgets

    public BottomNavigationView bottomNavigationView;
    public FloatingActionButton btnCreatePost;

    //declare firebase

    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;
    public DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init firebase

        initFirebase();

        //load fragment Home Fragment -> Default Fragment

        loadFragment(new HomeFragment());


        //init widgets

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNav);
        btnCreatePost = (FloatingActionButton) findViewById(R.id.createPost);


        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        btnCreatePost.setOnClickListener(this);




    }


    public void initFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userDatabase = mDatabase.getReference();
    }

    private boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainRoot, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.itemHome:
                fragment = new HomeFragment();
                break;
            case R.id.itemProfile:
                fragment = new ProfileFragment();
                break;
            case R.id.itemEvents:
                fragment = new EventFragment();
                break;
            case R.id.itemInteract:
                fragment = new InteractFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.createPost)
        {
            startActivity(new Intent(MainActivity.this, NewPostActivity.class));
        }
    }
}
