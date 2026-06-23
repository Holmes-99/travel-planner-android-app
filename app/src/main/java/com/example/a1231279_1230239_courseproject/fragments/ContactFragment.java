package com.example.a1231279_1230239_courseproject.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1231279_1230239_courseproject.R;


public class ContactFragment extends Fragment {
    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }
    public  void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        Button buttonCall= view.findViewById(R.id.button_call);
        Button buttonLocate= view.findViewById(R.id.button_locate);
        Button buttonEmail= view.findViewById(R.id.button_email);

        //call button
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+970598776898"));
                startActivity(intent);
            }
        });
        //locate button
        buttonLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("geo:31.9769,35.2400?q=Birzeit+University"));
                startActivity(intent);
            }
        });
        //email button
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:travelplanner@example.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Travel Inquiry");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello, I would like to inquire about...");
                startActivity(intent);
            }
        });


    }
}















