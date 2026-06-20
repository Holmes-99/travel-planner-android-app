package com.example.a1231279_1230239_courseproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        EditText editTextFirstName = findViewById(R.id.editText_firstName);
        EditText editTextLastName = findViewById(R.id.editText_lastName);
        EditText editTextEmail = findViewById(R.id.editText_Remail);
        EditText editTextPassword = findViewById(R.id.editText_Rpassword);
        EditText editTextConfirmPassword = findViewById(R.id.editText_confirmPassword);
        EditText editTextPhone = findViewById(R.id.editText_phone);
        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        Spinner spinnerMajor = findViewById(R.id.spinner_Rmajor);
        Button buttonRegister = findViewById(R.id.button_register);

        //major
        String[] majorArray = getResources().getStringArray(R.array.major);
        ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorArray);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(majorAdapter);


    }
}