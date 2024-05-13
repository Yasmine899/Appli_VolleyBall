package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;


public class Resultat {
 
    /* 4 retourner noteQuiz */
    public int getNoteQuiz(int IdResultats) throws SQLException{
        int idquiz=getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> idQuestions=quizzs.getIDQuestionbyQuizz(idquiz);
        int note=0;
        int IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
        for(int idQuestion:idQuestions){
            note+=getPointOfQuestion(IdReponseQuiz, idQuestion);
        }
        return note;
    }

    //3. quand user a fini ce quiz,on va calculer la note dequiz 
    //evaluer les reponse de quiz et les stocker dans bdd
    public void verifierQuiz(int IdResultats) throws SQLException{
       
        int idquiz=getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> idQuestions=quizzs.getIDQuestionbyQuizz(idquiz);
        
        //evaluer les question
        for(int idQuestion:idQuestions){
            int IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
            verifierQuestionQCM(idQuestion,IdReponseQuiz);
        }
    }

 
    /*stocker les reponses de user dans bdd*/
     //1. quand user commence un entrainement,on ajouter les informations de cet entrainement dans resultats
    public int saveReponseQuiz(int IdQuizz,int IdPerson,Time date){
        int IdReponseQuiz=0;
        String insertSQL = "INSERT INTO resultats (IdQuizz, IdPerson, note, date) VALUES (?, ?, 0.0, ?)";
        String updateSQL = "UPDATE resultats SET IdReponseQuiz = IdResultats WHERE IdResultats = ?";
        try (
        Connection connection = connectMysql.getConnection();
        PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
        ) {
            insertStatement.setInt(1, IdQuizz);
            insertStatement.setInt(2, IdPerson);
            insertStatement.setTime(3, date);
        
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    IdReponseQuiz = generatedKeys.getInt("IdResultats");
                    updateStatement.setInt(1, IdReponseQuiz);
                    updateStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error during the insertion of the quiz response: " + e.getMessage());
            e.printStackTrace();
        }
        return IdReponseQuiz;
    }

    //2. user a fini une question,on va stocker ses reponses dans bdd et evaluer ses reponses
    public void saveReponsesQuestion(int IdReponseQuiz,int IdQuestion,ArrayList<String> ReponseQuiz ) throws SQLException{
        int IdReponsesQuestion =0;
        String sql = "INSERT INTO reponse_quiz (IdReponseQuiz, IdQuestion) VALUES (?, ?)";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql) 
        ) {
            statement.setInt(1, IdReponseQuiz);  
            statement.setInt(2, IdQuestion);  

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    IdReponsesQuestion = generatedKeys.getInt(1); // Get the generated key
                } 
            }
            
        } catch (SQLException e) {
            System.out.println("Database error during the insertion of the quiz response: " + e.getMessage());
            e.printStackTrace();
        }
        
        for(String reponse:ReponseQuiz){
            saveReponseQuestion(IdReponsesQuestion,reponse);
        }
        verifierQuestionQCM(IdQuestion,IdReponseQuiz);

    }
    
    public void saveReponseQuestion(int IdReponsesQuestion,String reponse){
        String sql = "INSERT INTO reponse_question (IdReponsesQuestion, reponse) VALUES (?, ?)";

        try (
            Connection connection = connectMysql.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql) 
        ) {
            statement.setInt(1, IdReponsesQuestion);  
            statement.setString(2, reponse);  
        } catch (SQLException e) {
            System.out.println("Database error during the insertion of the response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //evaluer les reponses d'une question et les stocker dans bdd
    public void verifierQuestionQCM(int idQuestion, int IdReponseQuiz) throws SQLException {
    //reponsesUtilisateur={A,B}
    ArrayList<Integer> list_IdReponseQuestion= getIdReponseQuestion(IdReponseQuiz,idQuestion);
    //reponsesPossibles={A,B,C,D}
    List<Reponse> reponsesPossibles=Reponse.getReponsesByQuestionId(idQuestion);
    //correct_reponse={B,C}
    ArrayList<String> correct_reponse=new ArrayList<>();
    int nb_correct = 0;
    int nb_choix=reponsesPossibles.size();
    int point = 0;
    //pour savoir le point pour chaque correct choix
    for(Reponse choix:reponsesPossibles){
        if(choix.estCorrecte()){
            correct_reponse.add(choix.getReponseText());
        }
    }
    nb_correct=correct_reponse.size();
    point=nb_correct/nb_choix; // 2/4=0.5 

    //reponsesUtilisateur={A,B}
    for(int IdReponseQuestion:list_IdReponseQuestion){
        if(correct_reponse.contains(getReponseOfIdReponseQuestion(IdReponseQuestion))){
            updatePoint(IdReponseQuestion, point);
        }else{
            updatePoint(IdReponseQuestion, -point);
        }
    }
}


    /*recuperer les donnes de bdd */
    //IdReponseQuestion -> une seule reponse A
    public  String getReponseOfIdReponseQuestion(int IdReponseQuestion) throws SQLException{
        String reponse = null;
        String sql = "SELECT reponse FROM reponse_question WHERE IdReponseQuestion = ?";
    
        try (
            Connection connection = connectMysql.getConnection();  // Utilise une seule connexion pour les deux requêtes
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            //  recuperer IdReponsesQuestion
            statement.setInt(1, IdReponseQuestion);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reponse = resultSet.getString("reponse");
                }
                //  recuperer les réponses
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return reponse;
    }
    
    //IdReponseQuiz,idQuestion->les reponses d'une question 
    // pour une question de multichoix,user a choisi (A,B,C)
    public ArrayList<String> getReponseOfQuestion(int IdReponseQuiz,int idQuestion) throws SQLException{
        ArrayList<String> reponses = new ArrayList<>();
        int idReponsesQuestion = 0;
        //IdReponsesQuestion= id de {A,B,C}
        String sql1 = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";
        //IdReponseQuestion= id de A/B/C
        String sql2 = "SELECT IdReponseQuestion FROM reponse_question WHERE IdReponsesQuestion = ?";
    
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
                if (idReponsesQuestion != 0) {
                     //  recuperer IdReponseQuestion
                    statement2.setInt(1, idReponsesQuestion);
                    ResultSet resultSet2 = statement2.executeQuery() ;
                    while (resultSet2.next()) {
                        int IdReponseQuestion=resultSet2.getInt("IdReponseQuestion");
                        //obtient le contenue "A" avec id de A
                        String reponse = getReponseOfIdReponseQuestion(IdReponseQuestion);
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
    // ((A,B,C),(A,B),(A,C))
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
    public static int getIdquizzByIdresultats(int IdResultats) throws SQLException {
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

    //IdReponseQuiz,IdQuestion ->les IdReponseQuestion d'une question
    public static ArrayList<Integer> getIdReponseQuestion(int IdReponseQuiz,int IdQuestion) throws SQLException {
        ArrayList<Integer> listIdReponseQuestion = new ArrayList<>(); 
        int idReponsesQuestion = 0;
        String sql1 = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";
        String sql2 = "SELECT IdReponseQuestion FROM reponse_question WHERE IdReponsesQuestion = ?";
    
        try (
            Connection connection = connectMysql.getConnection();  // Utilise une seule connexion pour les deux requêtes
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            PreparedStatement statement2 = connection.prepareStatement(sql2)
        ) {
            //  recuperer IdReponsesQuestion
            statement1.setInt(1, IdReponseQuiz);
            statement1.setInt(2, IdQuestion);
            try (ResultSet resultSet1 = statement1.executeQuery()) {
                if (resultSet1.next()) {
                    idReponsesQuestion = resultSet1.getInt("IdReponsesQuestion");
                }
                if (idReponsesQuestion != 0) {
                     //  recuperer IdReponseQuestion
                    statement2.setInt(1, idReponsesQuestion);
                    ResultSet resultSet2 = statement2.executeQuery() ;
                    while (resultSet2.next()) {
                        int IdReponseQuestion=resultSet2.getInt("IdReponseQuestion");
                        listIdReponseQuestion.add(IdReponseQuestion);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return listIdReponseQuestion;
    }
    
    //IdReponseQuiz,idQuestion-> points d'une question 
    public int getPointOfQuestion(int IdReponseQuiz,int idQuestion) throws SQLException{
        int pointQuestion=0;
        int idReponsesQuestion = 0;
        String sql1 = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";
        String sql2 = "SELECT point FROM reponse_question WHERE IdReponsesQuestion = ?";
    
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
                        int pointReponse = resultSet2.getInt("point");
                        pointQuestion+=pointReponse;
                    }
                    
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        if(pointQuestion<0){
            return 0;
        }else{
            return pointQuestion;
        }
    }
    
    //update point de reponse
    public static void updatePoint(int IdReponseQuestion, float newPoint) throws SQLException {
        String sql = "UPDATE reponse_question SET point = ? WHERE IdReponseQuestion = ?";
    
        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setFloat(1, newPoint);  
            statement.setInt(2, IdReponseQuestion);  
    
        } catch (SQLException e) {
            System.out.println("Erreur de mise à jour dans la base de données: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        }
    }
    

}