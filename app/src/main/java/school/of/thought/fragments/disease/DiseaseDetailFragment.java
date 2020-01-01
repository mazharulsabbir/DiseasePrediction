package school.of.thought.fragments.disease;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import school.of.thought.adapter.DiseaseAnswerListAdapter;
import school.of.thought.model.Disease;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.model.Question;
import school.of.thought.utils.Utils;


public class DiseaseDetailFragment extends Fragment {
    private static final String TAG = "DiseaseDetailFragment";

    private Disease disease;
    private Toast toast;

    public static DiseaseDetailFragment newInstance(Disease disease) {
        DiseaseDetailFragment fragment = new DiseaseDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Utils.DISEASE_NAME, disease);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            disease = getArguments().getParcelable(Utils.DISEASE_NAME);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disease_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView diseaseDesc = view.findViewById(R.id.disease_desc);

        if (disease != null) {
            diseaseDesc.setText(disease.getDescription());
        }

        List<DiseaseQuestionAnswer> diseaseQuestionAnswerList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.ques_list_with_ans);

        DiseaseAnswerListAdapter diseaseAnswerListAdapter = new DiseaseAnswerListAdapter(diseaseQuestionAnswerList, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(diseaseAnswerListAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Utils.getQuesListOf(disease.getName()));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Question question = snapshot.getValue(Question.class);

                        List<String> answers = new ArrayList<>();

                        for (DataSnapshot answerSnapshot : snapshot.child("answer").getChildren()) {
                            String s = answerSnapshot.getValue(String.class);
                            answers.add(s);
                        }

                        if (question != null) {
                            diseaseQuestionAnswerList.add(new DiseaseQuestionAnswer(question, answers));
                        }
                    }

                    diseaseAnswerListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });

    }

}
