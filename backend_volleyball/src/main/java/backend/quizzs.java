package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class quizzs {
    public int getScoreTotalByIdQuizz(int idQuizz){
        int scoreTotal=-1;
        try{
            Connection connection = connectMysql.getConnection();
            String sql = "SELECT scoreTotal FROM quizzs WHERE idQuizz = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idQuizz);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                scoreTotal = resultSet.getInt("scoreTotal");
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return scoreTotal;
    }
    public int getRandomQuizz(int idChapitre){
        List<Integer> listQuizz=getQuizzbyidChapitre(idChapitre);
        Random random = new Random();
        int randomIndex = random.nextInt(listQuizz.size());
        int randomQuizz = listQuizz.get(randomIndex);
        return randomQuizz;
    }


    public List<Integer>  getQuizzbyidChapitre(int idChapitre){
        List<Integer> quizzIds = new ArrayList<>();
        try{
            Connection connection =connectMysql.getConnection();
            String sql = "SELECT idQuizz FROM quizzs WHERE idChapitre = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChapitre);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizzId = resultSet.getInt("idQuizz");
                quizzIds.add(quizzId);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzIds;
    }
    public void insert_quizz ( int idChapitre, int scoreTotal){
       try {
        Connection connection = connectMysql.getConnection();
        String sql = "INSERT INTO quizzs (idChapitre, scoreTotal) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,idChapitre); 
        preparedStatement.setInt(2, scoreTotal); 
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de insert quizz---------------------");
            e.printStackTrace();
        }
    }
    public void insert_quizz ( int idQuizz,int idChapitre, int scoreTotal){
        try {
         Connection connection = connectMysql.getConnection();
         String sql = "INSERT INTO quizzs (idQuizz,idChapitre, scoreTotal) VALUES (?,?,?)";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1,idQuizz); 
         preparedStatement.setInt(2,idChapitre); 
         preparedStatement.setInt(3, scoreTotal); 
         int rowsAffected = preparedStatement.executeUpdate();
         System.out.println("Rows affected: " + rowsAffected);
         
         connection.close();
         } catch (SQLException e) {
             System.out.println("--------erreur de insert quizz---------------------");
             e.printStackTrace();
         }
     }

    public boolean Delete_quizz(int idQuizz){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM quizzs WHERE idQuizz = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idQuizz); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete quizz---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }

}

