package backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;  




public class Resultat {
 
    /* 4 retourner noteQuiz */
    public static float getNoteQuiz(int IdResultats) throws SQLException{
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
    public static  void updateNoteQuiz(int IdResultats) throws SQLException{
       
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
    public static int commenceQuiz(int IdQuizz,int IdPerson) throws SQLException{
        int IdResultats = 0;
        String sql = "INSERT INTO resultats (IdQuizz, IdPerson, date) VALUES (?, ?, ?)";

        try {
            Connection connection = connectMysql.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, IdQuizz);  
            statement.setInt(2, IdPerson); 
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis()); 
            statement.setDate(3, sqlDate);
            statement.executeUpdate();
 
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // System.out.println("bien ajouter une donne dans tableau de resultats avec idquiz:"+IdQuizz);
                    IdResultats = generatedKeys.getInt(1);  
                }
                else{
                    System.out.println("update pas reussi");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Database error during the quiz commencement: " + e.getMessage());
            e.printStackTrace();
        }
        updateIdReponseQuizByIdresultats(IdResultats);

       
        return IdResultats;
    }

     //2. user a fini une question,on va stocker ses reponses dans bdd et evaluer ses reponses
    public static void saveReponsesQuestion(int IdReponseQuiz,int IdQuestion,ArrayList<String> ReponseQuestion,float point ) throws SQLException{
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
        
        for(String reponse:ReponseQuestion){
            saveReponseQuestion(IdReponsesQuestion,reponse);
        }
       

    }
    
    public static void saveReponseQuestion(int IdReponsesQuestion,String reponse){
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
     
    //IdReponseQuiz->list IdReponsesQuestion
    public static ArrayList<Integer> getListIdReponseQuestion(int IdReponseQuiz){
        
        ArrayList<Integer> idList = new ArrayList<>();
        String sql = "SELECT IdReponsesQuestion FROM reponse_quiz WHERE IdReponseQuiz = ?";

        try (
            Connection connection = connectMysql.getConnection();   
            PreparedStatement statement = connection.prepareStatement(sql);  
        ) {
            statement.setInt(1, IdReponseQuiz);  

            ResultSet resultSet = statement.executeQuery(); 
            while (resultSet.next()) {
                int id = resultSet.getInt("IdReponsesQuestion");   
                idList.add(id);   
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
    public static int getIdReponseQuizByIdresultats(int IdResultats) throws SQLException {
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
    private static float getNoteQuestionByIdQuestion(int IdReponseQuiz,int idQuestion) {
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

    public static void updateIdReponseQuizByIdresultats(int IdResultats) throws SQLException {
        String sql = "UPDATE resultats SET IdReponseQuiz = ? WHERE IdResultats = ?";

        try (
            Connection connection = connectMysql.getConnection();  
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdResultats);   
            statement.setInt(2, IdResultats);   
            // System.out.println("bien mise a jour IdReponseQuiz");
        } catch (SQLException e) {
            System.out.println("Database error during the update of IdReponseQuiz: " + e.getMessage());
            e.printStackTrace();
            throw e;  
        }
    } 
    
    public static void afficherResultat (int IdResultats){
        String sql = "SELECT * FROM resultats WHERE IdResultats = ?";
        try (
            Connection connection = connectMysql.getConnection();   
            PreparedStatement statement = connection.prepareStatement(sql)  
        ) {
            statement.setInt(1, IdResultats);   
    
            ResultSet resultSet = statement.executeQuery();  
    
            if (resultSet.next()) { 
          
                int idPerson = resultSet.getInt("IdPerson");
                int idQuizz = resultSet.getInt("IdQuizz");
                float note = resultSet.getFloat("note");
                java.sql.Date date = resultSet.getDate("date");
    
                System.out.println("Resultat pour IdResultats " + IdResultats + ":");
                System.out.println("IdPerson: " + idPerson +" , IdQuizz: " + idQuizz +" , Note: " + note + " , Date: " + date) ;
            } else {
                System.out.println("Aucun résultat trouvé pour IdResultats " + IdResultats);
            }
        } catch (SQLException e) {
            System.out.println("Database error during the retrieval of the result: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void deleteResultat(int IdResultats) throws SQLException{
        int IdReponseQuiz=getIdquizzByIdresultats(IdResultats);
        ArrayList<Integer> list_IdReponseQuestion=getListIdReponseQuestion(IdReponseQuiz);
        for(int IdReponseQuestion:list_IdReponseQuestion){
            //supprimer tous les reponses stocké
            deleteReponsesQuestion(IdReponseQuestion);
        }
        //supprimer les reponsesQustion stocké
        deleteReponseQuiz(IdReponseQuiz);
       
        //supprimer resultat
        String sql = "DELETE FROM resultats WHERE IdResultats = ?";

        try (
            Connection connection = connectMysql.getConnection();   
            PreparedStatement statement = connection.prepareStatement(sql);  
        ) {
            statement.setInt(1, IdResultats);   
             statement.executeUpdate();   
            
        } catch (SQLException e) {
            System.out.println("Database error during the deletion of responses: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void deleteReponseQuiz(int IdReponseQuiz){
        String sql = "DELETE FROM reponse_quiz WHERE IdReponseQuiz = ?";

        try (
            Connection connection = connectMysql.getConnection();   
            PreparedStatement statement = connection.prepareStatement(sql);  
        ) {
            statement.setInt(1, IdReponseQuiz);   
             statement.executeUpdate();   
            
        } catch (SQLException e) {
            System.out.println("Database error during the deletion of responses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteReponsesQuestion(int IdReponsesQuestion){
        String sql = "DELETE FROM reponse_question WHERE IdReponsesQuestion = ?";

        try (
            Connection connection = connectMysql.getConnection();   
            PreparedStatement statement = connection.prepareStatement(sql);  
        ) {
            statement.setInt(1, IdReponsesQuestion);   
             statement.executeUpdate();   
            
        } catch (SQLException e) {
            System.out.println("Database error during the deletion of responses: " + e.getMessage());
            e.printStackTrace();
        }
    
    }
    
    

}