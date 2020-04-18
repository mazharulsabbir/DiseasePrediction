package school.of.thought.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import school.of.thought.R;
import school.of.thought.activity.Doctor_Appoinment;
import school.of.thought.model.DoctorRegistrationModel;
import school.of.thought.utils.Utils;

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
        holder.bind(helper);
    }

    @Override
    public int getItemCount() {
        Log.d("DoctorListAdapter", "getItemCount: " + doctorDetails.size());
        return doctorDetails.size();
    }

    class DoctorHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView designation, chamber;
        private ChipGroup area;
        private MaterialButton appointment;

        private DoctorHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.doctor_image);
            name = itemView.findViewById(R.id.doctor_name);
            designation = itemView.findViewById(R.id.doctor_designation);
            area = itemView.findViewById(R.id.special_area_chips);
            chamber = itemView.findViewById(R.id.chambers);
            appointment = itemView.findViewById(R.id.doctor_appointment);

            itemView.setOnClickListener(view -> {

            });

            //appointment button click
            appointment.setOnClickListener(v -> {

            });
        }

        private void bind(DoctorRegistrationModel doctor) {
            name.setText(doctor.getName());
            designation.setText(doctor.getDrsignation());
            Chip newArea = new Chip(area.getContext());

            newArea.setText(doctor.special_area);
            area.addView(newArea);

            Glide.with(context).load(doctor.getImage())
                    .load(Utils.COMMON_USER_AVATAR_URL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(image);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                image.setClipToOutline(true);
            }

            StringBuilder chambers = new StringBuilder();
            if (!doctor.chamber_list.isEmpty())
                for (int i = 0; i < doctor.chamber_list.size(); i++) {
                    chambers.append(doctor.chamber_list.get(i).chamber_name);

                    if (i < doctor.chamber_list.size() - 1)
                        chambers.append(", ");
                }
            chamber.setText(chambers.toString());

                appointment.setOnClickListener(v -> {
                    Intent intent= new Intent(context, Doctor_Appoinment.class);
                    context.startActivity(intent);
                });
        }
    }
}
