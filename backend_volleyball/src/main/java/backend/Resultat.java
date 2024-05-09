package backend;

import java.util.ArrayList;
import backend.Histoire;

public class Resultat {
    private int idPerson;
    private Histoire histoire;
    
    public Resultat(int idPerson, Histoire histoire){
        this.idPerson=idPerson;
        this.histoire=histoire;
    }
   
    //renvoyer la note en donnant un list de reponse
    int noteQuiz(){
        ArrayList<Question> quiz=this.histoire.getIdQuestions();
        ArrayList<Reponse> reponses=this.histoire.getreponses();
        int note=0;
        Question currentQuestion;
        Reponse currentreponse;
        for(int i=0;i< quiz.size();i++){
            currentQuestion=quiz.get(i);
            currentreponse=reponses.get(i);
            //a modifier
            if(currentreponse.estCorrecte()){
                note+=currentQuestion.getScore();
            }

        }
        return note;
    
    }





}