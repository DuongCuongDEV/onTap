package com.example.ontap;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onRegisterButtonClick(View view) {
        EditText etNewUsername = findViewById(R.id.etNewUsername);
        EditText etNewPassword = findViewById(R.id.etNewPassword);

        String email = etNewUsername.getText().toString();
        String password = etNewPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Chuyển sang màn hình đăng nhập
                        Intent intent = new Intent(activity_register.this, activity_login.class);
                        startActivity(intent);
                        finish(); // Đóng màn hình đăng ký

                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(activity_register.this, "Đăng ký thất bại.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}