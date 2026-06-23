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
import com.example.a1231279_1230239_courseproject.utils.SharedPreManager;

public class ReservationsFragment extends Fragment {

    public ReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);
    }

    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout listLayout = view.findViewById(R.id.layout_reservations_list);

        SharedPreManager prefs = SharedPreManager.getInstance(getActivity());
        int userId = prefs.readInt("loggedInUserId", -1);
//get users reservations from db
        DataBaseHelper db = new DataBaseHelper(
                getActivity(), "TravelPlanner.db", null, 1);
        Cursor cursor = db.getReservationByUserId(userId);
//show them
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String destination = cursor.getString(
                        cursor.getColumnIndexOrThrow("TRIPDESTINATION"));
                String date = cursor.getString(
                        cursor.getColumnIndexOrThrow("DATE"));
                String status = cursor.getString(
                        cursor.getColumnIndexOrThrow("STATUS"));
                String type = cursor.getString(
                        cursor.getColumnIndexOrThrow("TYPE"));
                int quantity = cursor.getInt(
                        cursor.getColumnIndexOrThrow("QUANTITY"));

                TextView tv= new TextView(getActivity());
                tv.setText(
                        "📍 " + destination +
                                "\n📅 Date: " + date +
                                "\n✅ Status: " + status +
                                "\n🧳 Type: " + type +
                                "\n👥 Quantity: " + quantity);
                tv.setTextSize(15);

                tv.setPadding(24,24,24,24);
                tv.setBackgroundColor(0xFFE7E1B1);
                tv.setTextColor(0xFF0D530E);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 16);
                tv.setLayoutParams(params);
                listLayout.addView(tv);
            }
            cursor.close();
        }
       else {
            //if no reservations
            TextView tv = new TextView(getActivity());
            tv.setText("No reservations yet.");
            tv.setTextSize(16);
            tv.setTextColor(0xFF306D29);
            tv.setPadding(24, 48, 24, 24);
            listLayout.addView(tv);
        }
    }
}