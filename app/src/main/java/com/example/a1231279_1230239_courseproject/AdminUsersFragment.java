package com.example.a1231279_1230239_courseproject;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AdminUsersFragment extends Fragment {

    LinearLayout listLayout;
    DataBaseHelper db;

    public AdminUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_users, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listLayout = view.findViewById(R.id.layout_admin_users_list);
        db= new DataBaseHelper(getActivity(), "TravelPlanner.db", null, 1);

        Button btnAddAdmin= view.findViewById(R.id.button_add_admin);
        btnAddAdmin.setOnClickListener(v -> showAddAdminDialog());
        loadUsers();
    }
    private void loadUsers(){
        listLayout.removeAllViews();
        Cursor cursor= db.getAllUsers();


        if (cursor!= null && cursor.moveToFirst()){
            do{
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FIRSTNAME"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("LASTNAME"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("ROLE"));

                LinearLayout card= new LinearLayout(getActivity());
                card.setOrientation(LinearLayout.VERTICAL);
                card.setBackgroundColor(0xFFFFFFFF);
                card.setPadding(24,24,24,24);
                LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,12);
                card.setLayoutParams(params);

                TextView tvInfo= new TextView(getActivity());
                tvInfo.setText("👤 " + firstName + " " + lastName +
                        "\n📧 " + email +
                        "\n🔑 Role: " + role);
                tvInfo.setTextSize(14);
                tvInfo.setPadding(0,0,0,12);

                Button btnDelete= new Button(getActivity());
                btnDelete.setText("Delete User");
                btnDelete.setTextColor(0xFFFFFFFF);
                btnDelete.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(0xFFE57373));//red because it deletes

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (role.equals("admin")) {
                            Toast.makeText(getActivity(),"Cannot delete admin account",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        db.deleteUser(userId);
                        Toast.makeText(getActivity(), "User deleted", Toast.LENGTH_SHORT).show();
                        loadUsers();
                    }
                });

                card.addView(tvInfo);
                card.addView(btnDelete);
                listLayout.addView(card);

            }while(cursor.moveToNext());
            cursor.close();
        }
    }
    private void showAddAdminDialog(){
        //show dialog for creating a new admin account
        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_admin, null);

        EditText etFirstName= dialogView.findViewById(R.id.editText_adminFirstName);
        EditText etLastName= dialogView.findViewById(R.id.editText_adminLastName);
        EditText etEmail= dialogView.findViewById(R.id.editText_adminEmail);
        EditText etPassword= dialogView.findViewById(R.id.editText_adminPassword);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Admin");
        builder.setView(dialogView);
        builder.setPositiveButton("Add",(dialog, which) -> {

            String firstName= etFirstName.getText().toString().trim();
            String lastName= etLastName.getText().toString().trim();
            String email= etEmail.getText().toString().trim();
            String password= etPassword.getText().toString().trim();

            //validate input
            if (firstName.isEmpty()||lastName.isEmpty()||
                    email.isEmpty()|| password.isEmpty()){
                Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            User admin= new User();
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(email);
            admin.setPassword(encryptPassword(password));
            admin.setRole("admin");
            admin.setGender("");
            admin.setMajor("");
            admin.setPhone("");


            long result= db.insertUser(admin);
            if (result!= -1) {
                Toast.makeText(getActivity(), "Admin added Successfully!", Toast.LENGTH_SHORT).show();
                loadUsers();
            } else {
                Toast.makeText(getActivity(), "Email already in use", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // hash password before saving
    private String encryptPassword(String password){
        try {
            java.security.MessageDigest md =
                    java.security.MessageDigest.getInstance("SHA-1");
            byte[] messageDigest= md.digest(password.getBytes());
            StringBuilder hexString= new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        }catch (Exception e) {
            return password;
        }
    }

}