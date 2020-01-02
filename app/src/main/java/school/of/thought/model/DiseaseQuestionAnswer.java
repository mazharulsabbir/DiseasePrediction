package school.of.thought.model;

import java.util.List;

public class DiseaseQuestionAnswer {
    private Question question;
    private List<String> answers;
    private boolean isAnswered;
    private String answer;

    public DiseaseQuestionAnswer(Question question, List<String> answers, boolean isAnswered) {
        this.question = question;
        this.answers = answers;
        this.isAnswered = isAnswered;
    }

    public Question getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
