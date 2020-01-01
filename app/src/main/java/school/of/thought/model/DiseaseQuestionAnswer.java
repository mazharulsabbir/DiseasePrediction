package school.of.thought.model;

import java.util.List;

public class DiseaseQuestionAnswer {
    private Question question;
    private List<String> answers;

    public DiseaseQuestionAnswer(Question question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Question getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
