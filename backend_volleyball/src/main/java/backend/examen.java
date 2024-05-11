package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class examen {

     public List<Question> getQuestionbyid(int idExamen){
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM examen_question WHERE idExamen = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, idExamen);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                   questions.add(Question.getQuestionById(resultSet.getInt("idQuestion")));
                }
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions par quizz : " + e.getMessage());
        }
        return questions;
    }

    //insert un nouveaux examen
    public void insert_new_examen(int idExamen){
         try {
        Connection connection = connectMysql.getConnection();
        String sql = "INSERT INTO examens (idExamen) VALUES ( ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,idExamen);  
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de insert quizz---------------------");
            e.printStackTrace();
        }
        insert_question_lier(idExamen);
    }

    public boolean Delete_examen(int idExamen){
        boolean reussi =Delete_examen_question(idExamen);
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM examens WHERE idExamen = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idExamen); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=reussi&&true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete examen---------------------");
            e.printStackTrace();
        }
        return (reussi ); 
    }

    public boolean Delete_examen_question(int idExamen){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM examen_question WHERE idExamen = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idExamen); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete examen---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }


    public void insert_question_lier(int idExamen){
        List<Question> questions= Question.getAllQuestions();
        List<Integer> idQuestions = questions.stream()
                                     .map(Question::getQuestionId)
                                     .collect(Collectors.toList());
         Collections.shuffle(idQuestions);
        System.out.println(idQuestions);
        for (int comptage=0; comptage<40;comptage++){
            insert_examen_question(idExamen, idQuestions.get(comptage));
        }
    }

    public void insert_examen_question(int idExamen,int idQuestion){
        try {
            Connection connection = connectMysql.getConnection();
            String sql = "INSERT INTO  examen_question (idExamen,idQuestion) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idExamen); 
            preparedStatement.setInt(2,idQuestion); 
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            connection.close();
            } catch (SQLException e) {
                System.out.println("--------erreur de insert examen---------------------");
                e.printStackTrace();
            }
    }
}
