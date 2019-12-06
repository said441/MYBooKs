package com.example.mybooks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private ImageView goback;
    private EditText emailid;
    private Button resetbtn;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        goback = (ImageView) findViewById(R.id.goback);
        emailid = (EditText) findViewById(R.id.registred_email);
        resetbtn = (Button) findViewById(R.id.resetpassword_btn);
        firebaseAuth= FirebaseAuth.getInstance();


        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkinput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetbtn.setEnabled(false);
                resetbtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.sendPasswordResetEmail(emailid.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this,"email sent seccessfully!",Toast.LENGTH_SHORT).show();
                                    finish();

                                }else {
                                    String error=task.getException().getMessage();
                                    Toast.makeText(ForgotPassword.this,error,Toast.LENGTH_SHORT).show();

                                }
                                resetbtn.setEnabled(true);
                                resetbtn.setTextColor(Color.WHITE);
                            }
                        });
            }
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });
    }

    private void checkinput() {
        if (TextUtils.isEmpty(emailid.getText().toString())){
            resetbtn.setEnabled(false);
            resetbtn.setTextColor(Color.argb(50,255,255,255));

        }else {
            resetbtn.setEnabled(true);
            resetbtn.setTextColor(Color.WHITE);

        }
    }


}
