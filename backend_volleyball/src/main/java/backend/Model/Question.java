package backend.Model;

public class Question {

    private int questionId;
    private int chapitre;
    private String questionText;
    private int questionScore;

    public Question() {

    }

    public Question(int questionId, int chapitre, String questionText, int questionScore) {
        this.questionId = questionId;
        this.chapitre = chapitre;
        this.questionText = questionText;
        this.questionScore = questionScore;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChapitre() {
        return chapitre;
    }

    public void setChapitre(int chapitre) {
        this.chapitre = chapitre;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }


}
