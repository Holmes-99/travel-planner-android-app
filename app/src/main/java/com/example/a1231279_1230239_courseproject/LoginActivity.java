package com.example.a1231279_1230239_courseproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private String encryptPassword(String password) {
        try {
            java.security.MessageDigest md =
                    java.security.MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return password;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        findViewById(android.R.id.content).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_in));

        EditText editTextEmail = findViewById(R.id.editText_email);
        EditText editTextPassword = findViewById(R.id.editText_password);
        CheckBox checkBoxRememberMe = findViewById(R.id.checkBox_rememberMe);
        Button buttonLogin = findViewById(R.id.button_login);
        Button buttonSignup = findViewById(R.id.button_signup);

        SharedPreManager sharedPreManager = SharedPreManager.getInstance(this);

        //pre-fill if remember me is checked
        if(sharedPreManager.readBoolean("rememberMe", false)){
            editTextEmail.setText(sharedPreManager.readString("email", ""));
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogin.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.scale));


                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //validation
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                DataBaseHelper db = new DataBaseHelper(LoginActivity.this, "TravelPlanner.db", null, 1);
                Cursor cursor = db.getUserByEmail(email);

                if(cursor != null && cursor.moveToFirst()) {

                    String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("PASSWORD"));
                    if (storedPassword.equals(encryptPassword(password))) {

                        int userId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                        String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FIRSTNAME"));
                        String role = cursor.getString(cursor.getColumnIndexOrThrow("ROLE"));

                        //save login details
                        sharedPreManager.writeInt("loggedInUserId", userId);
                        sharedPreManager.writeString("LoggedInFirstName", firstName);
                        sharedPreManager.writeString("LoggedInRole", role);
                        sharedPreManager.writeString("LoggedInEmail", email);

                        //Remember me handling

                        if (checkBoxRememberMe.isChecked()) {
                            sharedPreManager.writeString("email", email);
                            sharedPreManager.writeBoolean("rememberMe", true);
                        } else {
                            sharedPreManager.writeBoolean("rememberMe", false);
                            sharedPreManager.writeString("email", "");
                        }
                        cursor.close();


                        if(role.equals("admin")) {

                            Intent intent = new Intent(LoginActivity.this,
                                    AdminActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Intent intent = new Intent(LoginActivity.this,
                                    HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Incorrect password!",
                                Toast.LENGTH_SHORT).show();
                    }

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Email not found!",
                                Toast.LENGTH_SHORT).show();
                         }


                    }
                });
                    buttonSignup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        }
            });
         }
     }
