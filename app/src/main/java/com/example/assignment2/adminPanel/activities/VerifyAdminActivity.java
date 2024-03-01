package com.example.assignment2.adminPanel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment2.R;

public class VerifyAdminActivity extends AppCompatActivity {
     EditText verificationField;
    AppCompatButton verifyButton;
    private String adminCode = "100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_admin);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        verificationField = findViewById(R.id.verifyEd);
        verifyButton = findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getCode = verificationField.getText().toString().trim();

                if (getCode.equals(adminCode)){
                    Toast.makeText(VerifyAdminActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent verify = new Intent(VerifyAdminActivity.this, AdminDashboardActivity.class);
                    startActivity(verify);
                }else {
                    Toast.makeText(VerifyAdminActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}