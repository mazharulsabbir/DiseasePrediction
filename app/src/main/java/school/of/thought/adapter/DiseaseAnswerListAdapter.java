package school.of.thought.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.utils.Utils;

public class DiseaseAnswerListAdapter extends RecyclerView.Adapter<DiseaseAnswerListAdapter.AnswerHolder> {
    private static final String TAG = "DiseaseAnswerListAdapte";
    private List<DiseaseQuestionAnswer> questions;
    private Context context;

    public DiseaseAnswerListAdapter(List<DiseaseQuestionAnswer> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        return new AnswerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {

        DiseaseQuestionAnswer question = questions.get(position);

        holder.quesNo.setText(String.valueOf(position + 1));

        holder.ques.setText(question.getQuestion().getQuestion());

        switch (holder.getItemViewType()) {
            case Utils.QUES_TYPE_DROPDOWN:

                if (question.getAnswers() == null)
                    return;

                String[] strings = question.getAnswers().toArray(new String[0]);

                ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, strings);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                holder.spinnerAns.setAdapter(aa);
                break;

            case Utils.QUES_TYPE_RADIO_GROUP:
                if (question.getAnswers() == null)
                    return;

                holder.yes.setText(question.getAnswers().get(0));
                holder.no.setText(question.getAnswers().get(1));

                Log.d(TAG, "onBindViewHolder: " + holder.radioGroupAns.getCheckedRadioButtonId());
                break;
            case Utils.QUES_TYPE_TEXT:
                if (question.getAnswers() == null)
                    return;

                holder.editTextAns.setHint("Your ans..");
                break;
        }
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

    public class AnswerHolder extends RecyclerView.ViewHolder {
        private TextView quesNo;
        private TextView ques;

        private EditText editTextAns;

        private RadioGroup radioGroupAns;

        private RadioButton yes, no;

        private Spinner spinnerAns;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);

            quesNo = itemView.findViewById(R.id.text_view_1);
            ques = itemView.findViewById(R.id.text_view_ques);

            editTextAns = itemView.findViewById(R.id.ques_ans_edit_text);

            radioGroupAns = itemView.findViewById(R.id.ques_ans_radio_group);

            yes = itemView.findViewById(R.id.radio_button_yes);
            no = itemView.findViewById(R.id.radio_button_no);

            spinnerAns = itemView.findViewById(R.id.ques_ans_dropdown_list);
        }
    }
}
