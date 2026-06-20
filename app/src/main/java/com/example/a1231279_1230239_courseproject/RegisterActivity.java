package com.example.a1231279_1230239_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

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
    }catch(Exception e){
                return password;
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextEmail = findViewById(R.id.editText_Remail);
        EditText editTextFirstName = findViewById(R.id.editText_firstName);
        EditText editTextLastName = findViewById(R.id.editText_lastName);
        EditText editTextPassword = findViewById(R.id.editText_Rpassword);
        EditText editTextConfirmPassword = findViewById(R.id.editText_confirmPassword);
        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        Spinner spinnerMajor = findViewById(R.id.spinner_Rmajor);
        EditText editTextPhone = findViewById(R.id.editText_phone);

        Button buttonRegister = findViewById(R.id.button_register);

        //major
        String[] majorArray = { "Computer Engineering","Computer science",
                                 "Civil Engineering","Medicine","Business",
                                  "Law","other"};
        ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, majorArray);
        spinnerMajor.setAdapter(majorAdapter);

        //gender
        String[] genderArray = { "Male","Female","Other"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genderArray);
        spinnerGender.setAdapter(genderAdapter);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
            String email = editTextEmail.getText().toString();
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword =editTextConfirmPassword.getText().toString();
            String gender = spinnerGender.getSelectedItem().toString();
            String major = spinnerMajor.getSelectedItem().toString();
            String phone = editTextPhone.getText().toString();

            //validation

            if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
            || password.isEmpty() || confirmPassword.isEmpty() ||phone.isEmpty() ){
            Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;}

        if(firstName.length() < 3 || lastName.length() < 3){
            Toast.makeText(RegisterActivity.this, "First name and last name must be at least 3 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean hasletter = false;
        boolean hasnumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasletter = true;
            } else if (Character.isDigit(c)) {
                hasnumber = true;
            }
        }
            if (!hasletter || !hasnumber) {
                Toast.makeText(RegisterActivity.this, "Password must contain at least one letter and one number",
                        Toast.LENGTH_SHORT).show();
                return;
            }


        if(!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivity.this, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(encryptPassword(password));
        user.setGender(gender);
        user.setMajor(major);
        user.setPhone(phone);

        DataBaseHelper dbHelper = new DataBaseHelper(RegisterActivity.this,"TravelPlanner.db",null,1);

        long result = dbHelper.insertUser(user);
        if(result == -1){
            Toast.makeText(RegisterActivity.this, "Registration failed,Email already in use!",
                    Toast.LENGTH_SHORT).show();
            }else{
            Toast.makeText(RegisterActivity.this, "Registration successful!",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();



         }
     }

     });
   }
}