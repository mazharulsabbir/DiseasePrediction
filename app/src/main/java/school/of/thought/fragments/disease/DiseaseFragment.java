package school.of.thought.fragments.disease;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import school.of.thought.R;
import school.of.thought.activity.DiseaseQuestionsAnswering;
import school.of.thought.activity.LoginRegistrationHolder;
import school.of.thought.adapter.DiseaseListAdapter;
import school.of.thought.model.Disease;
import school.of.thought.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseFragment extends Fragment {

    private static final String TAG = "DiseaseFragment";
    private List<Disease> diseases = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disease, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDiseaseListData(view);
    }

    private void initDiseaseListData(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        RecyclerView recyclerView = v.findViewById(R.id.disease_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DiseaseListAdapter diseaseListAdapter = new DiseaseListAdapter(diseases, getContext());

        recyclerView.setAdapter(diseaseListAdapter);

        diseaseListAdapter.setOnItemClickListener(p -> {
            Log.d(TAG, "initDiseaseListData: Name: " + diseases.get(p).getName());
            Log.d(TAG, "initDiseaseListData: Desc: " + diseases.get(p).getDescription());

            if (user != null) {
                Intent intent = new Intent(getContext(), DiseaseQuestionsAnswering.class);
                intent.putExtra(Utils.DISEASE_NAME, diseases.get(p));
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), LoginRegistrationHolder.class);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(Utils.DISEASE_LIST).keepSynced(true);

        reference.child(Utils.DISEASE_LIST)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange() returned: " + dataSnapshot.getChildrenCount());

                        if (dataSnapshot.hasChildren()) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Disease disease = snapshot.getValue(Disease.class);

                                if (disease != null) {
                                    diseases.add(disease);
                                } else Log.d(TAG, "onDataChange: " + "null");
                            }

                            diseaseListAdapter.notifyDataSetChanged();

                        } else Log.d(TAG, "onDataChange: " + "no data");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                    }
                });
    }

}
