package school.of.thought.fragments.doctors;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import school.of.thought.R;
import school.of.thought.adapter.DoctorListAdapter;
import school.of.thought.model.DoctorChamberListModel;
import school.of.thought.model.DoctorRegistrationModel;
import school.of.thought.utils.Utils;


public class DoctorListFragment extends Fragment {


    private View rootView;
    private RecyclerView recyclerView;
    private List<DoctorRegistrationModel> doctorDetails = new ArrayList<>();
    private List<DoctorChamberListModel> doctorChamber;


    public DoctorListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeData();


        return rootView;
    }

    private void initializeData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Utils.DOCTORS_REF);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoctorRegistrationModel doctorHelper = snapshot.getValue(DoctorRegistrationModel.class);

                    if (doctorHelper != null) {
                        doctorDetails.add(doctorHelper);

                        DoctorListAdapter adapter = new DoctorListAdapter(doctorDetails);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
