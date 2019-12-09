package school.of.thought.model;

import school.of.thought.R;

public class DoctorChamberListModel {
    public String chamber_name;
    public String district;
    public String specific_place;
    public String room_number;

    public DoctorChamberListModel(String chamber_name, String district, String specific_place, String room_number) {
        this.chamber_name = chamber_name;
        this.district = district;
        this.specific_place = specific_place;
        this.room_number = room_number;
    }
}
