package backend;

import java.util.ArrayList;

public class Histoire {
    private int idquiz;
    private int idreponse;
    public Histoire(int idquiz,int idreponse){
        this.idquiz=idquiz;
        this.idreponse=idreponse;
    }
    public int getIdquiz() {
        return idquiz;
    }
    public int getIdreponse() {
        return idreponse;
    }
    public ArrayList<Reponse> getreponses(){
        return null;
        //lier au bdd
    }

    //a modifier 
    public ArrayList<Question> getIdQuestions(){
        return null;
    }

}
