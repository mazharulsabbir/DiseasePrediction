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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import school.of.thought.R;
import school.of.thought.activity.DiseaseQuestionsAnswering;
import school.of.thought.adapter.DiseaseAnswerListAdapter;
import school.of.thought.model.Dengue;
import school.of.thought.model.Disease;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.model.Question;
import school.of.thought.utils.DiseaseAPI;
import school.of.thought.utils.DiseaseAnswerItemClickListener;
import school.of.thought.utils.RetrofitClient;
import school.of.thought.utils.Utils;


public class DiseaseQuestionsFragment extends Fragment implements DiseaseAnswerItemClickListener {
    private static final String TAG = "DiseaseQuestions";
    private List<DiseaseQuestionAnswer> diseaseQuestionAnswerList = new ArrayList<>();

    private RecyclerView recyclerView;

    private Disease disease;
    private FloatingActionButton submitAnswer;

    private DiseaseAnswerListAdapter diseaseAnswerListAdapter;

    private CompositeDisposable disposable = new CompositeDisposable();

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

        submitAnswer.setOnClickListener(
                view1 -> submitResult()
        );

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
    public void onAnswerChange(int p, String s, String type) {
        Log.d(TAG, "onAnswerChange: Position: " + p + ", Value: " + s);

        scrollTo(p);

        if (!s.isEmpty()) {
            diseaseQuestionAnswerList.get(p).setAnswered(true);
            diseaseQuestionAnswerList.get(p).setAnswer(s);
        } else {
            diseaseQuestionAnswerList.get(p).setAnswered(false);
            diseaseQuestionAnswerList.get(p).setAnswer("");
        }

        boolean valid = true;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < diseaseQuestionAnswerList.size(); i++) {
            if (!diseaseQuestionAnswerList.get(i).isAnswered()) {
                valid = false;
                break;
            }

            builder.append(diseaseQuestionAnswerList.get(i).isAnswered()).append(i).append(": ").append(s).append("\n");
        }

        Log.d(TAG, "onAnswerChange: " + builder.toString());

        if (valid) {
            submitAnswer.setVisibility(View.VISIBLE);
        } else submitAnswer.setVisibility(View.GONE);

    }

    private void scrollTo(int p) {
        p+=1;
        recyclerView.smoothScrollToPosition(p);
    }

    private void submitResult() {

        Retrofit client = RetrofitClient.get();
        DiseaseAPI diseaseAPI = client.create(DiseaseAPI.class);

        try {
            Map<String, String> post = new HashMap<>();
            post.put("age", diseaseQuestionAnswerList.get(0).getAnswer());
            post.put("gender", diseaseQuestionAnswerList.get(5).getAnswer());
            post.put("days", diseaseQuestionAnswerList.get(4).getAnswer());
            post.put("high_fever", diseaseQuestionAnswerList.get(6).getAnswer());
            post.put("rash", diseaseQuestionAnswerList.get(7).getAnswer());
            post.put("muscle_pain", diseaseQuestionAnswerList.get(8).getAnswer());
            post.put("joint_pain", diseaseQuestionAnswerList.get(9).getAnswer());
            post.put("blooding", diseaseQuestionAnswerList.get(10).getAnswer());
            post.put("vomiting", diseaseQuestionAnswerList.get(11).getAnswer());
            post.put("Severe_headache", diseaseQuestionAnswerList.get(1).getAnswer());
            post.put("pain_behind_eyes", diseaseQuestionAnswerList.get(2).getAnswer());
            post.put("swollen_gland", diseaseQuestionAnswerList.get(3).getAnswer());

            Dengue dengue = new Dengue(
                    post.get("age"),
                    post.get("gender"),
                    post.get("days"),
                    post.get("high_fever"),
                    post.get("rash"),
                    post.get("muscle_pain"),
                    post.get("joint_pain"),
                    post.get("blooding"),
                    post.get("vomiting"),
                    post.get("Severe_headache"),
                    post.get("pain_behind_eyes"),
                    post.get("swollen_gland")
            );

            disposable.add(
                    diseaseAPI.submitAnswers(dengue).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::showResult, this::showException)
            );
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void showResult(Dengue dengue) {
        ((DiseaseQuestionsAnswering) getActivity()).openFragment(ProbabilityResultFragment.newInstance(dengue));
    }

    private void showException(Throwable t) {
        Log.e(TAG, "submitResult: ", t);
        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
