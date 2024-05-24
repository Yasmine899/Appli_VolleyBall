package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String  course_description;
    private int  idChapitre;
    private String title;
    private String Reference_id;
      public Course (){}
      public Course (int course_id,String title,String course_description,int idChapitre,String Reference_id){
        this.Reference_id=Reference_id;
        this.course_description=course_description;
        this.idChapitre=idChapitre;
        this.title=title;
      }
    //obtenir les chapitre par les idSection
    public List<Integer> getChapitreBySection(int IdSection){
          Connection connection =connectMysql.getConnection();
        List<Integer> idChapitres=new ArrayList<>();
        try{
            String sql = "select idChapitre from section_chapitre where idSection =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, IdSection);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            idChapitres.add(resultSet.getInt("idChapitre"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idChapitres;
    }
    
    //Obtenir tous les courses par les chapitresx
    public List<Course> getCoursesByChapterId(int idChapter) {
        List<Course> courseIds = new ArrayList<>();
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT * FROM courses WHERE idChapitre = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChapter);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course= new Course();
                course.setCourse_description(resultSet.getString("course_description"));
                course.setIdChapitre(idChapter);
                course.setReference_id(resultSet.getString("Reference_id"));
                course.setTitle(resultSet.getString("title"));
                courseIds.add(course);
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
    public String getDescriptionByReferenceId(String  Reference_id) {
      String result=null;
        try{
            Connection connection =connectMysql.getConnection();
        
            String sql = "SELECT course_description FROM courses WHERE Reference_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Reference_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("course_description");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }




    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
    public void setIdChapitre(int idChapitre) {
        this.idChapitre = idChapitre;
    }
    public void setReference_id(String reference_id) {
        Reference_id = reference_id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public String getCourse_description() {
        return course_description;
    }
    public int getIdChapitre() {
        return idChapitre;
    }
    public String getReference_id() {
        return Reference_id;
    }
    @Override
    public String toString() {
        
        return  "Course{\n" +
        "Reference ID= " + this.Reference_id +
        "\ntitle= "+this.title+
        "\n, chapitre= " + this.idChapitre +
        ", Text='" + this.course_description +
        '}';
    }
   
    public void insert_course(String  Reference_id,String title, String course_description, int idChaptre){
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "INSERT INTO courses (course_description, idChapitre,Reference_id,title) VALUES (?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, course_description); 
        preparedStatement.setString(3,Reference_id); 
        preparedStatement.setInt(2, idChaptre); 
        preparedStatement.setString(4,title);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        
        connection.close();
        } catch (SQLException e) {
            System.out.println("--------erreur de insert course---------------------");
            e.printStackTrace();
        }
    }
    public boolean delete_course_by_reference(String Reference_id){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM courses WHERE Reference_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,Reference_id); 
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
    public boolean delete_course_by_description(String description){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "DELETE FROM courses WHERE course_description = ?";
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

    public boolean update_course(String Reference_id ,String updatedescription){
        boolean reussi =false;
        try {
        Connection connection = connectMysql.getConnection();
        String sql = "UPDATE courses SET course_description = ? WHERE Reference_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, updatedescription); 
        preparedStatement.setString(2, Reference_id);
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
