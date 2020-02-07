package school.of.thought.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import school.of.thought.R;
import school.of.thought.model.DoctorChamberListModel;
import school.of.thought.model.DoctorRegistrationModel;

public class DoctorRegistration extends AppCompatActivity {
    public static final int PROFILE_IMAGE = 1;
    private TextInputLayout name, designation, special_area, email, mobile, pass, chamber_name, district, specific_place, room_number;
    private TextView anotherChamber;
    private ImageView profileImage;
    private String userId;
    private Uri imageURI;
    private String imageLink = "";
    private DatabaseReference mDatabase;

    private List<DoctorChamberListModel> chamber_list = new ArrayList<>();

    private CircularProgressButton register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Doctor Registration");
        }

        initViews();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mDatabase.push().getKey();

        profileImage.setOnClickListener(v -> {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PROFILE_IMAGE);
        });

        register_button.setOnClickListener(v -> {
            register_button.startAnimation();
            doctorRegistration();
        });

        anotherChamber.setOnClickListener(view1 -> {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.add_chamber, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText p_chamber_name = promptsView.findViewById(R.id.editTextChamberName);
            final EditText p_district = promptsView.findViewById(R.id.editTextDistrict);
            final EditText p_specific_place = promptsView.findViewById(R.id.editTexttSpecificPlaceName);
            final EditText p_room_number = promptsView.findViewById(R.id.editTextRoomNumber);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Add",
                            (dialog, id) -> {

                                // get user input and set it to result
                                // edit text
                                // result.setText(editTextChamberName.getText());
                                DoctorChamberListModel doctorChamberListModel = new DoctorChamberListModel(
                                        String.valueOf(p_chamber_name.getText()),
                                        String.valueOf(p_district.getText()),
                                        String.valueOf(p_specific_place.getText()),
                                        String.valueOf(p_room_number.getText())
                                );
                                chamber_list.add(doctorChamberListModel);
                            })
                    .setNegativeButton("Cancel",
                            (dialog, id) -> dialog.cancel());

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        });
    }

    private void initViews() {
        //User info
        name = findViewById(R.id.textInputName);
        designation = findViewById(R.id.textInputDesignation);
        special_area = findViewById(R.id.textInputSpecialArea);
        email = findViewById(R.id.textInputEmail);
        mobile = findViewById(R.id.textInputMobile);
        pass = findViewById(R.id.textInputPassword);
        //Chamber info
        chamber_name = findViewById(R.id.textInputChamberName);
        district = findViewById(R.id.textInputDistrict);
        specific_place = findViewById(R.id.textInputSpecificPlaceName);
        room_number = findViewById(R.id.textInputRoomNumber);
        anotherChamber = findViewById(R.id.another_chamber);
        register_button = findViewById(R.id.cirRegisterButton);
        profileImage = findViewById(R.id.doctor_profileImage);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void uploadProfileImage() {
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("profile_image/" + userId);
        ref.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageLink = uri.toString();
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("image Upload", "failed");
            }
        });
    }


    private void doctorRegistration() {

        String cName, cDistrict, cSpecificPlace, cRoomNumber;

        cName = String.valueOf(chamber_name.getEditText().getText());
        cDistrict = String.valueOf(district.getEditText().getText());
        cSpecificPlace = String.valueOf(specific_place.getEditText().getText());
        cRoomNumber = String.valueOf(room_number.getEditText().getText());

        String dName, dDesignation, dSpecialArea, dEmail, dMobile;

        dName = name.getEditText().getText().toString();
        dDesignation = String.valueOf(designation.getEditText().getText());
        dSpecialArea = String.valueOf(special_area.getEditText().getText());
        dEmail = String.valueOf(email.getEditText().getText());
        dMobile = String.valueOf(mobile.getEditText().getText());

        if (validateData(cName, cDistrict, cSpecificPlace, cRoomNumber, dName, dDesignation, dSpecialArea, dEmail, dMobile)) {
            DoctorChamberListModel doctorChamberListModel = new DoctorChamberListModel(
                    cName,
                    cDistrict,
                    cSpecificPlace,
                    cRoomNumber
            );
            chamber_list.add(doctorChamberListModel);

            DoctorRegistrationModel doctorRegistrationModel = new DoctorRegistrationModel(
                    dName,
                    dDesignation,
                    dSpecialArea,
                    dEmail,
                    dMobile,
                    imageLink,
                    chamber_list);

            mDatabase.child("doctors").child(userId).setValue(doctorRegistrationModel)
                    .addOnCompleteListener(task -> {
                        register_button.revertAnimation();

                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        } else register_button.revertAnimation();


    }

    private boolean validateData(String cName, String cDistrict, String cSpecificPlace, String cRoomNumber,
                                 String dName, String dDesignation, String dSpecialArea, String dEmail, String dMobile) {

        boolean validity = true;

        /*chamber info*/
        if (TextUtils.isEmpty(cName)) {
            validity = setError(chamber_name);
        } else chamber_name.setErrorEnabled(false);

        if (TextUtils.isEmpty(cDistrict)) {
            validity = setError(district);
        } else district.setErrorEnabled(false);

        if (TextUtils.isEmpty(cSpecificPlace)) {
            validity = setError(specific_place);
        } else specific_place.setErrorEnabled(false);

        if (TextUtils.isEmpty(cRoomNumber)) {
            validity = setError(room_number);
        } else room_number.setErrorEnabled(false);

        /*doctor info*/

        if (TextUtils.isEmpty(dName)) {
            validity = setError(name);
        } else name.setErrorEnabled(false);

        if (TextUtils.isEmpty(dEmail)) {
            validity = setError(email);
        } else email.setErrorEnabled(false);

        if (TextUtils.isEmpty(dMobile)) {
            validity = setError(mobile);
        } else mobile.setErrorEnabled(false);


        return validity;
    }

    private boolean setError(TextInputLayout inputLayout, String errorMsg) {
        if (inputLayout.getEditText() != null)
            inputLayout.getEditText().setError(errorMsg);

        return false;
    }

    private boolean setError(TextInputLayout inputLayout) {
        if (inputLayout.getEditText() != null)
            inputLayout.getEditText().setError("Can't be empty");

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PROFILE_IMAGE && resultCode == RESULT_OK) {
            imageURI = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                Glide.with(getApplicationContext()).load(bitmap).centerCrop().into(profileImage);
                uploadProfileImage();
                if (imageURI != null) {
                } else {
                    imageLink = "";
                }
            } catch (Exception e) {

            }

        }
    }
}
