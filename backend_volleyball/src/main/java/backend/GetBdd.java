package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Statement;

public class GetBdd {

        //return tous les titres
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

        /* return les infos de quiz que l'utilisateur a fait
         * afficher le chapitre correspond,la date,le note
        */
        public static ArrayList<String> revoirQuiz(int idPerson) {
           
            connectMysql connectionManager = new connectMysql();
            Connection connection = connectionManager.getConnection();
            ArrayList<String> res= new ArrayList<String>();

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet=statement.executeQuery("SELECT Idcours, date, note FROM Table_Resultat WHERE idPerson = ?");
    
                while (resultSet.next()) {
                    // Récupération des valeurs de chaque colonne
                    int courseId = resultSet.getInt("Idcours");
                    Date date = resultSet.getDate("date");
                    int note = resultSet.getInt("note");
    
                    // Construction de la chaîne de résultat
                    String resultString = "Chapitre: " + courseId + ", Date: " + date + ", Note: " + note;
    
                    // Ajout de la chaîne de résultat à la liste
                    res.add(resultString);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return res;
        }

         /* return les question de quiz que l'utilisateur a fait
         * afficher les question,reponces,correction de quiz
        */
        public static ArrayList<String> getQuizFait(int idPerson,int idquiz) {
           
            connectMysql connectionManager = new connectMysql();
            Connection connection = connectionManager.getConnection();
            ArrayList<String> res= new ArrayList<String>();
            String title=null;

            try (Statement statement = connection.createStatement()) {
                // ResultSet resultSet=statement.executeQuery("select * from course ");
                // while (resultSet.next()) {//Parcourt les résultats de la requête ligne par ligne.
                //     title="";
                //     title+=resultSet.getString("title");
                //     res.add(title) ;
                //     System.out.println(title);
    
                // }
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
