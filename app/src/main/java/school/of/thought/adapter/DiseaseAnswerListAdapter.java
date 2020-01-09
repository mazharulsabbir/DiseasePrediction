package school.of.thought.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.utils.DiseaseAnswerItemClickListener;
import school.of.thought.utils.Utils;

public class DiseaseAnswerListAdapter extends RecyclerView.Adapter<AnswerViewHolder> {
    private static final String TAG = "DiseaseAnswerAdapter";
    private List<DiseaseQuestionAnswer> questions;
    private Context context;

    private DiseaseAnswerItemClickListener diseaseAnswerItemClickListener;

    public DiseaseAnswerListAdapter(List<DiseaseQuestionAnswer> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    public void setDiseaseAnswerItemClickListener(DiseaseAnswerItemClickListener diseaseAnswerItemClickListener) {
        this.diseaseAnswerItemClickListener = diseaseAnswerItemClickListener;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == Utils.QUES_TYPE_DROPDOWN) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_disease_ques_type_dropdown, parent, false);
        } else if (viewType == Utils.QUES_TYPE_RADIO_GROUP) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_disease_ques_type_radio_button, parent, false);
        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_disease_ques_type_text, parent, false);
        }

        return new AnswerViewHolder(view, diseaseAnswerItemClickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        DiseaseQuestionAnswer diseaseQuestionAnswer = questions.get(position);
        Log.d(TAG, "onBindViewHolder: " + diseaseQuestionAnswer);
        holder.bind(diseaseQuestionAnswer);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (questions.get(position).getQuestion().getQuestion_type()
                .equals(Utils.TYPE_DROPDOWN)) {

            return Utils.QUES_TYPE_DROPDOWN;

        } else if (questions.get(position).getQuestion().getQuestion_type()
                .equals(Utils.TYPE_RADIO_GROUP)) {

            return Utils.QUES_TYPE_RADIO_GROUP;

        } else {
            return Utils.QUES_TYPE_TEXT;
        }
    }

}
