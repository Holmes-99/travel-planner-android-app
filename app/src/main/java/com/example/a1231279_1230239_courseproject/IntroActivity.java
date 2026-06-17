package com.example.a1231279_1230239_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private static final String API_URL = "http://10.0.2.2:8080/trips";
    ProgressBar progressBar;
    Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    progressBar = findViewById(R.id.progressBar);
    buttonConnect = findViewById(R.id.button_connect);

    buttonConnect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ConnectionAsyncTask(IntroActivity.this).execute(API_URL);
        }
    });
    }
    public void setProgress(boolean show) {
        if(show){
             progressBar.setVisibility(View.VISIBLE);
            buttonConnect.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            buttonConnect.setEnabled(true);
        }
    }

    public void onConnectionSuccess(List<Trip> trips) {
        DataBaseHelper db = new DataBaseHelper(IntroActivity.this, "TravelPlanner.db", null, 1);
        for(Trip trip : trips) {
            db.insertTrip(trip);
        }
        Toast.makeText(this,"Connected!1 " + trips.size() + " trips loaded", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        }

    public void onConnectionFailed() {
    Toast.makeText(this,"Connection failed", Toast.LENGTH_SHORT).show();
    }
}