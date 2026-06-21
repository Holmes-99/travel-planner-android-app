package com.example.a1231279_1230239_courseproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    EditText editTextFirstName,editTextLastName,editTextPhone;
    EditText editTextEmail, editTextGender, editTextMajor;
    EditText editTextPassword,editTextConfirmPassword;
    Button buttonUpdate, buttonPickPhoto;
    ImageView imageViewProfile;
    String profilePicUri = "";

    //pass encryption
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

    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK &&
                        result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    profilePicUri= selectedImage.toString();
                    imageViewProfile.setImageURI(selectedImage);
                }
            });
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextFirstName= view.findViewById(R.id.editText_profileFirstName);
        editTextLastName =view.findViewById(R.id.editText_profileLastName);

        editTextEmail = view.findViewById(R.id.editText_profileEmail);
        editTextGender = view.findViewById(R.id.editText_profileGender);
        editTextMajor = view.findViewById(R.id.editText_profileMajor);

        editTextEmail.setEnabled(false);
        editTextGender.setEnabled(false);
        editTextMajor.setEnabled(false);

        editTextPhone= view.findViewById(R.id.editText_profilePhone);
        editTextPassword = view.findViewById(R.id.editText_profilePassword);
        editTextConfirmPassword= view.findViewById(R.id.editText_profileConfirmPassword);


        buttonUpdate= view.findViewById(R.id.button_updateProfile);
        buttonPickPhoto= view.findViewById(R.id.button_pickPhoto);
        imageViewProfile= view.findViewById(R.id.imageView_profile);


        SharedPreManager prefs= SharedPreManager.getInstance(getActivity());
        int userId = prefs.readInt("loggedInUserId", -1);

        DataBaseHelper db= new DataBaseHelper(getActivity(), "TravelPlanner.db", null, 1);
        //load user data
        String email= prefs.readString("LoggedInEmail", "");
        Cursor cursor = db.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()){
            editTextFirstName.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("FIRSTNAME")));
            editTextLastName.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("LASTNAME")));
            editTextEmail.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("EMAIL")));

            editTextGender.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("GENDER")));

            editTextMajor.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("MAJOR")));
            editTextPhone.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow("PHONE")));
            String pic= cursor.getString(cursor.getColumnIndexOrThrow("PROFILEPIC"));
            if (pic!= null && !pic.isEmpty()){
                imageViewProfile.setImageURI(Uri.parse(pic));
                profilePicUri = pic;
            }
            cursor.close();
        }
        buttonPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (firstName.length() < 3 || lastName.length() < 3) {
                    Toast.makeText(getActivity(), "Name must be at least 3 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!phone.matches("[0-9]{10}")) {
                    Toast.makeText(getActivity(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor c= db.getUserByEmail(email);
                String currentPassword="";
                String currentGender="";
                if(c!= null && c.moveToFirst()){
                    currentPassword= c.getString(c.getColumnIndexOrThrow("PASSWORD"));
                    currentGender= c.getString(c.getColumnIndexOrThrow("GENDER"));
                    c.close();
                }
                String finalPassword=currentPassword;
                if(!password.isEmpty()){
                    if(password.length()<6 || !password.matches(".*[a-zA-Z].*")||!password.matches(".*[0-9].*")){
                        Toast.makeText(getActivity(),"Password must be at least 6 chars with letters and numbers",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(confirmPassword)){
                        Toast.makeText(getActivity(), "Passwords do not match",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    finalPassword= encryptPassword(password);
                }
                User user= new User();
                user.setId(userId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phone);
                user.setEmail(email);
                user.setGender(currentGender);
                user.setPassword(finalPassword);
                user.setprofilepic(profilePicUri);

                int result= db.updateUser(user);
                if (result >0){
                    prefs.writeString("LoggedInFirstName",firstName);
                    Toast.makeText(getActivity(),"Profile updated!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Update failed",Toast.LENGTH_SHORT).show();
                }
                }
        });
    }

}