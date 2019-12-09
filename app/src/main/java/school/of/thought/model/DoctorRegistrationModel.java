package school.of.thought.model;

import java.util.List;

public class DoctorRegistrationModel {
    public String name;
    public String drsignation;
    public String special_area;
    public String email;
    public String Mobile;
    public String pass;
    public List<DoctorChamberListModel> chamber_list;

    public DoctorRegistrationModel(String name, String drsignation, String special_area, String email, String mobile, String pass, List<DoctorChamberListModel> chamber_list) {
        this.name = name;
        this.drsignation = drsignation;
        this.special_area = special_area;
        this.email = email;
        Mobile = mobile;
        this.pass = pass;
        this.chamber_list = chamber_list;
    }

}
