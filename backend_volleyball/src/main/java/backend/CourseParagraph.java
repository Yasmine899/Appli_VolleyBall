package backend;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseParagraph {

    public List<Integer> getIdParametrebyIdCourse(BigDecimal idCourse){
        List<Integer> paragraphIds = new ArrayList<>();
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT idParagraph FROM course_paragraph WHERE idCourse = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, idCourse);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int paragraphs = resultSet.getInt("idParagraph");
                paragraphIds.add(paragraphs);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paragraphIds;
    
    }
    public String getContext(int idParagrah){
       
      String result=null;
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT text FROM course_paragraph WHERE idParagraph = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idParagrah);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("text");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    


    public void insertParagraph(int idParagrah, BigDecimal  idCourse, String text){
         try {
        Connection connection = connectMysql.getConnection();
        String sql = "INSERT INTO course_paragraph (idParagraph, idCourse, text) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idParagrah); 
        preparedStatement.setBigDecimal(2,idCourse); 
        preparedStatement.setString(3, text); 
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de insert paragraph---------------------");
            e.printStackTrace();
        }
    }
    public void insertParagraph( BigDecimal  idCourse, String text){
        try {
       Connection connection = connectMysql.getConnection();
       String sql = "INSERT INTO course_paragraph  idCourse, text) VALUES (?, ?)";
       PreparedStatement preparedStatement = connection.prepareStatement(sql);
       preparedStatement.setBigDecimal(1,idCourse); 
       preparedStatement.setString(2, text); 
       int rowsAffected = preparedStatement.executeUpdate();
       System.out.println("Rows affected: " + rowsAffected);
       
       connection.close();
       } catch (SQLException e) {
           System.out.println("--------erreur de insert paragraph---------------------");
           e.printStackTrace();
       }
   }


   public boolean delete_paragraph(int idParagrah){
    boolean reussi =false;
    try {
    Connection connection = connectMysql.getConnection();
    String sql = "DELETE FROM course_paragraph WHERE idParagraph = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, idParagrah); 
    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected>0){
        reussi=true;
    }
    System.out.println("Rows affected: " + rowsAffected);
    connection.close();
    } catch (SQLException e) {
        System.out.println("--------erreur de delete paragraph---------------------");
        e.printStackTrace();
    }
    return reussi; 
}
public boolean delete_paragraph(BigDecimal idCourse){
    boolean reussi =false;
    try {
    Connection connection = connectMysql.getConnection();
    String sql = "DELETE FROM course_paragraph WHERE idCourse = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setBigDecimal(1, idCourse); 
    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected>0){
        reussi=true;
    }
    System.out.println("Rows affected: " + rowsAffected);
    connection.close();
    } catch (SQLException e) {
        System.out.println("--------erreur de delete paragraph---------------------");
        e.printStackTrace();
    }
    return reussi; 
}
}
