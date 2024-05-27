package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class quizz_question {
    
    // d'apres idQuizz et id Chapitre, rajoute les question random dans la bdd
    public  void insert_quizz_AllQuestion(int idQuizz,int idChapitre){
        int scoreTotal=quizzs.getScoreTotalByIdQuizz(idQuizz);
        List<Question> questions= Question.getQuestionsByChapter(idChapitre);
        List<Integer> idQuestions = questions.stream()
                                     .map(Question::getQuestionId)
                                     .collect(Collectors.toList());
         Collections.shuffle(idQuestions);
        System.out.println(idQuestions);
        for (int comptage=0; comptage<scoreTotal;comptage++){
            insert_quizz_question(idQuizz, idQuestions.get(comptage));
        }
    }
    public void insert_quizz_question ( int idQuizz,int question){
        try {
         Connection connection = connectMysql.getConnection();
         String sql = "INSERT INTO  quizz_question (idQuizz,idQuestion) VALUES (?,?)";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1,idQuizz); 
         preparedStatement.setInt(2,question); 
         int rowsAffected = preparedStatement.executeUpdate();
         System.out.println("Rows affected: " + rowsAffected);
         
         connection.close();
         } catch (SQLException e) {
             System.out.println("--------erreur de insert quizz---------------------");
             e.printStackTrace();
         }
     }
     public void insert_quizz_question ( int id,int idQuizz,int question){
        try {
         Connection connection = connectMysql.getConnection();
         String sql = "INSERT INTO  quizz_question (idQuizz_question,idQuizz,idQuestion) VALUES (?,?,?)";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1,id);
         preparedStatement.setInt(2,idQuizz); 
         preparedStatement.setInt(3,question); 
         int rowsAffected = preparedStatement.executeUpdate();
         System.out.println("Rows affected: " + rowsAffected);
         
         connection.close();
         } catch (SQLException e) {
             System.out.println("--------erreur de insert quizz---------------------");
             e.printStackTrace();
         }
     }

     public boolean Delete_quizz_question(int idQuizz_question){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM quizz_question WHERE idQuizz_question = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idQuizz_question); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete quizz question---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }

    public boolean Delete_quizz_questionByQuizz(int idQuizz){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM quizz_question WHERE idQuizz = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idQuizz); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete quizz question---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }

}
