package backend.Model;

public class Reponse {

    private int reponseId;
    private int questionId;
    private String reponseText;
    private boolean reponseCorrecte;

    public Reponse() {

    }

    public Reponse(int reponseId, int questionId, String reponseText, boolean reponseCorrecte) {
        this.reponseId = reponseId;
        this.questionId = questionId;
        this.reponseText = reponseText;
        this.reponseCorrecte = reponseCorrecte;
    }

    public int getReponseId() {
        return reponseId;
    }

    public void setReponseId(int reponseId) {
        this.reponseId = reponseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getReponseText() {
        return reponseText;
    }

    public void setReponseText(String reponseText) {
        this.reponseText = reponseText;
    }

    public boolean isReponseCorrecte() {
        return reponseCorrecte;
    }

    public void setReponseCorrecte(boolean reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }
}
