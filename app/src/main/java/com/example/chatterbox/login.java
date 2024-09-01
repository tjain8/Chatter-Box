package com.example.chatterbox;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.intellij.lang.annotations.RegExp;

public class login extends AppCompatActivity {
    Button but;
    TextView logSign;
    EditText email, pass;
    FirebaseAuth auth;
    String regExp="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        but = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTextForLogEmail);
        pass = findViewById(R.id.editTextLogPassword);
        logSign=findViewById(R.id.logSignUp);

        logSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,registration.class);
                startActivity(intent);
                finish();
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String password = pass.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    progressDialog.dismiss();
                    Toast.makeText(login.this,"Enter The Email.",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password)){
                    progressDialog.dismiss();
                    Toast.makeText(login.this,"Enter The Password.",Toast.LENGTH_LONG).show();
                }
                else if(!Email.matches(regExp)){
                    progressDialog.dismiss();
                    email.setError("Given proper Email Address");
                }
                else if(pass.length()<6){
                    progressDialog.dismiss();
                    pass.setError("More than 6 character");
                    Toast.makeText(login.this,"Password needs to be longer than 6 characters.",Toast.LENGTH_LONG).show();
                }
                else{
                    auth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.show();
                                try {
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}