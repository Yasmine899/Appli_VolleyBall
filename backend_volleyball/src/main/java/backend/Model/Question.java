package backend.Model;

public class Question {

    private int questionId;
    private int chapitre;
    private String questionText;
    private int questionScore;
    private String referenceID;



    public Question() {

    }

    public Question(int questionId, int chapitre, String questionText, int questionScore, String referenceID) {
        this.questionId = questionId;
        this.chapitre = chapitre;
        this.questionText = questionText;
        this.questionScore = questionScore;
        this.referenceID = referenceID;
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

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }
}
