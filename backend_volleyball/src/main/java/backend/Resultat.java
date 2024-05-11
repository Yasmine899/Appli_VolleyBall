package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Resultat {
 
    /* noteQuiz */
    int noteQuiz(int IdResultats) throws SQLException{
        int pointQCM=0;
        int pointNQCM=0;
       
        int idquiz=getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> questions=quizzs.getIDQuestionbyQuizz(idquiz);
        ArrayList<ArrayList<String>> reponses=getIdReponseQuizbyIdQuizz(IdResultats);

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
            //a lier avec bdd
            pointQuestion=noteQuestion(currentQuestion,currentreponse);
            if(pointQuestion>0){
                note+=pointQuestion;
            }
        }
        return note;
    }


    /*recuperer les donnes de bdd */
    //IdReponseQuiz,idQuestion->les reponses d'une question 
    public ArrayList<String> getReponseOfQuestion(int IdReponseQuiz,int idQuestion) throws SQLException{
        ArrayList<String> reponses = new ArrayList<>();
        int idReponsesQuestion = 0;
        String sql1 = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";
        String sql2 = "SELECT reponse FROM reponse_question WHERE IdReponsesQuestion = ?";
    
        try (
            Connection connection = connectMysql.getConnection();  // Utilise une seule connexion pour les deux requêtes
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            PreparedStatement statement2 = connection.prepareStatement(sql2)
        ) {
            //  recuperer IdReponsesQuestion
            statement1.setInt(1, IdReponseQuiz);
            statement1.setInt(2, idQuestion);
            try (ResultSet resultSet1 = statement1.executeQuery()) {
                if (resultSet1.next()) {
                    idReponsesQuestion = resultSet1.getInt("IdReponsesQuestion");
                }
                //  recuperer les réponses
                if (idReponsesQuestion != 0) {
                    statement2.setInt(1, idReponsesQuestion);
                    ResultSet resultSet2 = statement2.executeQuery() ;
                    while (resultSet2.next()) {
                        String reponse = resultSet2.getString("reponse");
                        reponses.add(reponse);
                    }
                    
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return reponses;
    }
      
    //IdResultats->les reponses d'un quiz 
    public ArrayList<ArrayList<String>> getIdReponseQuizbyIdQuizz(int IdResultats) throws SQLException{
        int idQuizz=getIdquizzByIdresultats(IdResultats);
        int IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
        ArrayList<ArrayList<String>> ReponseQuiz=new ArrayList<>();
        ArrayList<String> reponse_question=new ArrayList<>();

        ArrayList<Integer> idQuestions=quizzs.getIDQuestionbyQuizz(idQuizz);
        for(int question:idQuestions){
            reponse_question=getReponseOfQuestion(IdReponseQuiz,question);
            ReponseQuiz.add(reponse_question);
        }
        return ReponseQuiz;
    }

    //IdResultats -> IdQuizz
    public int getIdquizzByIdresultats(int IdResultats) throws SQLException {
        int idQuizz = 0; 
        String sql = "SELECT IdQuizz FROM resultats WHERE IdResultats = ?"; 
    
        try (
            Connection connection = connectMysql.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdResultats); 
            ResultSet resultSet = statement.executeQuery();  
    
            if (resultSet.next()) {  
                idQuizz = resultSet.getInt("IdQuizz"); 
            }
        } catch (SQLException e) {
            System.out.println("Error in database operation: " + e.getMessage());
            e.printStackTrace();
            throw e;  
        }
    
        return idQuizz;  
    }

    //IdResultats -> IdReponseQuiz
    public int getIdReponseQuizByIdresultats(int IdResultats) throws SQLException {
        int IdReponseQuiz = 0; 
        String sql = "SELECT IdReponseQuiz FROM resultats WHERE IdResultats = ?"; 
    
        try (
            Connection connection = connectMysql.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdResultats); 
            ResultSet resultSet = statement.executeQuery();  
    
            if (resultSet.next()) {  
                IdReponseQuiz = resultSet.getInt("IdQuizz"); 
            }
        } catch (SQLException e) {
            System.out.println("Error in database operation: " + e.getMessage());
            e.printStackTrace();
            throw e;  
        }
    
        return IdReponseQuiz;  
    }

    
    

}