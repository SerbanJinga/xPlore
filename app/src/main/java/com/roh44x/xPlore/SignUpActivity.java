package com.roh44x.xPlore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roh44x.xPlore.model.AllHours;
import com.roh44x.xPlore.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
//TODO E TERMINATA CLASA

    //declare firebase
    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;
    public DatabaseReference userDatabase;
    public DatabaseReference schedguleDatabase;

    //declare widgets
    public EditText etFirstName, etLastName, etEmail, etPassword, etRepeatPassword;
    public Button btnSignUp, btnHaveAccount;
    public RadioButton btnTeacher, btnStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //init firebase
        initFirebase();

        //init widgets
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmailSignUp);
        etPassword = (EditText)findViewById(R.id.etPasswordSignUp);
        etRepeatPassword = (EditText)findViewById(R.id.etRepeatPasswordSignUp);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnHaveAccount = (Button)findViewById(R.id.haveAccount);
        btnStudent = (RadioButton) findViewById(R.id.btnStudent);
        btnTeacher = (RadioButton)findViewById(R.id.btnTeacher);

        btnSignUp.setOnClickListener(this);
        btnHaveAccount.setOnClickListener(this);
    }


    public void initFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        userDatabase = mDatabase.getReference();
        schedguleDatabase = mDatabase.getReference();
    }

    public void signUpWithEmailAndPassword(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            storeUserInDatabase();
                            storeSchedguleInDatabase();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public String getDegree()
    {
        if(btnStudent.isChecked())
            return "Student";
        if(btnTeacher.isChecked())
            return "Teacher";

        return "";
    }

    public void storeUserInDatabase()
    {
        User user = new User(etFirstName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(), getDegree());

        userDatabase.child("Users").child(mAuth.getCurrentUser().getUid())
                .setValue(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void storeSchedguleInDatabase()
    {
        AllHours allHours = new AllHours("romana", "mate", "mate", "informatica", "antreprenoriala", "sport", "desen");
        schedguleDatabase.child("Schedgule").setValue(allHours);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.btnSignUp)
        {
            signUpWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString());
        }else if(i == R.id.haveAccount){
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        }
    }
}
