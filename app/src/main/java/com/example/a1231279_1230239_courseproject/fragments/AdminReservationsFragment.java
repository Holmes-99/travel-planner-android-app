package com.example.a1231279_1230239_courseproject.fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1231279_1230239_courseproject.database.DataBaseHelper;
import com.example.a1231279_1230239_courseproject.R;

public class AdminReservationsFragment extends Fragment {

    public AdminReservationsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_reservations, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout listLayout = view.findViewById(R.id.layout_admin_reservations_list);
        DataBaseHelper db = new DataBaseHelper(
                getActivity(), "TravelPlanner.db", null, 1);

        Cursor cursor = db.getAllReservations();

        if(cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("USERID"));
                String destination = cursor.getString(
                        cursor.getColumnIndexOrThrow("TRIPDESTINATION"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("STATUS"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("TYPE"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("QUANTITY"));

                TextView tv = new TextView(getActivity());
                tv.setText("📍 " + destination +
                        "\n👤 User ID: " + userId +
                        "\n📅 Date: " + date +
                        "\n✅ Status: " + status +
                        "\n🧳 Type: " + type +
                        "\n👥 Quantity: " + quantity);
                tv.setTextSize(14);
                tv.setPadding(24, 24, 24, 24);
                tv.setBackgroundColor(0xFFFFFFFF);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 12);
                tv.setLayoutParams(params);

                listLayout.addView(tv);


            }while (cursor.moveToNext());
            cursor.close();
        } else {
            TextView tv = new TextView(getActivity());
            tv.setText("No reservations found.");
            tv.setPadding(24, 48, 24, 24);
            listLayout.addView(tv);
        }
    }
}