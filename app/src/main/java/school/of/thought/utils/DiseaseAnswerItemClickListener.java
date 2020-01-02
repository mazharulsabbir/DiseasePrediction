package school.of.thought.utils;

import java.util.List;

import school.of.thought.model.DiseaseQuestionAnswer;

public interface DiseaseAnswerItemClickListener {
    void onAnswerChange(int pos, List<DiseaseQuestionAnswer> answerList);
}
