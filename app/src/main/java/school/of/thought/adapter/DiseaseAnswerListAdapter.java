package school.of.thought.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.utils.DiseaseAnswerItemClickListener;
import school.of.thought.utils.Utils;

public class DiseaseAnswerListAdapter extends RecyclerView.Adapter<DiseaseAnswerListAdapter.AnswerHolder> {
    private static final String TAG = "DiseaseAnswerAdapter";
    private List<DiseaseQuestionAnswer> questions;
    private Context context;

    private DiseaseAnswerItemClickListener diseaseAnswerItemClickListener;

    public DiseaseAnswerListAdapter(List<DiseaseQuestionAnswer> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setDiseaseAnswerItemClickListener(DiseaseAnswerItemClickListener diseaseAnswerItemClickListener) {
        this.diseaseAnswerItemClickListener = diseaseAnswerItemClickListener;
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
        DiseaseQuestionAnswer diseaseQuestionAnswer = questions.get(position);
        Log.d(TAG, "onBindViewHolder: " + diseaseQuestionAnswer);

        holder.quesNo.setText(String.valueOf(position + 1));

        holder.ques.setText(diseaseQuestionAnswer.getQuestion().getQuestion());

        if (holder.getItemViewType() == Utils.QUES_TYPE_DROPDOWN) {
            String[] strings = diseaseQuestionAnswer.getAnswers().toArray(new String[0]);

            ArrayAdapter<String> aa = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            holder.spinnerAns.setAdapter(aa);

            if (diseaseQuestionAnswer.isAnswered()) {
                String value = diseaseQuestionAnswer.getAnswer();
                int pos = 0;

                for (int i = 0; i < strings.length; i++) {
                    if (value.equals(strings[i])) break;
                    pos = i;
                }

                holder.spinnerAns.setSelection(pos);
            }

            holder.spinnerAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    diseaseQuestionAnswer.setAnswered(true);
                    diseaseQuestionAnswer.setAnswer(holder.spinnerAns.getSelectedItem().toString());

                    setClickListener(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    diseaseQuestionAnswer.setAnswered(true);
                    diseaseQuestionAnswer.setAnswer(holder.spinnerAns.getSelectedItem().toString());
                }
            });
        }
        else if (holder.getItemViewType() == Utils.QUES_TYPE_RADIO_GROUP) {
            holder.yes.setText(diseaseQuestionAnswer.getAnswers().get(0));
            holder.no.setText(diseaseQuestionAnswer.getAnswers().get(1));

            holder.radioGroupAns.setOnCheckedChangeListener((radioGroup, i) -> {
                Toast.makeText(context, i + "RG " + position, Toast.LENGTH_SHORT).show();

                switch (i) {
                    case R.id.radio_button_yes:
                        diseaseQuestionAnswer.setAnswered(true);
                        diseaseQuestionAnswer.setAnswer("1");
                        setClickListener(position);
                        break;

                    case R.id.radio_button_no:
                        diseaseQuestionAnswer.setAnswered(true);
                        diseaseQuestionAnswer.setAnswer("0");
                        setClickListener(position);
                        break;
                }
            });

            if (diseaseQuestionAnswer.isAnswered()) {
                switch (diseaseQuestionAnswer.getAnswer()) {
                    case "0":
                        holder.radioGroupAns.check(holder.no.getId());
                        break;

                    case "1":
                        holder.radioGroupAns.check(holder.yes.getId());
                        break;
                }
            }

            Log.d(TAG, "onBindViewHolder: " + holder.radioGroupAns.getCheckedRadioButtonId());
        } else {
            if (diseaseQuestionAnswer.isAnswered()) {
                holder.editTextAns.getEditText().setText(diseaseQuestionAnswer.getAnswer());
            }

            holder.editTextAns.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    diseaseQuestionAnswer.setAnswer(editable.toString());

                    if (!editable.toString().isEmpty()) {
                        diseaseQuestionAnswer.setAnswered(true);
                    } else {
                        diseaseQuestionAnswer.setAnswered(false);
                    }
                }
            });

            holder.editTextAns.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {
                Toast.makeText(context, "ET " + position, Toast.LENGTH_SHORT).show();

                if (i == EditorInfo.IME_ACTION_NEXT) {
                    hideKeyboardFrom(context, holder.editTextAns);
                    setClickListener(position);
                    return true;
                }
                return false;
            });
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

    private void setClickListener(int pos) {
        if (pos != RecyclerView.NO_POSITION) {
            if (diseaseAnswerItemClickListener != null) {
                diseaseAnswerItemClickListener.onAnswerChange(pos, questions);
            }
        }
    }

    public class AnswerHolder extends RecyclerView.ViewHolder {
        private TextView quesNo;
        private TextView ques;

        private TextInputLayout editTextAns;

        private RadioGroup radioGroupAns;

        private RadioButton yes, no;

        private Spinner spinnerAns;

        @SuppressLint("ResourceType")
        private AnswerHolder(@NonNull View itemView) {
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
