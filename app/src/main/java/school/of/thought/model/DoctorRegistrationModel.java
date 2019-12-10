package school.of.thought.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class DoctorRegistrationModel {
    public String name;
    public String drsignation;
    public String special_area;
    public String email;
    public String mobile;
    public String pass;
    public String image;
    public List<DoctorChamberListModel> chamber_list = new ArrayList<>();

    public DoctorRegistrationModel() {
        //needed for firebase
    }

    public DoctorRegistrationModel(String name, String drsignation, String special_area, String email, String mobile, String pass, String image, List<DoctorChamberListModel> chamber_list) {
        this.name = name;
        this.drsignation = drsignation;
        this.special_area = special_area;
        this.email = email;
        this.mobile = mobile;
        this.pass = pass;
        this.image = image;
        this.chamber_list = chamber_list;
    }

    public DoctorRegistrationModel(String name, String drsignation, String special_area, List<DoctorChamberListModel> chamber_list) {
        this.name = name;
        this.drsignation = drsignation;
        this.special_area = special_area;
        this.chamber_list = chamber_list;
    }

    public String getName() {
        return name;
    }

    public String getDrsignation() {
        return drsignation;
    }

    public String getSpecial_area() {
        return special_area;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPass() {
        return pass;
    }

    public String getImage() {
        return image;
    }

    public List<DoctorChamberListModel> getChamber_list() {
        return chamber_list;
    }
}
