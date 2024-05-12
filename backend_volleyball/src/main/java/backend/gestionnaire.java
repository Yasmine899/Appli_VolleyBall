package backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class gestionnaire {
   
    public void updatePointNQCM(int idQuestion, int IdResultats,int newpoint) throws SQLException {

        int IdReponseQuiz=Resultat.getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> list_IdReponseQuestion= Resultat.getIdReponseQuestion(IdReponseQuiz,idQuestion);
        if(list_IdReponseQuestion.size()==1){
            Resultat.updatePoint(list_IdReponseQuestion.get(0), newpoint);
        }else{
            System.out.println("c'est une question QCM!!!!!");
        }
    }
        
}
