package school.of.thought.fragments.disease;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import school.of.thought.model.Dengue;
import school.of.thought.model.DoctorRegistrationModel;
import school.of.thought.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProbabilityResultFragment extends Fragment {
    private static final String TAG = "ProbabilityResult";
    private Dengue dengue;

    public static ProbabilityResultFragment newInstance(Dengue dengue) {
        ProbabilityResultFragment fragment = new ProbabilityResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Utils.DENGUE, dengue);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dengue = getArguments().getParcelable(Utils.DENGUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_probability_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView result = view.findViewById(R.id.result);
        String string;

        if (Integer.parseInt(dengue.getDengue()) == 0)
            string = "Negative";
        else string = "Positive";

        result.setText(string);

        doctorLists(view);
    }

    private void doctorLists(View rootView) {
        //todo: sort by suggested doctor
        RecyclerView recyclerView;
        List<DoctorRegistrationModel> doctorDetails = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Utils.DOCTORS_REF);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoctorRegistrationModel doctorHelper = snapshot.getValue(DoctorRegistrationModel.class);

                    if (doctorHelper != null) {
                        doctorDetails.add(doctorHelper);

                        DoctorListAdapter adapter = new DoctorListAdapter(doctorDetails, getContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }
}
