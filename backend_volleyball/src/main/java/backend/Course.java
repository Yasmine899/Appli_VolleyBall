package backend;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course {
     

    //Obtenir tous les id de courses par les chapitres
    public List<BigDecimal> getCoursesByChapterId(int idChapter) {
        List<BigDecimal> courseIds = new ArrayList<>();
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT idCourse FROM courses WHERE idChapitre = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChapter);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BigDecimal courseId = resultSet.getBigDecimal("idCourse");
                courseIds.add(courseId);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseIds;
    }


    //obtenir la description de ce idCourse
    public String getDescriptionByidCourse(BigDecimal idCourse) {
      String result=null;
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT description FROM courses WHERE idCourse = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, idCourse);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("description");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }






   
    public void insert_course(BigDecimal  idCourse, String description, int idChaptre){
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "INSERT INTO courses (idCourse, description, idChapitre) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBigDecimal(1, idCourse); 
        preparedStatement.setString(2,description); 
        preparedStatement.setInt(3, idChaptre); 
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de insert course---------------------");
            e.printStackTrace();
        }
    }
    public boolean delete_course(BigDecimal idCourse){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM courses WHERE idCourse = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBigDecimal(1, idCourse); 
        System.out.println(sql + idCourse);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete course---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }
    public boolean delete_course(String description){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM courses WHERE description = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, description); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete course---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }
    public boolean delete_course(int idChapitre){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM courses WHERE idChapitre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idChapitre); 
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de delete course---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }

    public boolean update_course(BigDecimal idCourse,String updatedescription){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "UPDATE courses SET description = ? WHERE idCourse = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, updatedescription); 
        preparedStatement.setBigDecimal(2, idCourse);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de update course---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }

    public boolean update_course(BigDecimal idCourse,int idChapitre){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "UPDATE courses SET idChapitre = ? WHERE idCourse = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idChapitre); 
        preparedStatement.setBigDecimal(2, idCourse);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected>0){
            reussi=true;
        }
        System.out.println("Rows affected: " + rowsAffected);
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de update course---------------------");
            e.printStackTrace();
        }
        return reussi; 
    }
     
}