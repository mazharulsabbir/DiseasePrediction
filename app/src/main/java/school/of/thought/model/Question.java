package school.of.thought.model;

public class Question {
    private String image,question,question_type;

    public Question() {
    }

    public Question(String image, String question, String question_type) {
        this.image = image;
        this.question = question;
        this.question_type = question_type;
    }

    public String getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestion_type() {
        return question_type;
    }
}
