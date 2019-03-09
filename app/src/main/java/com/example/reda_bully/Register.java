package com.example.reda_bully;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText firstname;
    private EditText lastname;
    private EditText dob;
    private EditText email;
    private EditText password;
    private EditText repassword;
    private EditText contact;

    private TextView text;

    private RadioGroup gender;

    private Button register;

    private ProgressDialog progressdialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressdialog = new ProgressDialog(this);

        text = (TextView) findViewById(R.id.loginlink);
        firstname = (EditText) findViewById(R.id.txtfirstname);
        lastname = (EditText) findViewById(R.id.txtlastname);
        dob = (EditText) findViewById(R.id.txtdob);
        email = (EditText) findViewById(R.id.txtemail);
        password = (EditText) findViewById(R.id.txtpassword1);
        repassword = (EditText) findViewById(R.id.txtpassword2);
        contact = (EditText) findViewById(R.id.txtcontact);
        gender = (RadioGroup) findViewById(R.id.rdbgrp);
        register = (Button) findViewById(R.id.btnsignup);

        register.setOnClickListener(this);
        text.setOnClickListener(this);
    }

    private void registerUser() {
        String first = firstname.getText().toString().trim();
        String last = lastname.getText().toString().trim();
        String DOB = dob.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String repass = repassword.getText().toString().trim();
        String ctct = contact.getText().toString().trim();

        if (TextUtils.isEmpty(first)) {
            Toast.makeText(this, "Please insert Firstname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(last)) {
            Toast.makeText(this, "Please insert Lastname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(DOB)) {
            Toast.makeText(this, "Please insert Date of Birth", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(em)) {
            Toast.makeText(this, "Please insert Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please insert Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repass)) {
            Toast.makeText(this, "Please retype password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!repass.equals(pass)) {
            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ctct)) {
            Toast.makeText(this, "Please insert Contact", Toast.LENGTH_SHORT).show();
            return;
        }

        progressdialog.setMessage("Registering User...");
        progressdialog.show();

        firebaseAuth.createUserWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }
                         else {
                            Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                        progressdialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            int selectedId = gender.getCheckedRadioButtonId();

            // find the radio button by returned id
            RadioButton gender = (RadioButton) findViewById(selectedId);

            registerUser();
        }
        if (v == text) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
