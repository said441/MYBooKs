package com.example.mybooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;




public class Admin extends AppCompatActivity {
    private EditText InputEmail;
    private EditText InputPassword;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button loginButton = findViewById(R.id.login_btn);
        TextView forgotpasword = findViewById(R.id.forget_password_link);
        forgotpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this,ForgotPassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });
        loadingBar =new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        InputPassword =  findViewById(R.id.login_password_input);
        InputEmail =  findViewById(R.id.login_phone_number_input);
        TextView notAdminlink = findViewById(R.id.not_admin_panel_link);
        notAdminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAdmin();
            }
        });




    }

    private void LoginAdmin() {
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();


            AllowAccessToAccount(email, password);
        }
    }

    private void AllowAccessToAccount(String email, String password) {
        String emailpater = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if (email.matches(emailpater)){
            if (password.length()>=8){
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    loadingBar.setTitle("Loding");
                                    loadingBar.setMessage("please Wait......");
                                    loadingBar.setCanceledOnTouchOutside(false);
                                    loadingBar.show();
                                    Intent intent = new Intent(Admin.this, AdminCategoryActivity.class);
                                    startActivity(intent);
                                    Admin.this.finish();

                                }else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(Admin.this,error,Toast.LENGTH_SHORT).show();


                                }

                            }
                        });

            }else {
                Toast.makeText(Admin.this,"Invalid email or password ",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }else {
            Toast.makeText(Admin.this,"Invalid email or password ",Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();


        }
    }


}
