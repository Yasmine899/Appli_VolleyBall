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
    public float getNoteQuiz(int IdResultats) throws SQLException{
        float note =0;  
        String sql = "SELECT note FROM resultats WHERE IdResultats = ?";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdResultats); 

            ResultSet resultSet = statement.executeQuery();  
            if (resultSet.next()) { 
                note = resultSet.getFloat("note");  
            }
        } catch (SQLException e) {
            System.out.println("Database error during the retrieval of the quiz note: " + e.getMessage());
            e.printStackTrace();
        }
        return note; 
    }

    //3. quand user a fini ce quiz,on va calculer la note de quiz et update dans bdd
    public void updateNoteQuiz(int IdResultats) throws SQLException{
       
        int idquiz=getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> idQuestions=quizzs.getIDQuestionbyQuizz(idquiz);
        float pointQuestion=0;
        float noteQuiz=0;
        int IdReponseQuiz=0;

        //calculer note de quiz
        for(int idQuestion:idQuestions){
            IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
            pointQuestion=getNoteQuestionByIdQuestion(IdReponseQuiz,idQuestion);
            noteQuiz+=pointQuestion;
        }

        //update
        String sql = "UPDATE resultats SET note = ? WHERE IdResultats = ?";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setFloat(1, noteQuiz);  
            statement.setInt(2, IdResultats); 
        } catch (SQLException e) {
            System.out.println("Database error during the note update: " + e.getMessage());
            e.printStackTrace();
        }

    }


     //1. quand user commence un entrainement,on ajouter les informations de cet entrainement dans resultats
    public int commenceQuiz(int IdQuizz,int IdPerson,Time date) throws SQLException{
        int IdReponseQuiz=0;
        int IdResultats = 0;
        String sql = "INSERT INTO resultats (IdQuizz, IdPerson, date) VALUES (?, ?, ?)";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdQuizz);  
            statement.setInt(2, IdPerson);  
            statement.setTime(3, date);  

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    IdResultats = generatedKeys.getInt(1);  
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Database error during the quiz commencement: " + e.getMessage());
            e.printStackTrace();
        }
        IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
        return IdReponseQuiz;
    }

     //2. user a fini une question,on va stocker ses reponses dans bdd et evaluer ses reponses
    public void saveReponsesQuestion(int IdReponseQuiz,int IdQuestion,ArrayList<String> ReponseQuiz,float point ) throws SQLException{
        int IdReponsesQuestion =0;
        String sql = "INSERT INTO reponse_quiz (IdReponseQuiz, IdQuestion, point ) VALUES (?, ?, ?)";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql) 
        ) {
            statement.setInt(1, IdReponseQuiz);  
            statement.setInt(2, IdQuestion); 
            statement.setFloat(3, point);   

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    IdReponsesQuestion = generatedKeys.getInt("IdReponseQuiz"); 
                } 
            }
            
        } catch (SQLException e) {
            System.out.println("Database error during the insertion of the quiz response: " + e.getMessage());
            e.printStackTrace();
        }
        
        for(String reponse:ReponseQuiz){
            saveReponseQuestion(IdReponsesQuestion,reponse);
        }
       

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

    // 5.afficher les reponses d'une question
    // IdResultats,IdQuestion->ArrayList<String> ReponseQuestions
    public ArrayList<String> getReponsesByidQuestion(int IdResultats,int IdQuestion) throws SQLException{
        String reponse=null;
        ArrayList<String> ReponsesUser=new ArrayList<>();
        
        int IdReponseQuiz=getIdReponseQuizByIdresultats(IdResultats);
        ArrayList<Integer> list_IdReponseQuestion=getListIdReponseQuestionByIdQuestion(IdReponseQuiz,IdQuestion);
        for(int IdReponseQuestion:list_IdReponseQuestion){
            reponse=getReponseByidQuestion(IdReponseQuestion);
            ReponsesUser.add(reponse);
        }
        return ReponsesUser;
    }

    //IdReponseQuestion-> String Reponse
    public String getReponseByidQuestion(int IdReponsesQuestion){
        String ReponseUser = null;   
        String sql = "SELECT reponse FROM reponse_question WHERE IdReponseQuestion = ?";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdReponsesQuestion);  

            ResultSet resultSet = statement.executeQuery(); 

            if (resultSet.next()) {  
                ReponseUser = resultSet.getString("reponse");  
            }
        } catch (SQLException e) {
            System.out.println("Database error during the retrieval of the response: " + e.getMessage());
            e.printStackTrace();
        }
        return ReponseUser; 
    }

    //IdReponseQuiz,IdQuestion->list IdReponseQuestion
    public ArrayList<Integer> getListIdReponseQuestionByIdQuestion(int IdReponseQuiz,int IdQuestion){
        ArrayList<Integer> idList = new ArrayList<>();  

        String sql = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";
        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql) 
        ) {
            statement.setInt(1, IdReponseQuiz);  
            statement.setInt(2, IdQuestion);  
    
            ResultSet resultSet = statement.executeQuery();  
    
            while (resultSet.next()) {  
                idList.add(resultSet.getInt("IdReponsesQuestion"));  
            }
        } catch (SQLException e) {
            System.out.println("Database error during the retrieval of IdReponsesQuestion: " + e.getMessage());
            e.printStackTrace();
        }
        return idList;
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

    //IdReponseQuiz,idQuestion -> note d'une question
    private float getNoteQuestionByIdQuestion(int IdReponseQuiz,int idQuestion) {
        float point=0;
        String sql = "SELECT point FROM reponse_quiz WHERE IdReponseQuiz = ? AND IdQuestion = ?";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdReponseQuiz);  
            statement.setInt(2, idQuestion);  
    
            ResultSet resultSet = statement.executeQuery();  
    
            if (resultSet.next()) {  
                point = resultSet.getFloat("point");  
            }
        } catch (SQLException e) {
            System.out.println("Database error during the retrieval of the question score: " + e.getMessage());
            e.printStackTrace();
        }
        return point;
        
    }

    

}