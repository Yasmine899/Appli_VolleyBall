package backend;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int idQuestion;
    private String enonce;
    private String chapitre;
    private List<Reponse> reponsesPossibles;
    private int score; // Nouvel attribut score

    public Question(int idQuestion, String enonce, String chapitre, int score) {
        this.idQuestion = idQuestion;
        this.enonce = enonce;
        this.chapitre = chapitre;
        this.reponsesPossibles = new ArrayList<>();
        this.score = score;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getEnonce() {
        return enonce;
    }

    public String getChapitre() {
        return chapitre;
    }

    public List<Reponse> getReponsesPossibles() {
        return reponsesPossibles;
    }

    public void ajouterReponse(Reponse reponse) {
        reponsesPossibles.add(reponse);
    }

    public int getScore() {
        return score;
    }

    public int verifierReponse(List<Integer> reponsesUtilisateur) {
        int points = 0;
        for (Integer indexReponse : reponsesUtilisateur) {
            if (indexReponse >= 0 && indexReponse < reponsesPossibles.size()) {
                Reponse reponse = reponsesPossibles.get(indexReponse);
                if (reponse.estCorrecte()) {
                    points += score; // Incrémenter les points avec le score de la question
                }
            }
        }
        return points;
    }
}
