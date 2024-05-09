package backend;

public class Reponse {
    private int idReponse;
    private String libelle;
    private boolean estCorrecte;

    public Reponse(int idReponse, String libelle, boolean estCorrecte) {
        this.idReponse = idReponse;
        this.libelle = libelle;
        this.estCorrecte = estCorrecte;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public String getLibelle() {
        return libelle;
    }

    public boolean estCorrecte() {
        return estCorrecte;
    }
}