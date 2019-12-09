package school.of.thought.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.DoctorRegistrationModel;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorHolder> {

    private List<DoctorRegistrationModel> doctorDetails;

    public DoctorListAdapter(List<DoctorRegistrationModel> doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_list, parent, false);
        return new DoctorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        DoctorRegistrationModel helper = doctorDetails.get(position);
        holder.name.setText(helper.getName());
        holder.designation.setText(helper.getDrsignation());
        holder.area.setText(helper.getSpecial_area());

        StringBuilder chambers = new StringBuilder();
        if (!helper.chamber_list.isEmpty())
            for (int i = 0; i < helper.chamber_list.size(); i++) {
                chambers.append(helper.chamber_list.get(i).chamber_name).append("\n");
            }
        holder.chamber.setText(chambers.toString());
    }

    @Override
    public int getItemCount() {
        Log.d("DoctorListAdapter", "getItemCount: "+doctorDetails.size());
        return doctorDetails.size();
    }

    public class DoctorHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView designation, area, chamber;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.doctor_image);
            name = itemView.findViewById(R.id.doctor_name);
            designation = itemView.findViewById(R.id.doctor_designation);
            area = itemView.findViewById(R.id.doctor_area);
            chamber = itemView.findViewById(R.id.doctor_chamber);
        }
    }
}
