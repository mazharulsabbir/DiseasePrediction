package school.of.thought.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import school.of.thought.R;
import school.of.thought.model.DiseaseQuestionAnswer;
import school.of.thought.utils.DiseaseAnswerItemClickListener;
import school.of.thought.utils.Utils;

public class AnswerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "AnswerViewHolder";
    private TextView quesNo;
    private TextView ques;
    private Context context;
    private TextInputLayout editTextAns;
    private RadioGroup radioGroupAns;
    private RadioButton yes, no;
    private Spinner spinnerAns;
    private DiseaseAnswerItemClickListener diseaseAnswerItemClickListener;

    private DiseaseQuestionAnswer diseaseQuestionAnswer;

    public AnswerViewHolder(@NonNull View itemView, DiseaseAnswerItemClickListener diseaseAnswerItemClickListener, Context context) {
        super(itemView);
        this.diseaseAnswerItemClickListener = diseaseAnswerItemClickListener;
        this.context = context;

        quesNo = itemView.findViewById(R.id.text_view_1);
        ques = itemView.findViewById(R.id.text_view_ques);

        editTextAns = itemView.findViewById(R.id.ques_ans_edit_text);

        radioGroupAns = itemView.findViewById(R.id.ques_ans_radio_group);

        yes = itemView.findViewById(R.id.radio_button_yes);
        no = itemView.findViewById(R.id.radio_button_no);

        spinnerAns = itemView.findViewById(R.id.ques_ans_dropdown_list);
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void bind(DiseaseQuestionAnswer diseaseQuestionAnswer) {
        this.diseaseQuestionAnswer = diseaseQuestionAnswer;
        quesNo.setText(String.valueOf(getAdapterPosition() + 1));

        ques.setText(diseaseQuestionAnswer.getQuestion().getQuestion());

        switch (diseaseQuestionAnswer.getQuestion().getQuestion_type()) {
            case Utils.TYPE_DROPDOWN:
                String[] strings = diseaseQuestionAnswer.getAnswers().toArray(new String[0]);

                ArrayAdapter<String> aa = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerAns.setAdapter(aa);

                if (diseaseQuestionAnswer.isAnswered()) {
                    String value = diseaseQuestionAnswer.getAnswer();
                    int pos = 0;

                    for (int i = 0; i < strings.length; i++) {
                        if (value.equals(strings[i])) break;
                        pos = i;
                    }

                    spinnerAns.setSelection(pos);
                }

                spinnerAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        diseaseQuestionAnswer.setAnswered(true);
                        diseaseQuestionAnswer.setAnswer(spinnerAns.getSelectedItem().toString());
                        clickListener(spinnerAns.getSelectedItem().toString(), Utils.TYPE_DROPDOWN);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        diseaseQuestionAnswer.setAnswered(true);
                        diseaseQuestionAnswer.setAnswer(spinnerAns.getSelectedItem().toString());
                    }
                });
                Log.d(TAG, "onBindViewHolder: " + spinnerAns.getSelectedItem().toString());

                break;
            case Utils.TYPE_RADIO_GROUP:
                yes.setText(diseaseQuestionAnswer.getAnswers().get(0));
                no.setText(diseaseQuestionAnswer.getAnswers().get(1));

                if (diseaseQuestionAnswer.isAnswered()) {
                    switch (diseaseQuestionAnswer.getAnswer()) {
                        case "0":
                            radioGroupAns.check(no.getId());
                            break;

                        case "1":
                            radioGroupAns.check(yes.getId());
                            break;
                    }
                } else {
                    radioGroupAns.clearCheck();
                }

                yes.setOnClickListener(view -> {
                    diseaseQuestionAnswer.setAnswered(true);
                    diseaseQuestionAnswer.setAnswer("1");
                    clickListener("1", Utils.TYPE_RADIO_GROUP);
                });

                no.setOnClickListener(view -> {
                    diseaseQuestionAnswer.setAnswered(true);
                    diseaseQuestionAnswer.setAnswer("0");
                    clickListener("0", Utils.TYPE_RADIO_GROUP);
                });

                Log.d(TAG, "onBindViewHolder: " + radioGroupAns.getCheckedRadioButtonId());
                break;
            case Utils.TYPE_TEXT:
                if (diseaseQuestionAnswer.isAnswered()) {
                    editTextAns.getEditText().setText(diseaseQuestionAnswer.getAnswer());
                } else editTextAns.getEditText().setText("");

                editTextAns.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {

                    if (i == EditorInfo.IME_ACTION_NEXT) {
                        hideKeyboardFrom(context, editTextAns);
                        clickListener(editTextAns.getEditText().getText().toString(), Utils.TYPE_TEXT);
                        return true;
                    }
                    return false;
                });

                editTextAns.getEditText().setOnFocusChangeListener((view, b) -> {
                    if (!b)
                        clickListener(editTextAns.getEditText().getText().toString(), Utils.TYPE_TEXT);
                });
                break;
        }
    }

    private void clickListener(String s, String type) {
        int pos = getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION) {
            if (diseaseAnswerItemClickListener != null) {
                diseaseAnswerItemClickListener.onAnswerChange(pos, s, type);
            }
        }

        if (!s.isEmpty()) {
            diseaseQuestionAnswer.setAnswer(s);
            diseaseQuestionAnswer.setAnswered(true);
        } else {
            diseaseQuestionAnswer.setAnswer("");
            diseaseQuestionAnswer.setAnswered(false);
        }
    }
}
