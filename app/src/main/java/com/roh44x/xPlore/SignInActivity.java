package com.roh44x.xPlore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    //declare widgets

    public EditText etEmail, etPassword;
    public Button btnSignIn, btnCreateAccount;


    //declare firebase
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public FirebaseDatabase mDatabase;
    public DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        //init firebase

        initFirebase();

        //init widgets
        etEmail = (EditText)findViewById(R.id.etEmailSignIn);
        etPassword = (EditText)findViewById(R.id.etPasswordSignIn);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnCreateAccount = (Button)findViewById(R.id.createAccount);

        btnSignIn.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);


    }


    public void logInWithEmailAndPassword(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }else{
                        String error = task.getException().getMessage();
                        Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }


    public void initFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        userReference = mDatabase.getReference().child("users");
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.btnSignIn)
        {
            logInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString());
        }else if(i == R.id.createAccount){
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        }
    }
}
