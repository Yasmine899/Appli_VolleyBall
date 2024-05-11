package backend;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    public static void main( String[] args )
    {
        // Test de la connexion à la base de données
        Connection connection = connectMysql.getConnection();

        if(connection != null) {
            System.out.println("Connexion à la base de données réussie !");
            // Vous pouvez ajouter ici d'autres opérations sur la base de données si nécessaire
            // Assurez-vous de fermer la connexion lorsque vous avez terminé
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        } else {
            System.out.println("La connexion à la base de données a échoué.");
        }
    }
}
