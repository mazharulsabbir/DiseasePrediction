package school.of.thought.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import school.of.thought.R;
import school.of.thought.model.DoctorChamberListModel;
import school.of.thought.model.DoctorRegistrationModel;

public class DoctorRegistration extends AppCompatActivity {
    TextInputLayout name,designation,special_area,email,mobile,pass,chamber_name,district,specific_place,room_number;

    TextView anotherChamber;

    List<DoctorChamberListModel>chamber_list=new ArrayList<>();

    CircularProgressButton register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
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

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = mDatabase.push().getKey();

        register_button.setOnClickListener(v -> {
            DoctorChamberListModel doctorChamberListModel = new DoctorChamberListModel(
                    String.valueOf(chamber_name.getEditText().getText()),
                    String.valueOf(district.getEditText().getText()),
                    String.valueOf(specific_place.getEditText().getText()),
                    String.valueOf(room_number.getEditText().getText())
            );
            chamber_list.add(doctorChamberListModel);
            DoctorRegistrationModel doctorRegistrationModel = new DoctorRegistrationModel(

                    String.valueOf(name.getEditText().getText()),
                    String.valueOf(designation.getEditText().getText()),
                    String.valueOf(special_area.getEditText().getText()),
                    String.valueOf(email.getEditText().getText()),
                    String.valueOf(mobile.getEditText().getText()),
                    String.valueOf(pass.getEditText().getText()),
                    chamber_list);
            mDatabase.child("doctors").child(userId).setValue(doctorRegistrationModel);
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
}
