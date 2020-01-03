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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import school.of.thought.utils.DiseaseAnswerItemClickListener;
import school.of.thought.utils.Utils;


public class DiseaseQuestionsFragment extends Fragment implements DiseaseAnswerItemClickListener {
    private static final String TAG = "DiseaseQuestions";
    private List<DiseaseQuestionAnswer> diseaseQuestionAnswerList = new ArrayList<>();

    private RecyclerView recyclerView;

    private Disease disease;
    private FloatingActionButton submitAnswer;

    private DiseaseAnswerListAdapter diseaseAnswerListAdapter;

    private Toast toast;

    public static DiseaseQuestionsFragment newInstance(Disease disease) {
        DiseaseQuestionsFragment fragment = new DiseaseQuestionsFragment();

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
        return inflater.inflate(R.layout.fragment_disease_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView diseaseDesc = view.findViewById(R.id.disease_desc);
        submitAnswer = view.findViewById(R.id.submit_answer);

        if (disease != null) {
            diseaseDesc.setText(disease.getDescription());
        }

        submitAnswer.setOnClickListener(v -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < diseaseQuestionAnswerList.size(); i++) {
                stringBuilder.append(diseaseQuestionAnswerList.get(i).getAnswer()).append("\n");
            }

            Toast.makeText(getContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        });

        recyclerView = view.findViewById(R.id.ques_list_with_ans);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        diseaseAnswerListAdapter = new DiseaseAnswerListAdapter(diseaseQuestionAnswerList, getContext());

        recyclerView.setAdapter(diseaseAnswerListAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Utils.getQuesListOf(disease.getName()));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            diseaseQuestionAnswerList.add(new DiseaseQuestionAnswer(question, answers, false, ""));
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

        diseaseAnswerListAdapter.setDiseaseAnswerItemClickListener(this);

    }

    @Override
    public void onAnswerChange(int p, String s) {
        Log.d(TAG, "onAnswerChange: Position: " + p + ", Value: " + s);

        if (!s.isEmpty()) {
            diseaseQuestionAnswerList.get(p).setAnswered(true);
            diseaseQuestionAnswerList.get(p).setAnswer(s);
        }

        boolean valid = true;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < diseaseQuestionAnswerList.size(); i++) {
            if (!diseaseQuestionAnswerList.get(i).isAnswered()) {
                valid = false;
                break;
            }

            builder.append(diseaseQuestionAnswerList.get(i).isAnswered()).append(i).append("\n");
        }

        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(getContext(), builder.toString(), Toast.LENGTH_LONG);
        toast.show();

        if (valid) {
            submitAnswer.setVisibility(View.VISIBLE);
        } else submitAnswer.setVisibility(View.GONE);
    }
}
