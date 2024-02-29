package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.ModelClasses.RegisterUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    AppCompatButton signupBtn;
    EditText usrname, mail, pass;
    TextView textView;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    private String username, email, password;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        Ids();
        Clicklistners();
        Instances();

    }


    public void Ids() {
        textView = findViewById(R.id.loginTextView);
        usrname = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        signupBtn = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBarRegister);
    }

    public void Clicklistners() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usrname.getText().toString().trim();
                email = mail.getText().toString().trim();
                password = pass.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()
                ) {
                    Toast.makeText(MainActivity.this, "Enter Detail please", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(MainActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@gmail.com")) {
                    Toast.makeText(MainActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else {
                    SignupFirebase(email, password);
                }
            }
        });
    }

    private void Instances() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void SignupFirebase(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = auth.getCurrentUser();
                    userid = user.getUid();
                    Intent obj = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(obj);
                    AddUSerDataToRealtime(username, email, password, userid);

                    Toast.makeText(MainActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Auth Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddUSerDataToRealtime(String name, String email, String pass, String userid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(userid);
        RegisterUser obj = new RegisterUser(
                name,
                email,
                pass,
                userid
        );
        myRef.setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Database Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}