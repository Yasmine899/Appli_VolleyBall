package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class Get {
        //return tous les aide que un utilisateur a demande
        public static ArrayList<String> getCoursTitle() {
           
            connectMysql connectionManager = new connectMysql();
            Connection connection = connectionManager.getConnection();
            ArrayList<String> res= new ArrayList<String>();
            String title=null;

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet=statement.executeQuery("select * from course ");
    
                while (resultSet.next()) {//Parcourt les résultats de la requête ligne par ligne.
                    title="";
                    title+=resultSet.getString("title");
                    res.add(title) ;
                    System.out.println(title);
    
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return res;
        }
    public static void main(String[] args) {
        getCoursTitle();
    }
    
    
}
