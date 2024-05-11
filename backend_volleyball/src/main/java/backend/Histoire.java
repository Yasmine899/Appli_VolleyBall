package backend;

import java.util.ArrayList;

public class Histoire {
    private int idhistoire;
    private int idquiz;
    private int idreponse;
    public Histoire(int idhistoire,int idquiz,int idreponse){
        this.idhistoire=idhistoire;
        this.idquiz=idquiz;
        this.idreponse=idreponse;
    }
    public int getIdquiz() {
        return idquiz;
    }
    public int getIdreponse() {
        return idreponse;
    }
    public ArrayList<ArrayList<String>> getreponses(){
        return null;
        //lier au bdd
    }

  
    public ArrayList<Integer> getIdQuestions(){
         //lier au bdd
        return null;
    }

}
