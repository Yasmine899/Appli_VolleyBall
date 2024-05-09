package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMysql {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_017", "projet_gei_017", "deiJies9");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        return null;
    }
}


