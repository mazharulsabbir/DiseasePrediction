package school.of.thought.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.DoctorRegistrationModel;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorHolder> {

    private List<DoctorRegistrationModel> doctorDetails;
    private Context context;

    public DoctorListAdapter(List<DoctorRegistrationModel> doctorDetails, Context context) {
        this.doctorDetails = doctorDetails;
        this.context = context;
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

        Glide.with(context).load(R.drawable.ic_launcher_background).circleCrop().into(holder.image);
        StringBuilder chambers = new StringBuilder();
        if (!helper.chamber_list.isEmpty())
            for (int i = 0; i < helper.chamber_list.size(); i++) {
                chambers.append(helper.chamber_list.get(i).chamber_name).append("\n");
            }
        holder.chamber.setText(chambers.toString());

        //appoinment button click
        holder.appoinment.setOnClickListener(v -> {

        });

        //cardview clik
        holder.cardview.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        Log.d("DoctorListAdapter", "getItemCount: " + doctorDetails.size());
        return doctorDetails.size();
    }

    public class DoctorHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView designation, area, chamber;
        private Button appoinment;
        private CardView cardview;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.doctor_image);
            name = itemView.findViewById(R.id.doctor_name);
            designation = itemView.findViewById(R.id.doctor_designation);
            area = itemView.findViewById(R.id.doctor_area);
            chamber = itemView.findViewById(R.id.doctor_chamber);
            appoinment = itemView.findViewById(R.id.appoinmentButton);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }
}
