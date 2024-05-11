package backend;

import java.util.ArrayList;
import java.util.List;


public class Resultat {
    private int idPerson;
    private Histoire histoire;
    
    public Resultat(int idPerson, Histoire histoire){
        this.idPerson=idPerson;
        this.histoire=histoire;
    }
   
    /* noteQuiz*/
    int noteQuiz(){
        int pointQCM=0;
        int pointNQCM=0;
        ArrayList<Integer> questions=this.histoire.getIdQuestions();
        ArrayList<ArrayList<String>> reponses=this.histoire.getreponses();
        ArrayList<Integer> QuestionQCM=new ArrayList();
        ArrayList<Integer> QuestionNQCM=new ArrayList();
        ArrayList<ArrayList<String>> ReponseQCM=new ArrayList();
        ArrayList<ArrayList<String>> ReponseNQCM=new ArrayList();
        
        for(int i=0;i<questions.size();i++){
            int idQuestion=questions.get(i);
            ArrayList<String> reponse=reponses.get(i);
            //on verifier si cette question a des choix pour identifier si c'est QCM ou pas
            if( Reponse.getReponsesByQuestionId(idQuestion).size() >0 ){
                QuestionQCM.add(idQuestion);
                ReponseQCM.add(reponse);
            }else{
                QuestionNQCM.add(idQuestion);
                ReponseNQCM.add(reponse);
            }
        }
        pointQCM=noteQCM(QuestionQCM, ReponseQCM);
        pointNQCM=noteNQCM(QuestionNQCM, ReponseNQCM);

        return pointQCM+pointNQCM;
    }

    /* Partie QCM */
    //renvoyer la note d'un quiz de partie QCM
    int noteQCM(ArrayList<Integer> idQuestions,ArrayList<ArrayList<String>> reponses ){
        int note=0;
        int pointQuestion=0;
        int currentQuestion;
        ArrayList<String> currentreponse;
        for(int i=0;i< idQuestions.size();i++){
            currentQuestion=idQuestions.get(i);
            currentreponse=reponses.get(i);
            pointQuestion=noteQuestion(currentQuestion,currentreponse);
            if(pointQuestion>0){
                note+=pointQuestion;
            }
        }
        return note;
    }

    //renvoyer la note d'une question
    public int noteQuestion(int idQuestion, ArrayList<String> reponsesUtilisateur) {
        List<Reponse> reponsesPossibles=Reponse.getReponsesByQuestionId(idQuestion);
        int correct = 0;
        int full=reponsesPossibles.size();

        for(String reponse:reponsesUtilisateur){
            for(Reponse reponseQuestion:reponsesPossibles){
                //si reponse de l'utilisateur = choix  et  ce chois est correct
                if(reponseQuestion.getReponseText().equals(reponse)&& reponseQuestion.estCorrecte()){
                    correct++;
                }
            }
        }
        return correct/full;
    }

    /* Partie Non-QCM */
    int noteNQCM(ArrayList<Integer> idQuestions,ArrayList<ArrayList<String>> reponses){
        int note=0;
        int pointQuestion=0;
        int currentQuestion;
        ArrayList<String> currentreponse;
        for(int i=0;i< idQuestions.size();i++){
            currentQuestion=idQuestions.get(i);
            currentreponse=reponses.get(i);
            pointQuestion=noteQuestion(currentQuestion,currentreponse);
            if(pointQuestion>0){
                note+=pointQuestion;
            }
        }
        return note;
    }

}